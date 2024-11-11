package comp3607project;

public class ConsoleLoggerObserver implements EvaluationObserver {
    @Override
    public void onEvaluationStart(String className) {
        System.out.println("Starting evaluation of: " + className);
    }

    @Override
    public void onEvaluationComplete(String className, int score) {
        System.out.println("Completed evaluation of " + className + " with score: " + score);
    }
}
