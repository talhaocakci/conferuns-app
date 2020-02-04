package io.urla.conferuns.repository;

import io.urla.conferuns.domain.TalkHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TalkHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalkHistoryRepository extends JpaRepository<TalkHistory, Long> {

}
