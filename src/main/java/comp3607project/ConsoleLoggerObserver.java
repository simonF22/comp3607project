package comp3607project;

public class ConsoleLoggerObserver implements EvaluationObserver {
    @Override
    public void onEvaluationStart(String className) {
        System.out.println("Starting evaluation of Class: " + className + "...");
    }

    @Override
    public void onEvaluationComplete(String className) {
        System.out.println("...Completed evaluation of Class: " + className);
    }
}
