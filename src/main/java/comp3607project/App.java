package comp3607project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        ReportGenerator reportGenerator = new ReportGenerator();

        // Define paths
        Path zipFilePath = Path.of("submissions.zip"); // Path to ZIP file containing student submissions
        //Path outputDirectory = Path.of("extractedSubmissions"); // Directory to extract files
        Path outputDirectory = Path.of("src", "main", "java", "comp3607project", "extractedSubmissions");

  
        // Extract the main submission ZIP file 
        ZipHandler zipHandler = new ZipHandler();
        zipHandler.setOutputDirectory(outputDirectory);
        zipHandler.extractZipFile(zipFilePath);
 
        // Iterate over each student submission directory within the extracted submissions directory 
        // and append package name to java files only
        try {
            Files.list(outputDirectory).filter(Files::isDirectory).forEach(subDirectory -> {
                Path studentSubmissionDirectory = subDirectory;
                zipHandler.appendPackageToJavaFiles(studentSubmissionDirectory);
            });
        } catch (IOException e) {
            System.out.println("Error in appending package name to java files");
            e.printStackTrace();
        }


        // Step 2: Initialize Evaluator
        JavaEvaluator evaluator = new JavaEvaluator();
        evaluator.addObserver(new ConsoleLoggerObserver());

        // Step 3: Collect Java classes from extracted files..evaluation and generate report
        try {
            Files.list(outputDirectory)
                 .filter(Files::isDirectory) // Filter to ensure only directories
                 .forEach(subDirectory -> {
                    try {
                        String subDirectoryName = subDirectory.getFileName().toString();
                        String studentID = extractStudentId(subDirectoryName);
                        if (studentID != null) {
                            System.out.println("Submission name format is valid. Student ID: " + subDirectoryName);
                            try {
                                // Collect all .java files within the current subfolder
                                Files.walk(subDirectory)
                                     .filter(Files::isRegularFile) // Ensure it's a file
                                     .filter(file -> file.toString().endsWith(".class")) // Only .java files
                                     .forEach(file -> {
                                       try {
                                           ArrayList<Class<?>> classInstances = new ArrayList<>();
                                           // Convert the file path into a fully qualified class name
                                           String className = "comp3607project.extractedSubmissions." + subDirectory.getFileName().toString() + "." + file.getFileName().toString().replaceAll(".class", "");
                                           // Load the class dynamically
                                           Class<?> clazz = Class.forName(className);
                                           classInstances.add(clazz);
                                           classInstances.forEach(System.out::println);
                                           evaluator.inspect(classInstances, studentID, subDirectory);
                                       } catch (ClassNotFoundException e) {
                                           System.err.println("Class not found: " + e.getMessage());
                                       }
                                   });
                            } catch (IOException e) {
                                System.err.println("Error reading files in subfolder: " + subDirectory);
                                e.printStackTrace();
                            }
                        } else {
                            reportGenerator.generateReport(studentID, null, null, "Submission name format is INVALID", subDirectory );
                            System.err.println("Submission name format is INVALID. " + subDirectoryName);
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

    private static String extractStudentId(String folderName) {
        String[] parts = folderName.split("_");
        if (parts.length >= 3) {
            String studentId = parts[2];
            if (studentId.matches("\\d{9}")) {
                return studentId;
            } 
        }
        return null;
    }

}
