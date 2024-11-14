package comp3607project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PDFGeneratorTest {

    private PDFGenerator pdfGenerator;
    private String[] studentInfo;

    {
        studentInfo = new String[4];
        studentInfo[0] = "FirstName";
        studentInfo[1] = "LastName";
        studentInfo[2] = "816000000";
        studentInfo[3] = "A1";
    }

    private Path reportPath;
    private File reportFile;
    private int totalScore = 50;

    @Before
    public void setUp() throws Exception{
        pdfGenerator = new PDFGenerator();
        reportPath = Files.createTempFile("report_", ".pdf");
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(reportPath);
    }

    @Test
    public void testReportGeneration() throws Exception {
        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("Test method", 5);
        testResults.put("Test field", 7);

        // Add sample feedback for each test
        Map<String, String> feedbackMap = new HashMap<>();
        feedbackMap.put("Test method", "correct name and return type");
        feedbackMap.put("Test field", "incorrect type");

        // Call generateReport with all required arguments
        pdfGenerator.generateReport(studentInfo, totalScore, testResults, feedbackMap, "",reportPath);

        assertTrue(Files.exists(reportPath));
    }

    public String[] getStudentInfo() {
        return studentInfo;
    }
}

