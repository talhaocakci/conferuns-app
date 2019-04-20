package com.btocakci.conferuns.repository;

import com.btocakci.conferuns.domain.Presenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Presenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresenterRepository extends JpaRepository<Presenter, Long> {

}
