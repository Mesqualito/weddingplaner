package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PartyFood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyFoodRepository extends JpaRepository<PartyFood, Long> {

}
