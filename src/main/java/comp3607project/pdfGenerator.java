import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class pdfGenerator {

    public static class results {
        private boolean grade;
        private String feedBack;
        private String test;

        
        public results(boolean grade, String feedBack, String test) {
            this.grade = grade;
            this.feedBack = feedBack;
            this.test = test;
        }

        public boolean getGrade() {
            return grade;
        }

        public String getFeedBack() {
            return feedBack;
        }

        public String getTest() {
            return test;
        }
    }

    public void createPDF(String studentID, List<results> resultList, String path) {
        String filePath = path + " " + studentID + " .pdf"; // Path to where the PDF will be stored (needs to be edited)
        try (PDDocument Doc = new PDDocument()) {
            PDPage page = new PDPage();
            Doc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(Doc, page)) {
                
                contentStream.setFont(PDType1Font.COURIER, 18);  
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.setFont(PDType1Font.COURIER, 12);  
                contentStream.showText("Student ID: " + studentID);
                contentStream.newLine();
                contentStream.newLine();

                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);  
                contentStream.showText("Test ");
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Result");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Feedback");
                contentStream.newLineAtOffset(-300, -20);

                contentStream.setLineWidth(0.5f);
                contentStream.moveTo(50, 710);
                contentStream.lineTo(550, 710);
                contentStream.stroke();

                contentStream.setFont(PDType1Font.COURIER, 12); 
                contentStream.newLineAtOffset(0, -20);

                
                for (results result : resultList) {
                    contentStream.showText(result.getTest());
                    contentStream.newLineAtOffset(200, 0);
                    contentStream.showText(result.getGrade() ? "Pass" : "Fail");
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(result.getGrade() ? "No feedback, test case passed!" : result.getFeedBack());
                    contentStream.newLineAtOffset(-300, -20);
                }
            }

            Doc.save(filePath);  
            System.out.println("PDF generated");

        } catch (IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        }
    }
}