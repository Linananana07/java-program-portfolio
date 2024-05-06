import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    public List<File> findJSONFiles(String directoryPath) {
        List<File> jsonFiles = new ArrayList<>();
        findFilesWithExtension(directoryPath, ".json", jsonFiles);
        return jsonFiles;
    }

    public List<File> findCSVFiles(String directoryPath) {
        List<File> csvFiles = new ArrayList<>();
        findFilesWithExtension(directoryPath, ".csv", csvFiles);
        return csvFiles;
    }

    private void findFilesWithExtension(String directoryPath, String extension, List<File> files) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Указанный путь не является директорией");
            return;
        }
        findFiles(directory, extension, files);
    }

    private void findFiles(File directory, String extension, List<File> files) {
        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory != null) {
            for (File file : filesInDirectory) {
                if (file.isDirectory()) {
                    findFiles(file, extension, files); // Рекурсивный вызов для поддиректорий
                } else {
                    if (file.getName().endsWith(extension)) {
                        files.add(file);
                    }
                }
            }
        }
    }
}
