package comp3607project;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class JavaEvaluator {
    private Path studentDirectory;
    @SuppressWarnings("FieldMayBeFinal")
    private List<EvaluationStrategy> strategies = new ArrayList<>();
    @SuppressWarnings("FieldMayBeFinal")
    private List<EvaluationObserver> observers = new ArrayList<>();
    @SuppressWarnings("FieldMayBeFinal")
    private ReportGenerator reportGenerator = new ReportGenerator();
    @SuppressWarnings("FieldMayBeFinal")
    private ScoreCalculator scoreCalculator = new ScoreCalculator();

    public JavaEvaluator() {
        strategies.add(new NamingConventionStrategy());
        strategies.add(new StructureStrategy());
        strategies.add(new InheritanceStrategy()); // New strategy for inheritance checks
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

    private void notifyObserversOnComplete(String className, int score) {
        for (EvaluationObserver observer : observers) {
            observer.onEvaluationComplete(className, score);
        }
    }

    public void inspect(List<Class<?>> classInstances, String studentId) {
        Map<String, Integer> testResults = new HashMap<>();
        Map<String, String> feedbackMap = new HashMap<>(); // Feedback for each test

        for (Class<?> clazz : classInstances) {
            int totalScore = 0;
            notifyObserversOnStart(clazz.getName());

            for (EvaluationStrategy strategy : strategies) {
                int score = strategy.evaluate(clazz);
                String strategyName = strategy.getClass().getSimpleName().replace("Strategy", "");
                testResults.put(strategyName, score);

                // Collect feedback from each strategy
                String feedback = strategy.getFeedback();
                if (feedback != null && !feedback.isEmpty()) {
                    feedbackMap.put(strategyName, feedback);
                }

                totalScore += score;
            }

            notifyObserversOnComplete(clazz.getName(), totalScore);
        }

        int finalScore = scoreCalculator.calculateScore(testResults);
        reportGenerator.generateReport(studentId, testResults, feedbackMap, studentDirectory.resolve("Report_" + studentId + ".txt"));
    }

    public List<EvaluationStrategy> getStrategies() {
        return strategies;
    }

    public List<EvaluationObserver> getObservers() {
        return observers;
    }

    public ReportGenerator getReportGenerator() {
        return reportGenerator;
    }

    public ScoreCalculator getScoreCalculator() {
        return scoreCalculator;
    }
}

