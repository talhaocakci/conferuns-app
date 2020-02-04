package io.urla.conferuns.repository;

import io.urla.conferuns.domain.ScheduleItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScheduleItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {

}
