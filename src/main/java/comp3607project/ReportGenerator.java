package comp3607project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class ReportGenerator {

    public void generateReport(String studentId,  Map<String, Integer> tests, Map<String, String> feedback, String comment, Path filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write("Evaluation Report for Student ID: " + studentId);
            writer.newLine();
            writer.write("======================================");
            writer.newLine();

            int totalScore = 0;
            if (tests == null || tests.isEmpty()) {
                writer.write("No test results available.");
                writer.newLine();
            } else{
                for (Map.Entry<String, Integer> entry : tests.entrySet()) {
                    String testName = entry.getKey();
                    int score = entry.getValue();
                    totalScore += score;
                    writer.write(testName + ": " + score);
                    writer.newLine();
    
                    // Add feedback for each test
                    if (feedback.containsKey(testName)) {
                        writer.write("  Feedback: " + feedback.get(testName));
                        writer.newLine();
                    }
                }
            }


            writer.write("======================================");
            writer.newLine();
            writer.write(comment);
            writer.newLine();
            writer.write("Total Score: " + totalScore);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
        
        System.out.println("Text report generated: " + filePath.getFileName());
    }
}

