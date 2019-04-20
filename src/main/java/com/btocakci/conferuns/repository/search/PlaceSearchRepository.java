package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Place;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Place entity.
 */
public interface PlaceSearchRepository extends ElasticsearchRepository<Place, Long> {
}
