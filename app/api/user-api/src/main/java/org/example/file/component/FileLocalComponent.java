package org.example.file.component;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.file.property.FileRootUrlProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileLocalComponent implements FileComponent {

    private final FileRootUrlProperty fileRootUrlProperty;
    private static final String IMAGE_URL_TEMPLATE = "/profile-image/";
    private static final String IMAGE_RESOURCE_LOCATION = "static/image/profileImage";
    private static final String IMAGE_EXTENSION = ".png";

    /**
     * 이미지 ID를 받아서 URL을 반환하는 메서드
     *
     * @param id 이미지 ID (1 ~ 16)
     * @return 이미지 URL
     */
    @Override
    public Optional<String> getImageUrl(int id) {
        // ID가 범위를 벗어난 경우 빈 값을 반환
        if (id < 1 || id > 16) {
            return Optional.empty();
        }

        return Optional.of(fileRootUrlProperty.rootUrl() + IMAGE_URL_TEMPLATE + id);
    }

    @Override
    @Cacheable(value = "profileImages", key = "#id")
    public Resource getProfileResource(int id) {
        Resource resource;
        try {
            String resourcePath = IMAGE_RESOURCE_LOCATION + id + IMAGE_EXTENSION;
            resource = new UrlResource(
                Objects.requireNonNull(getClass().getClassLoader().getResource(resourcePath))
                    .toURI());
            if (!resource.exists() && !resource.isReadable()) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return resource;
    }


}
