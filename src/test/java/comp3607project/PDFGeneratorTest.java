package comp3607project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFGeneratorTest {

    private PDFGenerator pdfGenerator;
    private String[] studentInfo;

    {
        studentInfo = new String[3];
        studentInfo[0] = "FirstName";
        studentInfo[1] = "LastName";
        studentInfo[2] = "816000000";
        studentInfo[3] = "A1";
    }

    private Path reportPath;
    private int totalScore = 50;

    @Before
    public void setUp() {
        pdfGenerator = new PDFGenerator();
        reportPath = Path.of("Report.pdf");
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(reportPath);
    }

    @Test
    public void testReportGeneration() throws Exception {
        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("NamingConvention", 10);
        testResults.put("Structure", 15);

        // Add sample feedback for each test
        Map<String, String> feedbackMap = new HashMap<>();
        feedbackMap.put("NamingConvention", "Class name should start with an uppercase letter.");
        feedbackMap.put("Structure", "Method should have a void return type.");

        // Call generateReport with all required arguments
        pdfGenerator.generateReport(studentInfo, totalScore, testResults, feedbackMap, "",reportPath);

        assertTrue(Files.exists(reportPath));
        
        String content = Files.readString(reportPath);
        assertTrue(content.contains("Student ID: " + studentInfo));
        assertTrue(content.contains("NamingConvention: 10"));
        assertTrue(content.contains("Structure: 15"));
        assertTrue(content.contains("Feedback"));  // Verify that feedback is included
    }

    public String[] getStudentInfo() {
        return studentInfo;
    }
}

