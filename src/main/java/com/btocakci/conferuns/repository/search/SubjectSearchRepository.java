package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.Subject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Subject entity.
 */
public interface SubjectSearchRepository extends ElasticsearchRepository<Subject, Long> {
}
