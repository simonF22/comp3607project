package comp3607project;

import java.lang.reflect.Method;
import java.util.Map;

public class ChatBotGeneratorStrategy implements EvaluationStrategy {
    private Map<String, Integer> tests;
    private Map<String, String> feedback;

    public ChatBotGeneratorStrategy(Map<String, Integer> tests, Map<String, String> feedback) {
        this.tests = tests;
        this.feedback = feedback;
    }

    public void evaluate(Class<?> clazz, Map<String, Integer> tests, Map<String, String> feedback) {

        for (Method method : clazz.getDeclaredMethods()) {
            if ("generateChatBotLLM".equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == int.class) {
                    if (method.getReturnType() != String.class) {
                        feedback.put("Method - generateChatBotLLM", "Name, return type and parameters correct");
                        tests.put("Method - generateChatBotLLM", 7);
                    } else {
                        feedback.put("Method - generateChatBotLLM", "Name and parameters correct but return type incorrect");
                        tests.put("Method - generateChatBotLLM", 0);
                    }
                } else {
                  feedback.put("Method - generateChatBotLLM", "Method signature failed - name correct but number or type of parameters incorrect");
                  tests.put("Method - generateChatBotLLM", 0);
                }
            }
        }
    }

    public Map<String, String> getFeedback() {
        return feedback;
    }

    public Map<String, Integer> getTests() {
        return tests;
    }
}
