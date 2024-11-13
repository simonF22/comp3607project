package comp3607project;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Integer> weights = new HashMap<>();

    public ScoreCalculator() {
        weights.put("NamingConvention", 20);
        weights.put("Structure", 30);
        weights.put("Inheritance", 50);
    }

    public int calculateScore(Map<String, Integer> testResults) {
        int totalWeight = 0;
        int totalScore = 0;

        for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
            String testName = entry.getKey();
            int score = entry.getValue();
            int weight = weights.getOrDefault(testName, 0);
            
            if(weight>0)
            {
                totalScore = totalScore + (score*weight)/100;
                totalWeight = totalWeight + weight;
            }
        }

        return totalScore;
    }
}

