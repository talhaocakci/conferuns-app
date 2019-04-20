package com.btocakci.conferuns.repository.search;

import com.btocakci.conferuns.domain.FileReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FileReview entity.
 */
public interface FileReviewSearchRepository extends ElasticsearchRepository<FileReview, Long> {
}
