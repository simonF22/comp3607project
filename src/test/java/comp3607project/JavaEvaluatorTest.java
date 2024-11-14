package comp3607project;

import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JavaEvaluatorTest {

    @Test
    public void testEvaluationProcess() throws Exception {
        // Initialize necessary objects
        String[] studentInfo = new String[3];
        studentInfo[0] = "FirstName";
        studentInfo[1] = "LastName";
        studentInfo[2] = "816000000";
        studentInfo[3] = "A1";

        JavaEvaluator evaluator = new JavaEvaluator();
        evaluator.addObserver(new ConsoleLoggerObserver());

        // Check if files exist and are ready for evaluation
        Path reportPath = Path.of("Report.pdf");
        assertFalse("The report file should not exist before evaluation", Files.exists(reportPath));

        // Add classes to mock evaluation
        evaluator.inspect(new ArrayList<>(), studentInfo, null);

        // Ensure the report gets generated
        assertTrue("The report file was not generated", Files.exists(reportPath));
    }
}

