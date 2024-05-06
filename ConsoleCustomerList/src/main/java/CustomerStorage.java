import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.*;

public class CustomerStorage {
    private final Map<String, Customer> storage;
    private static final Logger logger = LogManager.getLogger(CustomerStorage.class);
    private static final String EMAIL_REGEX = "^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$";

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        try {
        if (data.trim().isEmpty()) {
            throw new InvalidDataIsEmpty("Отсутствуют данные");
        }

        String[] components = data.split("\\s+");
        if (components.length > 4) {
            throw new InvalidDataLongFormatException("Передано более 4 слов в строке");
        } else if (components.length < 4) {
            throw new InvalidDataShortFormatException("Недостаточно данных");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        String email = components[INDEX_EMAIL];
        String phone = components[INDEX_PHONE];

        if (!isValidPhoneNumber(phone)) {
            throw new InvalidPhoneNumberException("Неверный формат номера");
        }

        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Неверный формат email");
        }
        if (storage.containsKey(name)) {
            throw new CustomerAlreadyExistsException("Клиент с таким именем уже существует");
        }

        storage.put(name, new Customer(name, phone, email));
        logger.log(Level.INFO, "Пользователь с именем: " + name + " добавлен");
        } catch (InvalidDataIsEmpty | InvalidDataLongFormatException | InvalidDataShortFormatException | InvalidPhoneNumberException |
          InvalidEmailFormatException | CustomerAlreadyExistsException e) {
            logger.log(Level.ERROR, "Ошибка при добавлении пользователя: " + e.getMessage());
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());

            throw e;
        }
    }


    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }

    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "\\+7\\d{10}";
        return phone.matches(phoneRegex);
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}