package org.example.repository.subscription.genresubscription;

import java.util.List;
import java.util.UUID;
import org.example.entity.usershow.GenreSubscription;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreSubscriptionRepository extends JpaRepository<GenreSubscription, UUID>,
    GenreSubscriptionQuerydslRepository {

    List<GenreSubscription> findAllByUserId(UUID userId);

    List<GenreSubscription> findByUserIdAndIsDeletedFalse(UUID userId);

    Long countByUserIdAndIsDeletedFalse(UUID uuid);

    void deleteAllByUserId(UUID userId);
}
