package comp3607project;

public class InheritanceStrategy implements EvaluationStrategy {

    private String feedback;

    @Override
    public int evaluate(Class<?> clazz) {
        // Check if the class directly extends Object (i.e., no explicit superclass)
        if (clazz.getSuperclass() == Object.class) {
            feedback = "Inheritance issue: This class directly extends Object. Consider adding meaningful inheritance.";
            return 0; // Return 0 for inheritance issues
        } else {
            feedback = "Inheritance is correctly set up. The class extends: " + clazz.getSuperclass().getName();
            return 10; // Return 10 for correct inheritance
        }
    }

    @Override
    public String getFeedback() {
        return feedback;
    }
}

