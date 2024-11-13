package comp3607project;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculatorTest {

    @Test
    public void testCalculateScoreWithWeights() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // Simulate test results with pass/fail (1 for pass, 0 for fail)
        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("test1", 30);
        testResults.put("test2", 20);
        testResults.put("test3", 20); 

        // Expected score calculation:
        // NamingConvention: 1 * 20% = 20
        // Structure: 0 * 30% = 0
        // Inheritance: 1 * 50% = 50
        // Total: 20 + 0 + 50 = 70
        int expectedScore = 70;  
        int actualScore = scoreCalculator.calculateScore(testResults);

        // Assert that the actual score matches the expected score
        assertEquals(expectedScore, actualScore);
    }

    @Test
    public void testCalculateScoreWithAllFails() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // Simulate all test results as failures (0 for all tests)
        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("NamingConvention", 0);  // Failed
        testResults.put("Structure", 0);         // Failed
        testResults.put("Inheritance", 0);       // Failed

        // Expected score calculation:
        // All tests failed, so score is 0
        int expectedScore = 0;
        int actualScore = scoreCalculator.calculateScore(testResults);

        // Assert that the actual score matches the expected score
        assertEquals(expectedScore, actualScore);
    }

    @Test
    public void testCalculateScoreWithAllPasses() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // Simulate all test results as passes (1 for all tests)
        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("NamingConvention", 1);  // Passed
        testResults.put("Structure", 1);         // Passed
        testResults.put("Inheritance", 1);       // Passed

        // Expected score calculation:
        // NamingConvention: 1 * 20% = 20
        // Structure: 1 * 30% = 30
        // Inheritance: 1 * 50% = 50
        // Total: 20 + 30 + 50 = 100
        int expectedScore = 100;
        int actualScore = scoreCalculator.calculateScore(testResults);

        // Assert that the actual score matches the expected score
        assertEquals(expectedScore, actualScore);
    }
}



