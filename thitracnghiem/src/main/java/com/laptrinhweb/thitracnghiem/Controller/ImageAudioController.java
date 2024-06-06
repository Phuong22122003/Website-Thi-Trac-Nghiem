package com.laptrinhweb.thitracnghiem.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/get-file")
public class ImageAudioController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/image/{imageName:.+}")
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        Path imagePath = Paths.get(uploadDir).resolve(imageName);
        // Path imagePath =
        // Paths.get(uploadDir).resolve("1717520689339illustration-gallery-icon_53876-27002.png");

        if (!Files.exists(imagePath)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            System.out.println("========================");
            System.out.println(imageName);
            return;
        }
        // Đặt loại hình ảnh trong tiêu đề
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        // Đọc hình ảnh vào InputStream và ghi vào OutputStream của HttpServletResponse
        try (InputStream inputStream = new FileInputStream(imagePath.toFile())) {
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        }
        return;
    }

    @GetMapping("/audio/{audioName:.+}")
    public void getAudio(@PathVariable String audioName, HttpServletResponse response) throws IOException {
        Path audioPath = Paths.get(uploadDir).resolve(audioName);
        System.out.println("============================");
        System.out.println("vao day");
        // Kiểm tra xem tệp âm thanh có tồn tại không
        if (!Files.exists(audioPath)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        String contentType = Files.probeContentType(audioPath);
        if (contentType != null) {
            response.setContentType(contentType);
        } else {
            // Nếu không thể xác định được loại MIME, sử dụng loại MIME mặc định cho file âm
            // thanh
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }

        // Đọc âm thanh vào InputStream và ghi vào OutputStream của HttpServletResponse
        try (InputStream inputStream = new FileInputStream(audioPath.toFile())) {
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}
