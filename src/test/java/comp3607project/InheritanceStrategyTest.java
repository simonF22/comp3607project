package comp3607project;

import org.junit.Test;
import static org.junit.Assert.*;

public class InheritanceStrategyTest {

    @Test
    public void testInheritance() {
        InheritanceStrategy inheritanceStrategy = new InheritanceStrategy();

        // Class with no inheritance (no superclass, should fail)
        Class<?> classWithoutInheritance = NoInheritance.class;
        int scoreNoInheritance = inheritanceStrategy.evaluate(classWithoutInheritance);
        String feedbackNoInheritance = inheritanceStrategy.getFeedback();
        // Verify the feedback indicates the lack of inheritance and score is 0
        assertTrue("Feedback should indicate inheritance issues if any", feedbackNoInheritance.contains("Inheritance issue"));
        assertEquals("Score should indicate an issue with inheritance", 0, scoreNoInheritance);

        // Class with inheritance (should pass, has a superclass)
        Class<?> classWithInheritance = ChildClass.class;
        int scoreWithInheritance = inheritanceStrategy.evaluate(classWithInheritance);
        String feedbackWithInheritance = inheritanceStrategy.getFeedback();
        // Verify the feedback indicates correct inheritance and score is 10
        assertTrue("Feedback should indicate inheritance is correct", feedbackWithInheritance.contains("Inheritance is correctly set up"));
        assertEquals("Score should indicate correct inheritance", 10, scoreWithInheritance);
    }

    // A class with no inheritance (directly from Object class)
    public static class NoInheritance {
        // No superclass, will inherit from Object class by default
    }

    // A class that inherits from NoInheritance (valid inheritance)
    public static class ChildClass extends NoInheritance {
        // Inherits from NoInheritance
    }
}

