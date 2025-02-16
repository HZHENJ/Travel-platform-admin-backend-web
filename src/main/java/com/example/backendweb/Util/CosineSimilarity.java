package com.example.backendweb.Util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CosineSimilarity
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/5
 * @Version 1.0
 */

public class CosineSimilarity {
    public static double calculate(Map<Long, Double> vector1, Map<Long, Double> vector2) {
        Set<Long> commonItems = new HashSet<>(vector1.keySet());
        commonItems.retainAll(vector2.keySet());

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Long itemId : commonItems) {
            double v1 = vector1.get(itemId);
            double v2 = vector2.get(itemId);
            dotProduct += v1 * v2;
        }

        for (double value : vector1.values()) {
            norm1 += Math.pow(value, 2);
        }

        for (double value : vector2.values()) {
            norm2 += Math.pow(value, 2);
        }

        if (norm1 <= 0 || norm2 <= 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
