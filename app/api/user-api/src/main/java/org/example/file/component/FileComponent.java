package org.example.file.component;

import java.util.Optional;
import org.springframework.core.io.Resource;

public interface FileComponent {

    Optional<String> getImageUrl(int id);

    Resource getProfileResource(int id);
}
