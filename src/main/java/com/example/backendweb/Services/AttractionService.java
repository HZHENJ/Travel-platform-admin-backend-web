package com.example.backendweb.Services;

import com.example.backendweb.Entity.Info.Attraction;
import com.example.backendweb.DTO.AttractionDTO;
import com.example.backendweb.Repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    public List<Attraction> saveAttractions(List<AttractionDTO> attractionDTOs) {
        List<Attraction> attractions = new ArrayList<>();
        List<Attraction> newAttractions = new ArrayList<>();

        for (AttractionDTO dto : attractionDTOs) {
            Attraction attraction = new Attraction();
            attraction.setAttractionName(dto.getName());
            attraction.setLocation(dto.getLocation().get("latitude") + ", " + dto.getLocation().get("longitude"));
            attraction.setDescription(dto.getDescription());
            attraction.setTicketType(dto.getPricing());
            attraction.setOpeningHours(convertBusinessHour(dto.getBusinessHour()));
            attraction.setTicketAvailability(generateRandomTicketAvailability(dto.getPricing()));
            attraction.setUuid(dto.getUuid()); // 设置uuid字段

            attractions.add(attraction);

            // 检查数据库中是否已经存在相同的记录
            if (!attractionRepository.existsByUuid(dto.getUuid())) {
                newAttractions.add(attraction);
            } else {
                System.out.println("记录已存在，跳过插入: " + dto.getUuid());
            }
        }

        if (!newAttractions.isEmpty()) {
            attractionRepository.saveAll(newAttractions);
        }

        // 返回完整的Attraction列表
        return attractions;
    }

    private Map<String, String> convertBusinessHour(List<Map<String, Object>> businessHour) {
        Map<String, String> openingHours = new HashMap<>();
        for (Map<String, Object> hour : businessHour) {
            String day = (String) hour.get("day");
            String openTime = (String) hour.get("openTime");
            String closeTime = (String) hour.get("closeTime");
            openingHours.put(day, openTime + " - " + closeTime);
        }
        return openingHours;
    }

    private Map<String, Integer> generateRandomTicketAvailability(Map<String, String> ticketType) {
        Map<String, Integer> ticketAvailability = new HashMap<>();
        Random random = new Random();
        for (String ticket : ticketType.keySet()) {
            ticketAvailability.put(ticket, random.nextInt(100) + 1); // 随机生成1到100之间的票数
        }
        return ticketAvailability;
    }
}
