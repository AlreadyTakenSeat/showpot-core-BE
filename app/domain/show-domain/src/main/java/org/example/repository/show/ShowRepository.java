package org.example.repository.show;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.entity.show.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ShowRepository extends JpaRepository<Show, UUID>, ShowQuerydslRepository {

    List<Show> findShowsByIdInAndIsDeletedFalse(List<UUID> showIds);

    Optional<Show> findByIdAndIsDeletedFalse(UUID showId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Show s WHERE s.id = :showId AND s.isDeleted = false")
    Optional<Show> findByIdAndIsDeletedWithLock(UUID showId);
}
