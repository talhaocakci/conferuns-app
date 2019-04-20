package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.TalkParticipant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TalkParticipant entity.
 */
public interface TalkParticipantSearchRepository extends ElasticsearchRepository<TalkParticipant, Long> {
}
