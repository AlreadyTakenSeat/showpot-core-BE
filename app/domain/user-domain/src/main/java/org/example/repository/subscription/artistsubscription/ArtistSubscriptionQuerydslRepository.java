package org.example.repository.subscription.artistsubscription;

import java.util.List;
import java.util.UUID;
import org.example.entity.ArtistSubscription;

public interface ArtistSubscriptionQuerydslRepository {

    List<ArtistSubscription> findSubscriptionList(UUID userId);
}