package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Conference entity.
 */
public interface ConferenceSearchRepository extends ElasticsearchRepository<Conference, Long> {
}
