package com.examportal.controllers;

import com.examportal.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping(value = "/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource fileResource = fileService.loadFileAsResource(fileName);

        String contentType = null;
        try{
            contentType = request.getServletContext()
                    .getMimeType(
                            fileResource.getFile().getAbsolutePath()
                    );
        }catch (IOException e){
            e.printStackTrace();
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
}
