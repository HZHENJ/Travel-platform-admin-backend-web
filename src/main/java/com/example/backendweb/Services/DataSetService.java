package com.example.backendweb.Services;

import com.example.backendweb.DTO.AttractionDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataSetService {


    private final AttractionService attractionService;

    public DataSetService(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    public List<Object> getDataSet(String dataSet, String sort, String order, String distinct) {
        List<Object> result = null;
        try {

            String urlStr = UriComponentsBuilder.fromUriString("https://api.stb.gov.sg/content/common/v2/search")
                    .queryParam("dataset", dataSet)
                    .queryParamIfPresent("sort", Optional.ofNullable(sort).filter(s -> !s.isEmpty()))
                    .queryParamIfPresent("order", Optional.ofNullable(order).filter(s -> !s.isEmpty()))
                    .queryParamIfPresent("distinct", Optional.ofNullable(distinct).filter(s -> !s.isEmpty()))
                    .toUriString();

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Content-Language", "zh-cn");
            connection.setRequestProperty("X-API-Key", "12eAYGc1VoVpA90r5sq7rpf7Ne9EpsKE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String, Object> jsonMap = gson.fromJson(response.toString(), type);

                List<Map<String, Object>> data = (List<Map<String, Object>>) jsonMap.get("data");
                if (data != null) {
                    switch (dataSet) {
                        case "attractions":
                            List<AttractionDTO> attractionDTOs = data.stream()
                                    .map(this::convertToAttractionDTO)
                                    .collect(Collectors.toList());
                            result = Collections.singletonList(attractionService.saveAttractions(attractionDTOs));
                            break;
                        case "accommodation":
                            break;
                        case "Flights":
                            break;
                        default:
                            System.out.println("未知数据集: " + dataSet);
                    }
                }
            } else {
                System.out.println("GET请求失败: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求失败，可能是由于网络问题或链接不合法。请检查链接的合法性并重试。");
        }
        return result;
    }

    private AttractionDTO convertToAttractionDTO(Map<String, Object> data) {
        AttractionDTO dto = new AttractionDTO();
        dto.setUuid((String) data.get("uuid")); // 设置uuid字段
        dto.setName((String) data.get("name"));
        dto.setDescription((String) data.get("description"));

        // 处理pricing字段，确保其为Map<String, String>
        Object pricingObj = data.get("pricing");
        if (pricingObj instanceof Map) {
            Map<String, String> pricing = new HashMap<>();
            ((Map<?, ?>) pricingObj).forEach((key, value) -> pricing.put((String) key, value.toString()));
            dto.setPricing(pricing);
        } else {
            dto.setPricing(new HashMap<>()); // 提供默认值
        }

        // 处理businessHour字段
        Object businessHourObj = data.get("businessHour");
        if (businessHourObj instanceof List) {
            dto.setBusinessHour((List<Map<String, Object>>) businessHourObj);
        } else {
            dto.setBusinessHour(new ArrayList<>()); // 提供默认值
        }

        // 处理location字段
        Object locationObj = data.get("location");
        if (locationObj instanceof Map) {
            dto.setLocation((Map<String, Double>) locationObj);
        } else {
            dto.setLocation(new HashMap<>()); // 提供默认值
        }

        return dto;
    }
}