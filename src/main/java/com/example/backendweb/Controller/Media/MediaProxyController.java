package com.example.backendweb.Controller.Media;

import com.example.backendweb.Services.Meida.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @ClassName MediaProxyController
 * @Description
 * @Author HUANG ZHENJIA
 * @StudentID A0298312B
 * @Date 2025/2/2
 * @Version 1.0
 */

@RestController
@RequestMapping("/proxy/media")
public class MediaProxyController {

    private final MediaService mediaService;

    @Autowired
    public MediaProxyController(MediaService mediaService){
        this.mediaService = mediaService;
    }

    @GetMapping("/{uuid}")
    public void getMedia(
            @PathVariable("uuid") String uuid,
            @RequestParam(name = "fileType", required = false) String fileType,
            HttpServletResponse response
    ) throws IOException {
        ResponseEntity<byte[]> externalResponse = mediaService.getMediaFile(uuid, fileType);
        if (externalResponse.getStatusCode().is2xxSuccessful() && externalResponse.getBody() != null) {
            // 设置响应的 Content-Type（例如 image/jpg）
            MediaType contentType = externalResponse.getHeaders().getContentType();
            response.setContentType(contentType != null ? contentType.toString() : "application/octet-stream");
            // 将获取到的图片二进制数据写入响应输出流
            StreamUtils.copy(externalResponse.getBody(), response.getOutputStream());
        } else {
            // 当请求失败时返回错误状态码
            response.sendError(externalResponse.getStatusCodeValue(), "Can not get Media File!");
        }
    }
}
