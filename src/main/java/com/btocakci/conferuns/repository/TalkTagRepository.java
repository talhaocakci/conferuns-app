package com.btocakci.conferuns.repository;

import com.btocakci.conferuns.domain.TalkTag;
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
@SuppressWarnings("unused")
@Repository
public interface TalkTagRepository extends JpaRepository<TalkTag, Long> {

    @Query(value = "select distinct talk_tag from TalkTag talk_tag left join fetch talk_tag.talks",
        countQuery = "select count(distinct talk_tag) from TalkTag talk_tag")
    Page<TalkTag> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct talk_tag from TalkTag talk_tag left join fetch talk_tag.talks")
    List<TalkTag> findAllWithEagerRelationships();

    @Query("select talk_tag from TalkTag talk_tag left join fetch talk_tag.talks where talk_tag.id =:id")
    Optional<TalkTag> findOneWithEagerRelationships(@Param("id") Long id);

}
