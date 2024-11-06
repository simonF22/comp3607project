package comp3607project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {

        ZipHandler zipHandler = new ZipHandler();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to the ZIP file containing student submissions: ");
        String zipFileAddressText = scanner.nextLine();
        Path zipFilePath = Paths.get(zipFileAddressText);
        scanner.close();

        if (!Files.exists(zipFilePath) || !zipFilePath.toString().endsWith(".zip")) {
            System.err.println("Invalid file path. Please ensure the file exists and is a ZIP file.");
            return;
        }
        
        Path outputDirectory = Paths.get(System.getProperty("user.dir"), "src", "submissions");
        
        zipHandler.setOutputDirectory(outputDirectory);
        zipHandler.extractZipFile(zipFilePath);
    }    
}