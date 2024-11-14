package comp3607project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class PDFGenerator {

    public void generateReport(String[] studentInfo, int totalScore, Map<String, Integer> testResults, Map<String, String> feedbackMap, String comment, Path filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Evaluation Report");
                contentStream.newLine();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 15);
                contentStream.showText("Student ID: " + studentInfo[2]);
                contentStream.newLine();
                contentStream.showText("Student Name: " + studentInfo[0] + " " + studentInfo[1]);
                contentStream.newLine();
                contentStream.showText("Assignment: " + studentInfo[3]);
                contentStream.newLine();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.showText("======================================");
                contentStream.newLine();
                
                for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
                    String testName = entry.getKey();
                    int score = entry.getValue();

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
    
    // Generates a report for a submission that did not meet submission name convention
    public void generateInvalidReport(String comment, Path filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Evaluation Report");
                contentStream.newLine();
                contentStream.showText(comment);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLine();

                int totalScore = 0;

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

