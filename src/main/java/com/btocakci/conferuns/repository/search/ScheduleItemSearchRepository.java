package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.ScheduleItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ScheduleItem entity.
 */
public interface ScheduleItemSearchRepository extends ElasticsearchRepository<ScheduleItem, Long> {
}
