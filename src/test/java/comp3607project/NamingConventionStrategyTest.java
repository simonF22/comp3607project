package comp3607project;

import org.junit.Test;
import static org.junit.Assert.*;

public class NamingConventionStrategyTest {

    @Test
    public void testFullyCorrectNaming() {
        NamingConventionStrategy strategy = new NamingConventionStrategy();
        int result = strategy.evaluate(SampleStudent.class);
        assertEquals(20, result);
        assertEquals("Naming conventions are followed correctly.", strategy.getFeedback());
    }

    @Test
    public void testPartiallyCorrectNaming() {
        class SampleStudentPartial {
            @SuppressWarnings("unused")
            private int sampleAttribute; // Correct field
            @SuppressWarnings("unused")
            public void SampleMethod() { 
                // Incorrect method name (should start with lowercase)
            }
        }

        NamingConventionStrategy strategy = new NamingConventionStrategy();
        int result = strategy.evaluate(SampleStudentPartial.class);
        assertEquals(10, result);  // Partial compliance (1 correct out of 2)
        assertTrue(strategy.getFeedback().contains("Some conventions are followed. Partial compliance."));
    }

    @Test
    public void testCompletelyIncorrectNaming() {
        class SampleStudentIncorrect {
            @SuppressWarnings({ "unused" })
            private int SampleAttribute; // Incorrect field name (should start with lowercase)
            @SuppressWarnings("unused")
            public void SampleMethod() { 
                // Incorrect method name (should start with lowercase)
            }
        }

        NamingConventionStrategy strategy = new NamingConventionStrategy();
        int result = strategy.evaluate(SampleStudentIncorrect.class);
        assertEquals(0, result);  // Fully incorrect naming conventions
        assertTrue(strategy.getFeedback().contains("Naming conventions are violated. Correct all the conventions."));
    }
}

