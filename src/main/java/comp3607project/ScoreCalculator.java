package comp3607project;

import java.util.Map;

public class ScoreCalculator {
    
    public int calculateScore(Map<String, Integer> testResults) {
        int totalScore = 0;

        for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
            String testName = entry.getKey();
            int score = entry.getValue();
            totalScore += (score);
        }

        return totalScore;
    }
}

