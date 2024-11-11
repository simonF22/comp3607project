package comp3607project;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NamingConventionStrategy implements EvaluationStrategy {
    private String feedback;

    @Override
    public int evaluate(Class<?> clazz) {
        int correctFields = 0;
        int correctMethods = 0;
        int totalFields = clazz.getDeclaredFields().length;
        int totalMethods = clazz.getDeclaredMethods().length;

        // Check fields for naming conventions
        for (Field field : clazz.getDeclaredFields()) {
            if (isValidFieldName(field)) {
                correctFields++;
            }
        }

        // Check methods for naming conventions
        for (Method method : clazz.getDeclaredMethods()) {
            if (isValidMethodName(method)) {
                correctMethods++;
            }
        }

        // Calculate score based on correct fields and methods
        int totalCorrect = correctFields + correctMethods;
        int totalItems = totalFields + totalMethods;

        // Determine feedback and score based on naming violations
        if (totalCorrect == totalItems) {
            feedback = "Naming conventions are followed correctly.";
            return 20; // Fully correct
        } else if (totalCorrect == 0) {
            feedback = "Naming conventions are violated. Correct all the conventions.";
            return 0;  // Completely incorrect
        } else {
            // Partial compliance: scale score to 20
            double score = (double) totalCorrect / totalItems * 20;
            feedback = "Some conventions are followed. Partial compliance.";
            return (int) score; // Return the scaled score
        }
    }

    @Override
    public String getFeedback() {
        return feedback;
    }

    // Helper method to validate field names
    private boolean isValidFieldName(Field field) {
        String name = field.getName();
        return Character.isLowerCase(name.charAt(0));  // Should start with a lowercase letter
    }

    // Helper method to validate method names
    private boolean isValidMethodName(Method method) {
        String name = method.getName();
        return Character.isLowerCase(name.charAt(0));  // Should start with a lowercase letter
    }
}





