import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.regex.Pattern;

public class MetroDataProcessor {
    public static JsonObject processData(JsonObject data, List<CSVStation> csvStations, List<JSONStation> jsonStations) {
        JsonObject result = new JsonObject();

        JsonObject stationsData = data.getAsJsonObject("stations");
        JsonArray connectionsData = data.getAsJsonArray("connections");
        JsonArray linesData = data.getAsJsonArray("lines");

        JsonArray stationsArray = new JsonArray();

        for (String line : stationsData.keySet()) {
            JsonArray stationNames = stationsData.getAsJsonArray(line);
            String lineName = getLineName(line, linesData);

            for (JsonElement stationElement : stationNames) {
                String stationName = normalizeStationName(stationElement.getAsString()); // Нормализация имени станции
                JsonObject stationObject = new JsonObject();
                stationObject.addProperty("name", stationName);
                stationObject.addProperty("line", lineName);
                // Поиск и добавление данных о станции из CSV
                for (CSVStation csvStation : csvStations) {
                    if (normalizeStationName(csvStation.getName()).equalsIgnoreCase(stationName)) {
                        stationObject.addProperty("date", csvStation.getDate());
                        break;
                    }
                }

// Поиск и добавление данных о станции из JSON
                for (JSONStation jsonStation : jsonStations) {
                    if (normalizeStationName(jsonStation.getStation_name()).equalsIgnoreCase(stationName)) {
                        String depthValue = jsonStation.getDepth();
                        // Проверяем, содержит ли строка цифры
                        if (Pattern.matches(".*\\d+.*", depthValue)) {
                            // Заменяем запятую на точку
                            depthValue = depthValue.replace(',', '.');
                            // Пытаемся преобразовать строку в тип double
                            try {
                                double depthDouble = Double.parseDouble(depthValue);
                                // Если успешно, добавляем параметр
                                stationObject.addProperty("depth", depthDouble);
                            } catch (NumberFormatException e) {
                                // В случае ошибки преобразования не делаем ничего, параметр не записываем
                            }
                        }
                        break;
                    }
                }
                stationObject.addProperty("hasConnection", hasConnection(stationName, connectionsData));


                stationsArray.add(stationObject);
            }
        }

        result.add("stations", stationsArray);

        return result;
    }

    private static String getLineName(String lineNumber, JsonArray linesData) {
        for (JsonElement lineElement : linesData) {
            JsonObject lineObject = lineElement.getAsJsonObject();
            if (lineObject.get("number").getAsString().equals(lineNumber)) {
                return lineObject.get("name").getAsString();
            }
        }
        return "";
    }

    private static boolean hasConnection(String stationName, JsonArray connectionsData) {
        for (JsonElement connectionElement : connectionsData) {
            JsonArray connectionArray = connectionElement.getAsJsonArray();
            for (JsonElement stationElement : connectionArray) {
                JsonObject stationObject = stationElement.getAsJsonObject();
                if (stationObject.get("name").getAsString().equals(stationName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String normalizeStationName(String name) {

        return name.replace('ё', 'е')
                .replace('й', 'и')
                .replace('Ё', 'Е')
                .replace('Й', 'И');
    }
}
