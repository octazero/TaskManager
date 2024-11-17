package com.amr.TaskManager.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

@Component
public class FileInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Example: Serve files under a specific path
        if (uri.startsWith("/"  )&& !uri.contains("error")) {

            Map<String, MultipartFile> files =((StandardMultipartHttpServletRequest) request).getFileMap();
            files.forEach((name, file) -> {
                System.out.println("File Name: " + file.getResource().getFilename() + ", Size: " + file.getSize());
                File directory = new File("uploads");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file1 = new File(directory.getAbsolutePath(), Objects.requireNonNull(file.getResource().getFilename()));

                try {
                    file.transferTo(file1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
//            ((StandardMultipartHttpServletRequest) request).getMultiFileMap().forEach((key, value) -> {
//                value.forEach((multipartFile) -> {
//                    try {
//
//                        ((StandardMultipartHttpServletRequest.StandardMultipartFile) multipartFile).filename
//                        // Target file location
//                        File files = new File("C:\\Users\\Amr\\Desktop\\taskManager\\TaskManager\\newFile" + File.separator + multipartFile.getName());
//                        files.createNewFile();
//                        multipartFile.transferTo(files);
//
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            });

        }

        return true; // Continue with the request

    }
}
