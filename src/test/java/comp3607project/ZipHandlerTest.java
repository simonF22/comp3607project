package comp3607project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

public class ZipHandlerTest {

    private ZipHandler zipHandler;
    private Path outputDirectory;

    @Before
    public void setUp() throws Exception {
        zipHandler = new ZipHandler();
        outputDirectory = Path.of("outputDir");
        Files.createDirectories(outputDirectory);
        zipHandler.setOutputDirectory(outputDirectory);
    }

    @After
    public void tearDown() throws Exception {
        Files.walk(outputDirectory)
            .map(Path::toFile)
            .forEach(file -> file.delete());
    }

    @Test
    public void testExtractZipFile() throws Exception {
        Path zipFilePath = Path.of("submissions.zip");

        // Extract ZIP and check for SampleStudent.java
        zipHandler.extractZipFile(zipFilePath);

        Path extractedFile = outputDirectory.resolve("SampleStudent.java");
        assertTrue(Files.exists(extractedFile));
    }
}
