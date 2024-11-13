package comp3607project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class pdfGenerator {

    public void generateReport(String studentId, Map<String, Integer> testResults, Map<String, String> feedbackMap, Path filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.COURIER_BOLD, 18);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Evaluation Report for Student ID: " + studentId);
                contentStream.newLine();
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.showText("======================================");
                contentStream.newLine();

                int totalScore = 0;
                
                for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
                    String testName = entry.getKey();
                    int score = entry.getValue();
                    totalScore = totalScore+score;

                    contentStream.showText(testName + ": " + score);
                    contentStream.newLine();

                    if (feedbackMap.containsKey(testName)) {
                        contentStream.showText("  Feedback: " + feedbackMap.get(testName));
                        contentStream.newLine();
                    }
                }

                contentStream.showText("======================================");
                contentStream.newLine();

                contentStream.showText("Total Score: " + totalScore);
                contentStream.endText();
            }

            document.save(filePath.toFile());
            System.out.println("PDF generated" );

        } catch (IOException e) {
            System.err.println("Error generating PDF" );
        }
    }
}
