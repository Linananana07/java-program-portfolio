import java.util.Comparator;

public class MetroComparator implements Comparator<String> {
    @Override
    public int compare(String line1, String line2) {
        int number1 = extractNumber(line1);
        int number2 = extractNumber(line2);
        if (number1 == number2) {
            return line1.compareTo(line2);
        } else if (line1.equals("D1")) {
            return 1;
        } else if (line2.equals("D1")) {
            return -1;
        } else if (line1.equals("D2")) {
            return 1;
        } else if (line2.equals("D2")) {
            return -1;
        } else {
            return Integer.compare(number1, number2);
        }
    }

    private int extractNumber(String line) {
        try {
            String number = line.replaceAll("[^0-9]", "");
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
