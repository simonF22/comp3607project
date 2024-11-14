package comp3607project;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class JavaEvaluator {
    private Path studentDirectory;
    
    private Map<String, Integer> tests;
    private Map<String, String> feedback;

    private EvaluationStrategy chatBotStrategy;
    private EvaluationStrategy chatBotPlatformStrategy;
    private EvaluationStrategy chatBotGeneratorStrategy;

    @SuppressWarnings("FieldMayBeFinal")
    private List<EvaluationObserver> observers = new ArrayList<>();
    @SuppressWarnings("FieldMayBeFinal")
    private ScoreCalculator scoreCalculator = new ScoreCalculator();
    private PDFGenerator pdfGenerator = new PDFGenerator();

    public JavaEvaluator() {
        tests = new HashMap<>();
        tests.put("Attribute - chatBotName", 0);
        tests.put("Attribute - numResponsesGenerated", 0);
        tests.put("Attribute - messageLimit", 0);
        tests.put("Attribute - messageNumber", 0);
        tests.put("Method - ChatBot overloaded constructor", 0);
        tests.put("Method - ChatBot", 0);
        tests.put("Method - getChatBotName", 0);
        tests.put("Method - getNumResponsesGenerated", 0);
        tests.put("Method - getTotalNumResponsesGenerated", 0);
        tests.put("Method - getTotalNumMessagesRemaining", 0);
        tests.put("Method - limitReached", 0);
        tests.put("Method - generateResponse", 0);
        tests.put("Method - promt", 0);
        tests.put("Method - toString", 0);
        tests.put("Attribute - bots", 0);
        tests.put("Method - ChatBotPlatform", 0);
        tests.put("Method - addChatBot", 0);
        tests.put("Method - getChatBotList", 0);
        tests.put("Method - interactWithBot", 0);
        tests.put("Method - generateChatBotLLM", 0);

        feedback = new HashMap<>();
        feedback.put("Attribute - chatBotName", "None");
        feedback.put("Attribute - numResponsesGenerated", "None");
        feedback.put("Attribute - messageLimit", "None");
        feedback.put("Attribute - messageNumber", "None");
        feedback.put("Method - ChatBot overloaded constructor", "None");
        feedback.put("Method - ChatBot", "None");
        feedback.put("Method - getChatBotName", "None");
        feedback.put("Method - getNumResponsesGenerated", "None");
        feedback.put("Method - getTotalNumResponsesGenerated", "None");
        feedback.put("Method - getTotalNumMessagesRemaining", "None");
        feedback.put("Method - limitReached", "None");
        feedback.put("Method - generateResponse", "None");
        feedback.put("Method - promt", "None");
        feedback.put("Method - toString", "None");
        feedback.put("Attribute - bots", "None");
        feedback.put("Method - ChatBotPlatform", "None");
        feedback.put("Method - addChatBot", "None");
        feedback.put("Method - getChatBotList", "None");
        feedback.put("Method - interactWithBot", "None");
        feedback.put("Method - generateChatBotLLM", "None");

        chatBotStrategy = new ChatBotStrategy(tests, feedback);
        chatBotPlatformStrategy = new ChatBotPlatformStrategy(tests, feedback);
        chatBotGeneratorStrategy = new ChatBotGeneratorStrategy(tests, feedback);
    }

    public void setStudentDirectory(Path studentDirectory) {
        this.studentDirectory = studentDirectory;
    }

    public void addObserver(EvaluationObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversOnStart(String className) {
        for (EvaluationObserver observer : observers) {
            observer.onEvaluationStart(className);
        }
    }

    private void notifyObserversOnComplete(String className) {
        for (EvaluationObserver observer : observers) {
            observer.onEvaluationComplete(className);
        }
    }

    public void inspect(List<Class<?>> classInstances, String[] studentInfo, Path subDirectory) {
 
        for (Class<?> clazz : classInstances) {
            int totalScore = 0;
            notifyObserversOnStart(clazz.getSimpleName());

            if (clazz.getName() == "ChatBot"){
                chatBotStrategy.evaluate(clazz, tests, feedback);
                tests = chatBotStrategy.getTests();
                feedback = chatBotStrategy.getFeedback();
            }

            if (clazz.getName() == "ChatBotPlatform"){
                chatBotPlatformStrategy.evaluate(clazz, tests, feedback);
                tests = chatBotPlatformStrategy.getTests();
                feedback = chatBotPlatformStrategy.getFeedback();
            }

            if (clazz.getName() == "ChatBotGenerator"){
                chatBotGeneratorStrategy.evaluate(clazz, tests, feedback);
                tests = chatBotGeneratorStrategy.getTests();
                feedback = chatBotGeneratorStrategy.getFeedback();
            }

            notifyObserversOnComplete(clazz.getSimpleName());
        }

        int totalScore = scoreCalculator.calculateScore(tests);
        pdfGenerator.generateReport(studentInfo, totalScore, tests, feedback, "", subDirectory.resolve("Report.pdf"));
    }

    public List<EvaluationObserver> getObservers() {
        return observers;
    }

    public PDFGenerator getReportGenerator() {
        return pdfGenerator;
    }

    public ScoreCalculator getScoreCalculator() {
        return scoreCalculator;
    }
}

