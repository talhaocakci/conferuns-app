package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Room;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Room entity.
 */
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {
}
