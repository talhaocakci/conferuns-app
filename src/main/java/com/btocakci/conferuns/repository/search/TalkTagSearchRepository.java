package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.TalkTag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TalkTag entity.
 */
public interface TalkTagSearchRepository extends ElasticsearchRepository<TalkTag, Long> {
}
