import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class CSVParser {
    public static List<CSVStation> parseCSV(File file) throws IOException {
        List<CSVStation> stations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true; // Флаг для определения первой строки
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Пропускаем первую строку
                }
                if (!line.trim().isEmpty()) { // Пропускаем пустые строки
                    String[] values = line.split(",");
                    if (values.length >= 2) {
                        String name = values[0].trim();
                        String date = values[1].trim();
                        CSVStation station = new CSVStation(name, date);
                        stations.add(station);
                    }
                }
            }
        }
        return stations;
    }
    public static void parseToJSONFile(List<CSVStation> stations, String filePath) {
        // Сортируем список станций по имени
        Collections.sort(stations, Comparator.comparing(CSVStation::getName));

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