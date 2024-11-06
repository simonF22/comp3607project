package comp3607project;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class ZipHandler {
    private Path outputDirectory;

    public ZipHandler() {
    }

    public void setOutputDirectory(Path outputDirectory){
        this.outputDirectory = outputDirectory;
    }

    public void extractZipFile(Path zipFilePath) {
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                Path newPath = outputDirectory.resolve(entry.getName());
    
                if (entry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    Files.createDirectories(newPath.getParent());
                    Files.copy(zipIn, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipIn.closeEntry();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while extracting the ZIP file: " + e.getMessage());
            return;
        }

        List<Path> zipFiles = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(outputDirectory, "*.zip")) {
            for (Path path : directoryStream) {
                zipFiles.add(path);
            }
        } catch (IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
            return;
        }
    
        for (Path studentZip : zipFiles) {
            try {
                String studentFolderName = studentZip.getFileName().toString().replace(".zip", "");
                Path studentFolder = outputDirectory.resolve(studentFolderName);

                ZipHandler studentSubmissionExtractor = new ZipHandler();
                studentSubmissionExtractor.setOutputDirectory(studentFolder);
                studentSubmissionExtractor.extractZipFile(studentZip);
                
                Files.delete(studentZip);
            } catch (IOException e) {
                System.err.println("Failed to extract or delete " + studentZip + ": " + e.getMessage());
            }
        }
    }
}