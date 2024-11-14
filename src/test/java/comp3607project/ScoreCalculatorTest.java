package comp3607project;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculatorTest {

    @Test
    public void testCalculateScore() {
    
        ScoreCalculator calculator = new ScoreCalculator();

        Map<String, Integer> testResults = new HashMap<>();
        testResults.put("test1", 10);
        testResults.put("test2", 20);
        testResults.put("test3", 30);

        int result = calculator.calculateScore(testResults);

        assertEquals(60, result);

        // Test an empty map
        testResults.clear();
        result = calculator.calculateScore(testResults);
        assertEquals(0, result);

        // Test a map with a single entry
        testResults.put("test4", 40);
        result = calculator.calculateScore(testResults);
        assertEquals(40, result);
    }
}



