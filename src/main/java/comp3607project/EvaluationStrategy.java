package comp3607project;

public interface EvaluationStrategy {
    int evaluate(Class<?> clazz);
    String getFeedback(); // Add this method to match the strategy implementations
}

