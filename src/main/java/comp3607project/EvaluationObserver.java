package comp3607project;

public interface EvaluationObserver {
    void onEvaluationStart(String className);
    void onEvaluationComplete(String className);
}


