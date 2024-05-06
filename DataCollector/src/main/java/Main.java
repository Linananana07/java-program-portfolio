import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String RESULT_FILE_PATH = "map.json";
    private static final String DIRECTORY_FILES = "src/data";

    public static void main (String[] args) {

        WebDataParsing dataParsing = new WebDataParsing();

        //Парсинг списка линий метро
        List<Map<String, String>> lines = dataParsing.parseLines();
        //Парсинг списка станций метро
        Map<String, List<String>> stations = dataParsing.parseStation();
        //Парсинг списка станций и их пересадок
        List<List<HashMap<String, String>>> connections = WebDataParsing.parseStationConnections();
        //Объединение данных
        MetroStationAndConnectionsCombinedData combinedData = new MetroStationAndConnectionsCombinedData(stations, connections, lines);
        //Запись данных в statons.json
        MetroDataWriter.writeToJsonFile(combinedData, RESULT_FILE_PATH);

        FileFinder fileFinder = new FileFinder();

        // Найти JSON файлы
        List<JSONStation> allStationsJSON = new ArrayList<>();
        List<File> jsonFiles = fileFinder.findJSONFiles(DIRECTORY_FILES);

        try{

            for (File file : jsonFiles) {
                List<JSONStation> stationsJSON = JSONParser.parseJSON(file);
                allStationsJSON.addAll(stationsJSON);
            }

            JSONParser.parseToJSONFile(allStationsJSON, "json_stations.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Найти CSV файлы
        List<CSVStation> allStationsCSV = new ArrayList<>();
        List<File> csvFiles = fileFinder.findCSVFiles(DIRECTORY_FILES);
        try{

            for (File file : csvFiles) {
                List<CSVStation> stationsCSV = CSVParser.parseCSV(file);
                allStationsCSV.addAll(stationsCSV);
            }

            CSVParser.parseToJSONFile(allStationsCSV, "csv_stations.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(RESULT_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject data = gson.fromJson(reader, JsonObject.class);

            JsonObject result = MetroDataProcessor.processData(data, allStationsCSV, allStationsJSON);
            MetroDataWriter.writeResultToFile(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

