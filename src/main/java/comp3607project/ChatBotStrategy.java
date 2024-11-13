package comp3607project;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ChatBotStrategy implements EvaluationStrategy{
    private Map<String, Integer> tests;
    private Map<String, String> feedback;

    public ChatBotStrategy(Map<String, Integer> tests, Map<String, String> feedback) {
        this.tests = tests;
        this.feedback = feedback;
    }


    public void evaluate(Class<?> clazz, Map<String, Integer> tests, Map<String, String> feedback) {

        try {
            Field field = clazz.getDeclaredField("chatBotName");
            if (field.getType().equals(String.class)){
                tests.put("Attribute - chatBotName", 1);
                feedback.put("Attribute - chatBotName", "correct name and type");
            }
            else {
                tests.put("Attribute - chatBotName", 0);
                feedback.put("Attribute - chatBotName", "has correct name but NOT the correct type");
            }
        } catch (NoSuchFieldException e) {
            tests.put("Attribute - chatBotName", 0);
            feedback.put("Attribute - chatBotName", "does not exist");
        }

        try {
            Field field = clazz.getDeclaredField("numResponsesGenerated");
            if (field.getType().equals(int.class)){
                tests.put("Attribute - numResponsesGenerated", 1);
                feedback.put("Attribute - numResponsesGenerated", "has correct name and type");
            }
            else {
                tests.put("Attribute - numResponsesGenerated", 0);
                feedback.put("Attribute - numResponsesGenerated", "has correct name but NOT the correct type");
            }
        } catch (NoSuchFieldException e) {
            tests.put("Attribute - numResponsesGenerated", 0);
            feedback.put("Attribute - numResponsesGenerated", "does not exist");
            
        }


        try {
            Field field = clazz.getDeclaredField("messageLimit");
            if (Modifier.isFinal(field.getModifiers())) {
                if (field.getType().equals(int.class)) {
                        tests.put("Attribute - messageLimit", 3);
                        feedback.put("Attribute - messageLimit", "correct");
                } else {
                    tests.put("Attribute - messageLimit", 0);
                    feedback.put("Attribute - messageLimit", "has correct name and modifier but NOT the correct type.");
                }
            } else {
                tests.put("Attribute - messageLimit", 0);
                feedback.put("Attribute - messageLimit", "has correct name but modifier NOT final.");
            }

        } catch (NoSuchFieldException e) {
            tests.put("Attribute - messageLimit", 0);
            feedback.put("Attribute - messageLimit", "does not exist");

        }

        try {
            Field field = clazz.getDeclaredField("messageNumber");
            if (field.getType().equals(int.class)){
                field.setAccessible(true); 
                tests.put("Attribute - messageNumber", 2);
                feedback.put("Attribute - messageNumber", "correct");
            }
            else {
                tests.put("Attribute - messageNumber", 0);
                feedback.put("Attribute - messageNumber", "has correct name but NOT the correct type");
            }
        } catch (NoSuchFieldException e) {
            tests.put("Attribute - messageNumber", 0);
            feedback.put("Attribute - messageNumber", "does not exist");
        }
    }

    public Map<String, String> getFeedback() {
        return feedback;
    }

    public Map<String, Integer> getTests() {
        return tests;
    }

}
