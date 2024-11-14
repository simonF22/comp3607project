package comp3607project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // Step 1: Get path of submission zip
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the ZIP file containing student submissions: ");
        String zipFileAddressText = scanner.nextLine();
        Path zipFilePath = Paths.get(zipFileAddressText);
        scanner.close();
        if (!Files.exists(zipFilePath) || !zipFilePath.toString().endsWith(".zip")) {
            System.err.println("Invalid file path. Please ensure the file exists and is a ZIP file.");
            return;
        }

        // Define path of extracted submissions
        Path outputDirectory = Path.of("src", "main", "java", "comp3607project", "extractedSubmissions");

  
        // Extract the main submission ZIP file 
        ZipHandler zipHandler = new ZipHandler();
        zipHandler.setOutputDirectory(outputDirectory);
        zipHandler.extractZipFile(zipFilePath);

        // Step 2: Initialize Evaluator
        JavaEvaluator evaluator = new JavaEvaluator();
        evaluator.addObserver(new ConsoleLoggerObserver());

        // Step 3: Collect Java classes from extracted files..evaluation and generate report
        PDFGenerator pdfGenerator = new PDFGenerator();
        try {
            Files.list(outputDirectory)
                 .filter(Files::isDirectory) // Filter to ensure only directories
                 .forEach(subDirectory -> {
                    try {
                        String subDirectoryName = subDirectory.getFileName().toString();
                        String[] studentInfo = extractStudentInfo(subDirectoryName);
                        if (studentInfo != null) {
                            System.out.println("Submission - " + subDirectoryName + " - name format is valid");
                            try {
                                // Collect all .class files within the current subfolder
                                Files.walk(subDirectory)
                                     .filter(Files::isRegularFile)
                                     .filter(file -> file.toString().endsWith(".class"))
                                     .forEach(file -> {
                                       try {
                                           ArrayList<Class<?>> classInstances = new ArrayList<>();
                                           // Convert the file path into a fully qualified class name
                                           String className = "comp3607project.extractedSubmissions." + subDirectory.getFileName().toString() + "." + file.getFileName().toString().replaceAll(".class", "");
                                           // Load the class dynamically
                                           Class<?> clazz = Class.forName(className);
                                           classInstances.add(clazz);
                                           //classInstances.forEach(System.out::println);
                                           classInstances.forEach(c -> System.out.println(c.getSimpleName()));
                                           evaluator.inspect(classInstances, studentInfo, subDirectory);
                                       } catch (ClassNotFoundException e) {
                                           System.err.println("Class not found: " + e.getMessage());
                                       }
                                   });
                            } catch (IOException e) {
                                System.err.println("Error reading files in subfolder: " + subDirectory);
                                e.printStackTrace();
                            }
                        } else {
                            // Generates a report for a submission that did not meet submission name convention
                            pdfGenerator.generateInvalidReport("Submission name format is INVALID", subDirectory.resolve("Report.pdf") );
                            System.out.println("Submission - " + subDirectoryName + " - name format is INVALID");
                        }
                    }catch (Exception e) {
                         System.err.println("Error processing subfolder: " + subDirectory);
                         e.printStackTrace();
                    }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Extracts info from a submission with valid name convention (first name, last name, id, assignment)
    private static String[] extractStudentInfo(String folderName) {
        String[] parts = folderName.split("_");
        
        if (parts.length == 4) {
            String firstName = parts[0];
            String secondName = parts[1];
            String studentId = parts[2];
            String assignmentCode = parts[3];

            if (studentId.matches("\\d{9}")) {
                return new String[] { firstName, secondName, studentId, assignmentCode };
            }
        }
        return null;
    }
}
