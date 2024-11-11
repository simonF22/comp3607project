package comp3607project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ReportGenerator {

    public void generateReport(String studentId, Map<String, Integer> testResults, Map<String, String> feedbackMap, Path filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write("Evaluation Report for Student ID: " + studentId);
            writer.newLine();
            writer.write("======================================");
            writer.newLine();

            int totalScore = 0;
            for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
                String testName = entry.getKey();
                int score = entry.getValue();
                totalScore += score;
                writer.write(testName + ": " + score);
                writer.newLine();

                // Add feedback for each test
                if (feedbackMap.containsKey(testName)) {
                    writer.write("  Feedback: " + feedbackMap.get(testName));
                    writer.newLine();
                }
            }

            writer.write("======================================");
            writer.newLine();
            writer.write("Total Score: " + totalScore);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
        
        System.out.println("Text report generated: " + filePath.getFileName());
    }
}

