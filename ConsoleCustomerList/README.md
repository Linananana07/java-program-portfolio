# ConsoleCustomerList

The ConsoleCustomerList project is a console application for managing a list of clients. Users can utilize various commands for adding, deleting, updating, and viewing client information. Type "help" in the console to get a list of available commands and examples of their usage.

Try entering different values and commands that do not match the examples to trigger exceptions. The program should handle exceptions as follows:

# IncorrectDataFormatException:
Thrown when the number of components in the provided data string is incorrect.
Example: an incorrect number of parameters is entered when adding a client.

# InvalidPhoneNumberFormatException:
Thrown when the phone number has an invalid format.
Example: a phone number is entered that does not comply with standards.
# InvalidEmailFormatException:
Thrown when the email has an invalid format.
Example: an email is entered that does not comply with standards.
#
The addCustomer() method should be protected from premature termination by handling exceptions. Information about exceptions should be displayed in the console in a user-friendly manner and logged in log files.

To achieve this, the Log4j2 library is connected and two separate logs are configured:

queries.log - filled with information about all requests to the application.
errors.log - filled with information about all encountered errors and exceptions, including details.
