package comp3607project;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;
import org.apache.commons.io.FileUtils;

public class ZipHandler {
    private Path outputDirectory;

    public ZipHandler() {
    }

    public void setOutputDirectory(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    // Method to extract ZIP files and process nested ZIPs
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

        // Process any nested ZIP files
        processNestedZipFiles();
    }

    // Helper method to process nested ZIP files
    private void processNestedZipFiles() {
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

                Files.delete(studentZip); // Delete the nested ZIP file after extraction
            } catch (IOException e) {
                System.err.println("Failed to extract or delete " + studentZip + ": " + e.getMessage());
            }
        }
    }

    // Method to append package declaration to Java files
    public void appendPackageToJavaFiles(Path studentDirectory) {
        File dir = new File(studentDirectory.toString());
        String[] extensions = new String[] { "java" };

        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

        for (File file : files) {
            StringBuilder builder = new StringBuilder();
            String contents = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                contents = builder.toString();
            } catch (IOException e) {
                System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                bw.write("package comp3607project.extractedSubmissions." + studentDirectory.getFileName() + ";");
                bw.newLine();
                bw.write(contents);
            } catch (IOException e) {
                System.err.println("Error writing package declaration to " + file.getName() + ": " + e.getMessage());
            }
        }
    }
} 
