package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Presenter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Presenter entity.
 */
public interface PresenterSearchRepository extends ElasticsearchRepository<Presenter, Long> {
}
