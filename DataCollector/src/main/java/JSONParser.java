import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class JSONParser {
    public static List<JSONStation> parseJSON(File file) throws IOException {
        List<JSONStation> stations = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            JSONStation[] stationArray = gson.fromJson(br, JSONStation[].class);
            stations.addAll(Arrays.asList(stationArray));
        }
        return stations;
    }
    public static void parseToJSONFile(List<JSONStation> stations, String filePath) {
        // Сортируем список станций по имени
        Collections.sort(stations, Comparator.comparing(JSONStation::getStation_name));

        // Создаем объект Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            // Преобразуем отсортированный список станций в JSON и записываем в файл
            gson.toJson(stations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}