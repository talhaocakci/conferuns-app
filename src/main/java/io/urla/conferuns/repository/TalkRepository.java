package io.urla.conferuns.repository;

import io.urla.conferuns.domain.Talk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Talk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {

}
