package org.example.repository;

import com.example.component.ViewCountComponent;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisViewCountComponent implements ViewCountComponent {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean validateViewCount(UUID showId, String deviceToken) {
        String viewStatus = stringRedisTemplate.opsForValue().get("VC:" + deviceToken + ":" + showId);

        if (viewStatus == null) {
            stringRedisTemplate.opsForValue()
                .set("VC:" + deviceToken + ":" + showId, "1", 1, TimeUnit.DAYS);
            return true;
        }

        return false;
    }
}
