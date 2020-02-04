package io.urla.conferuns.repository;

import io.urla.conferuns.domain.Fee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

}
