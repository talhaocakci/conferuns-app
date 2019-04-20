package com.btocakci.conferuns.repository;

import com.btocakci.conferuns.domain.FileReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileReviewRepository extends JpaRepository<FileReview, Long> {

}
