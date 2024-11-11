package comp3607project;

import java.lang.reflect.Method;

public class StructureStrategy implements EvaluationStrategy {
    private String feedback = "";

    @Override
    public int evaluate(Class<?> clazz) {
        int score = 0;

        for (Method method : clazz.getDeclaredMethods()) {
            // Example: Check if the return type is void
            if (method.getReturnType() == void.class) {
                score += 5;
            } else {
                feedback += "Method " + method.getName() + " should have a void return type.\n";
            }
        }

        return score;
    }

    @Override
    public String getFeedback() {
        return feedback;
    }
}
