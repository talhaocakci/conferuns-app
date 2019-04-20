package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.TalkHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TalkHistory entity.
 */
public interface TalkHistorySearchRepository extends ElasticsearchRepository<TalkHistory, Long> {
}
