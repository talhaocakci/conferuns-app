package io.urla.conferuns.repository;

import io.urla.conferuns.domain.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Conference entity.
 */
@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    @Query(value = "select distinct conference from Conference conference left join fetch conference.places left join fetch conference.talks",
        countQuery = "select count(distinct conference) from Conference conference")
    Page<Conference> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct conference from Conference conference left join fetch conference.places left join fetch conference.talks")
    List<Conference> findAllWithEagerRelationships();

    @Query("select conference from Conference conference left join fetch conference.places left join fetch conference.talks where conference.id =:id")
    Optional<Conference> findOneWithEagerRelationships(@Param("id") Long id);

}
