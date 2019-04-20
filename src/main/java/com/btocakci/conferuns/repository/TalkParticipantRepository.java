package com.btocakci.conferuns.repository;

import com.btocakci.conferuns.domain.TalkParticipant;
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
@SuppressWarnings("unused")
@Repository
public interface TalkParticipantRepository extends JpaRepository<TalkParticipant, Long> {

    @Query(value = "select distinct talk_participant from TalkParticipant talk_participant left join fetch talk_participant.talks",
        countQuery = "select count(distinct talk_participant) from TalkParticipant talk_participant")
    Page<TalkParticipant> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct talk_participant from TalkParticipant talk_participant left join fetch talk_participant.talks")
    List<TalkParticipant> findAllWithEagerRelationships();

    @Query("select talk_participant from TalkParticipant talk_participant left join fetch talk_participant.talks where talk_participant.id =:id")
    Optional<TalkParticipant> findOneWithEagerRelationships(@Param("id") Long id);

}
