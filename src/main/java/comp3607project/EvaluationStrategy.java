package comp3607project;

import java.util.Map;

public interface EvaluationStrategy {
    void evaluate(Class<?> clazz, Map<String, Integer> tests, Map<String, String> feedback);
    Map<String, String> getFeedback(); 
    Map<String, Integer> getTests();
}

