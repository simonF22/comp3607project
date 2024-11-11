package comp3607project;

import org.junit.Test;
import static org.junit.Assert.*;

public class StructureStrategyTest {

    @Test
    public void testStructure() {
        StructureStrategy strategy = new StructureStrategy();

        int score = strategy.evaluate(SampleStudent.class);

        // Calculate the expected score based on the structure rules in StructureStrategy
        int expectedScore = 5; // Assume one correctly structured method with a void return type

        assertEquals("Score should reflect correct method structure", expectedScore, score);

        String feedback = strategy.getFeedback();
        assertTrue("Feedback should indicate structural issues if any", feedback.contains("should have a void return type"));
    }
}
