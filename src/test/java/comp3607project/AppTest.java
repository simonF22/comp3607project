package comp3607project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    private Path outputDirectory;
    private Path reportPath;
    @SuppressWarnings("FieldMayBeFinal")
    private String studentId = "12345";

    @Before
    public void setUp() throws Exception {
        outputDirectory = Path.of("extractedSubmissions");
        reportPath = Path.of("Report_" + studentId + ".txt");
        Files.createDirectories(outputDirectory);
    }

    @After
    public void tearDown() throws Exception {
        Files.walk(outputDirectory)
            .map(Path::toFile)
            .forEach(file -> file.delete());
        Files.deleteIfExists(reportPath);
    }

    @Test
    public void testFullApplicationWorkflow() throws Exception {
        ZipHandler zipHandler = new ZipHandler();
        zipHandler.setOutputDirectory(outputDirectory);
        Path zipFilePath = Path.of("submissions.zip");

        zipHandler.extractZipFile(zipFilePath);
        assertTrue(Files.list(outputDirectory).findAny().isPresent());

        JavaEvaluator evaluator = new JavaEvaluator();
        evaluator.addObserver(new ConsoleLoggerObserver());

        List<Class<?>> mockClasses = new ArrayList<>();
        mockClasses.add(SampleStudent.class);

        evaluator.inspect(mockClasses, studentId, null);
        assertTrue(Files.exists(reportPath));

        String reportContent = Files.readString(reportPath);
        
        // Verify that the report contains scores and feedback
        assertTrue(reportContent.contains("Total Score"));
        assertTrue(reportContent.contains("Feedback"));
    }
}


