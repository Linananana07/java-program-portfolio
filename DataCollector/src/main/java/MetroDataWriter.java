import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;

public class MetroDataWriter {
    private static final String RESULT_FILE_PATH = "stations.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeResultToFile(JsonObject result) {
        try (FileWriter writer = new FileWriter(RESULT_FILE_PATH)) {
            gson.toJson(result, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToJsonFile(MetroStationAndConnectionsCombinedData data, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("stations", gson.toJsonTree(data.getStations()));
        jsonObject.add("connections", gson.toJsonTree(data.getConnections()));
        jsonObject.add("lines", gson.toJsonTree(data.getLines()));

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
