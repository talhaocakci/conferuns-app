package io.urla.conferuns.repository;

import io.urla.conferuns.domain.FileReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileReviewRepository extends JpaRepository<FileReview, Long> {

}
