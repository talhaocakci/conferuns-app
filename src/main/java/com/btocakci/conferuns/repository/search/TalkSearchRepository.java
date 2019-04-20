package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Talk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Talk entity.
 */
public interface TalkSearchRepository extends ElasticsearchRepository<Talk, Long> {
}
