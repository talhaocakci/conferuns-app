package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Fee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fee entity.
 */
public interface FeeSearchRepository extends ElasticsearchRepository<Fee, Long> {
}
