package com.example.backendweb.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AttractionDTO {
    private String uuid;
    private String name;
    private String description;
    private Map<String, String> pricing;
    private List<Map<String, Object>> businessHour;
    private Map<String, Double> location;
}