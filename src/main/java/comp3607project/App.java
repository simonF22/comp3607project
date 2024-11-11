package comp3607project;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Define paths
        Path zipFilePath = Path.of("submissions.zip"); // Path to ZIP file containing student submissions
        Path outputDirectory = Path.of("extractedSubmissions"); // Directory to extract files

        // Step 1: Extract the ZIP file
        ZipHandler zipHandler = new ZipHandler();
        zipHandler.setOutputDirectory(outputDirectory);
        zipHandler.extractZipFile(zipFilePath);

        // Step 2: Initialize Evaluator
        JavaEvaluator evaluator = new JavaEvaluator();
        evaluator.addObserver(new ConsoleLoggerObserver());

        // Step 3: Collect Java classes from extracted files (use real classes if dynamically loaded)
        List<Class<?>> mockClasses = new ArrayList<>();
        mockClasses.add(String.class); // Placeholder; replace with actual classes if possible

        // Step 4: Evaluate submissions and generate report
        String studentId = "12345"; // Example student ID
        evaluator.inspect(mockClasses, studentId);

        System.out.println("Evaluation complete for student " + studentId);
    }
}
