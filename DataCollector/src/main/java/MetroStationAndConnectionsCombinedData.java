import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroStationAndConnectionsCombinedData {
    Map<String, List<String>> stations;
    List<List<HashMap<String, String>>> connections;
    List<Map<String, String>> lines;

    public MetroStationAndConnectionsCombinedData(Map<String, List<String>> stations, List<List<HashMap<String, String>>> stationDataList, List<Map<String, String>> lines) {
        this.stations = stations;
        this.connections = stationDataList;
        this.lines = lines;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }

    public void setStations(Map<String, List<String>> stations) {
        this.stations = stations;
    }

    public List<List<HashMap<String, String>>> getConnections() {
        return connections;
    }

    public void setConnections(List<List<HashMap<String, String>>> connections) {
        this.connections = connections;
    }

    public List<Map<String, String>> getLines() {
        return lines;
    }

    public void setLines(List<Map<String, String>> lines) {
        this.lines = lines;
    }
}
