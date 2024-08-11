package org.example.repository.show.showartist;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.example.entity.artist.QArtist.artist;
import static org.example.entity.show.QShow.show;
import static org.example.entity.show.QShowArtist.showArtist;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.artist.response.ArtistKoreanNameDomainResponse;
import org.example.dto.artist.response.ArtistKoreanNamesWithShowIdDomainResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShowArtistQuerydslRepositoryImpl implements ShowArtistQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ArtistKoreanNamesWithShowIdDomainResponse> findArtistKoreanNamesWithShowId() {
        return jpaQueryFactory.selectFrom(showArtist)
            .join(show).on(show.id.eq(showArtist.showId), show.isDeleted.isFalse())
            .join(artist).on(artist.id.eq(showArtist.artistId), artist.isDeleted.isFalse())
            .where(showArtist.isDeleted.isFalse())
            .transform(
                groupBy(showArtist.id).list(
                    Projections.constructor(
                        ArtistKoreanNamesWithShowIdDomainResponse.class,
                        show.id,
                        list(
                            Projections.constructor(
                                ArtistKoreanNameDomainResponse.class,
                                artist.id,
                                artist.koreanName
                            )
                        )
                    )
                )
            );
    }
}