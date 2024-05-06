import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class WebDataParsing {

    private static final String URL = "https://skillbox-java.github.io/";

    public List<Map<String, String>> parseLines() {
        List<Map<String, String>> linesList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements metroLines = doc.select(".js-metro-line");

            for (Element metroLine : metroLines) {
                String lineNumber = metroLine.attr("data-line");
                String lineName = metroLine.text();

                Map<String, String> lineData = new HashMap<>();
                lineData.put("number", lineNumber);
                lineData.put("name", lineName);

                linesList.add(lineData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    public Map<String, List<String>> parseStation() {
        Map<String, List<String>> stationsMap = new TreeMap<>(new MetroComparator());

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements linesElement = doc.select(".js-depend");

            for (Element lineElement : linesElement) {
                String lineNumber = lineElement.attr("data-depend-set")
                        .replaceFirst(".*-", "");
                Elements stationElements = lineElement.select(".name");
                List<String> stationNames = new ArrayList<>();
                for (Element stationElement : stationElements) {
                    stationNames.add(stationElement.text());
                }
                stationsMap.put(lineNumber, stationNames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationsMap;
    }

    public static  List<List<HashMap<String, String>>> parseStationConnections() {
        List<List<HashMap<String, String>>> stationDataList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements metroStations = doc.select(".single-station");

            for (Element element : metroStations) {
                List<HashMap<String, String>> stationData = new ArrayList<>();
                HashMap<String, String> stationDetails = new HashMap<>();
                String line = element.parent().attr("data-line");
                String name = element.select(".name").text();
                stationDetails.put("line", line);
                stationDetails.put("name", name);

                Elements lnElements = element.select(".t-icon-metroln");
                if (!lnElements.isEmpty()) {
                    stationData.add(stationDetails);

                    for (Element lnElement : lnElements) {
                        String lnClass = lnElement.className()
                                .replaceAll(".*ln-([A-Za-z]*)(\\d+).*", "$1$2");
                        String title = lnElement.attr("title")
                                .replaceAll(".*«(.*?)».*", "$1");
                        HashMap<String, String> lnData = new HashMap<>();
                        lnData.put("line", lnClass);
                        lnData.put("name", title);
                        stationData.add(lnData);

                    }
                    stationDataList.add(stationData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationDataList;
    }
}
