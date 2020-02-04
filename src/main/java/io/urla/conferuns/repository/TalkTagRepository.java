package io.urla.conferuns.repository;

import io.urla.conferuns.domain.TalkTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TalkTag entity.
 */
@Repository
public interface TalkTagRepository extends JpaRepository<TalkTag, Long> {

    @Query(value = "select distinct talkTag from TalkTag talkTag left join fetch talkTag.talks",
        countQuery = "select count(distinct talkTag) from TalkTag talkTag")
    Page<TalkTag> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct talkTag from TalkTag talkTag left join fetch talkTag.talks")
    List<TalkTag> findAllWithEagerRelationships();

    @Query("select talkTag from TalkTag talkTag left join fetch talkTag.talks where talkTag.id =:id")
    Optional<TalkTag> findOneWithEagerRelationships(@Param("id") Long id);

}
