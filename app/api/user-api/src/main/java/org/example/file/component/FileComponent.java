package org.example.file.component;

import java.util.Optional;

public interface FileComponent {

    Optional<String> getImageUrl(int id);

    byte[] getProfileImageBytes(int id);
}
