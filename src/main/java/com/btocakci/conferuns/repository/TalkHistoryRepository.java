package com.btocakci.conferuns.repository;

import com.btocakci.conferuns.domain.TalkHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TalkHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalkHistoryRepository extends JpaRepository<TalkHistory, Long> {

}
