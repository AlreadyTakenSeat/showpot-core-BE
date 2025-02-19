package org.example.file.controller;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.example.file.component.FileLocalComponent;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileLocalComponent fileLocalComponent;

    @GetMapping("/profile-image/{id}")
    public ResponseEntity<Resource> getUserProfileImage(@PathVariable int id) {
        byte[] fileBytes = fileLocalComponent.getProfileImageBytes(id);

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.inline().filename(Arrays.toString(fileBytes)).toString())
            .body(new ByteArrayResource(fileBytes));
    }

}