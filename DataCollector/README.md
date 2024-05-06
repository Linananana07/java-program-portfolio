# Data Collector

The Data Collector program is designed to gather data from various sources and store it in JSON format. It consists of several modules, each performing a specific function:

# 1. Parsing the Moscow Metro webpage:
The program retrieves the HTML code of the ["Moscow Metro Stations List"](https://skillbox-java.github.io/) page and extracts information about metro lines and stations from it.

# 2. Searching for files in folders:
The program traverses folders within an archive and looks for files in JSON and CSV formats containing data about metro stations and other information.
# 3. Parsing JSON files:
The program analyzes JSON files and extracts data about metro stations and other details from them.
# 4. Parsing CSV files:
The program parses CSV files and extracts data about metro stations and other details from them.
# 5. Creating and writing JSON files:

Finally, the program combines the collected data and creates two JSON files: one with information about metro lines and stations in a format similar to the SPBMetro project file, and another with detailed information about each station.
#
Thus, the Data Collector program automates the process of collecting data about the Moscow Metro from various sources and organizes it into a convenient JSON format for further use.
