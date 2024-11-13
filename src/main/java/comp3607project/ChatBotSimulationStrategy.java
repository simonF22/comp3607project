package comp3607project;

import java.util.Map;

public class ChatBotSimulationStrategy implements EvaluationStrategy {
    private Map<String, Integer> tests;
    private Map<String, String> feedback;

    public ChatBotSimulationStrategy(Map<String, Integer> tests, Map<String, String> feedback) {
        this.tests = tests;
        this.feedback = feedback;
    }
    public void evaluate(Class<?> clazz, Map<String, Integer> tests, Map<String, String> feedback) {
    }

    public Map<String, String> getFeedback() {
        return feedback;
    }

    public Map<String, Integer> getTests() {
        return tests;
    }
}

