package com.example.backendweb.Controller.Recommendation;

import com.example.backendweb.Entity.Info.Attraction;
import com.example.backendweb.Services.Recommandation.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName RecommendationController
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/5
 * @Version 1.0
 */

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/popular") // Popular recommendations
    public List<Attraction> getPopularRecommendations() {
        return recommendationService.getPopularAttractions();
    }

    @GetMapping("/personalized/{userId}") // Personalized recommendations
    public List<Attraction> getPersonalizedRecommendations(@PathVariable Integer userId) {
        return recommendationService.getPersonalizedRecommendations(userId);
    }

    @GetMapping("/combined/{userId}") // Comprehensive recommendation
    public List<Attraction> getCombinedRecommendations(@PathVariable Integer userId) {
        return recommendationService.getCombinedRecommendations(userId);
    }
}
