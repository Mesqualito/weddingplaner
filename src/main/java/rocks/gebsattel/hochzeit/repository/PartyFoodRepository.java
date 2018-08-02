package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyFood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyFoodRepository extends JpaRepository<PartyFood, Long> {

}
