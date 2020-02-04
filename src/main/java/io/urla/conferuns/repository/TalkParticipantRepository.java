package io.urla.conferuns.repository;

import io.urla.conferuns.domain.TalkParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TalkParticipant entity.
 */
@Repository
public interface TalkParticipantRepository extends JpaRepository<TalkParticipant, Long> {

    @Query(value = "select distinct talkParticipant from TalkParticipant talkParticipant left join fetch talkParticipant.talks",
        countQuery = "select count(distinct talkParticipant) from TalkParticipant talkParticipant")
    Page<TalkParticipant> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct talkParticipant from TalkParticipant talkParticipant left join fetch talkParticipant.talks")
    List<TalkParticipant> findAllWithEagerRelationships();

    @Query("select talkParticipant from TalkParticipant talkParticipant left join fetch talkParticipant.talks where talkParticipant.id =:id")
    Optional<TalkParticipant> findOneWithEagerRelationships(@Param("id") Long id);

}
