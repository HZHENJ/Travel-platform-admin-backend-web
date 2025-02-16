package com.example.backendweb.Services.Meida;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName MediaService
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/2
 * @Version 1.0
 */

@Service
public class MediaService {

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${api.key}")
    private String apiKey;

    // 创建 RestTemplate 实例
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 根据媒体 UUID 和可选的 fileType 参数调用 TIH 的 Download Media File 接口，
     * 并返回 ResponseEntity<byte[]>，其中 byte[] 是图片的二进制数据。
     *
     * @param uuid 媒体的 UUID
     * @param fileType 可选参数，指定需要下载的文件类型（例如 "Small Thumbnail"）
     * @return ResponseEntity<byte[]> 返回包含图片二进制数据的响应
     */
    public ResponseEntity<byte[]> getMediaFile(String uuid, String fileType) {
        try {

            String url = apiBaseUrl + "/media/download/v2/" + uuid;
            if (fileType != null && !fileType.isEmpty()) {
                url += "?fileType=" + URLEncoder.encode(fileType, StandardCharsets.UTF_8.toString());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("X-API-Key", apiKey);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // 发起 GET 请求，返回二进制数据
            ResponseEntity<byte[]> externalResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    byte[].class
            );

            return externalResponse;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 当编码出现异常时，返回内部错误
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
