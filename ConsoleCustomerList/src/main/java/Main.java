import java.util.Scanner;
import org.apache.logging.log4j.*;

public class Main {

    private static final Logger logger =
            LogManager.getLogger(Main.class);

    private static final Logger queriesLogger = LogManager.getLogger("queriesLogger");

    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Неправильная команда! Пример правильных команд: \n" +
            COMMAND_EXAMPLES;
    private static final String helpText = "Команды:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Программа хранения списока клиентов\n").append(helpText);
        System.out.println(stringBuilder);
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        logger.log(Level.WARN, "Сообщение об ошибке");

        while (true) {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);
            try {
                if (tokens[0].equals("add")) {
                    queriesLogger.info("Запрос добавления пользователя: " + tokens[1]);
                    executor.addCustomer(tokens[1]);
                } else if (tokens[0].equals("list")) {
                    executor.listCustomers();
                    queriesLogger.info("Вывод списка");
                } else if (tokens[0].equals("remove")) {
                    executor.removeCustomer(tokens[1]);
                    queriesLogger.info("Удаление пользователя: " + tokens[1]);
                } else if (tokens[0].equals("count")) {
                    if (executor.getCount() > 1 || executor.getCount() == 0) {
                        System.out.println("В списке " + executor.getCount() + " пользователей");
                    } else System.out.println("В списке " + executor.getCount() + " пользователь");
                    queriesLogger.info("Вывод количества пользователей в списке");
                } else if (tokens[0].equals("help")) {
                    System.out.println(helpText);
                    queriesLogger.info("Введена команда помощи");
                } else {
                    System.out.println(COMMAND_ERROR);
                    queriesLogger.info("Введена неправильная команда");
                }
            } catch (InvalidDataIsEmpty | InvalidDataLongFormatException | InvalidDataShortFormatException |
                     InvalidPhoneNumberException |
                     InvalidEmailFormatException | CustomerAlreadyExistsException e) {
            }
        }
    }
}