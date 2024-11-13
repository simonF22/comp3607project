package comp3607project;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ChatBotPlatformStrategy implements EvaluationStrategy {
    private Map<String, Integer> tests;
    private Map<String, String> feedback;

    public ChatBotPlatformStrategy(Map<String, Integer> tests, Map<String, String> feedback) {
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
