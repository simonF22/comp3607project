package comp3607project;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JavaEvaluator {

    private Path studentDirectory;

    public void setStudentDirectory(Path studentDirectory) {
        this.studentDirectory = studentDirectory;
    }

    public void inspect() {
        ArrayList<Class<?>> classInstances = new ArrayList<Class<?>>();
        File dir = new File(studentDirectory.toString());
		String[] extensions = new String[] { "java" };

        /*List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        
        for (File file : files) {
            try {
                String className = file.getName().replaceAll(".java", "");

                Class<?> c = Class.forName(className);
                classInstances.add(c);    
                System.out.println("Inspecting class: " + c.getName());
    
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }*/
    }

}