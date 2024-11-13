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

public class ReportGeneratorTest {

    private ReportGenerator reportGenerator;
    @SuppressWarnings("FieldMayBeFinal")
    private String studentId = "12345";
    private Path reportPath;

    @Before
    public void setUp() {
        reportGenerator = new ReportGenerator();
        reportPath = Path.of("Report_" + studentId + ".txt");
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
        reportGenerator.generateReport(studentId, testResults, feedbackMap, "",reportPath);

        assertTrue(Files.exists(reportPath));
        
        String content = Files.readString(reportPath);
        assertTrue(content.contains("Student ID: " + studentId));
        assertTrue(content.contains("NamingConvention: 10"));
        assertTrue(content.contains("Structure: 15"));
        assertTrue(content.contains("Feedback"));  // Verify that feedback is included
    }

    public String getStudentId() {
        return studentId;
    }
}

