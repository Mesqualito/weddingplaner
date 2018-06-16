package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PartyFood.
 */
public interface PartyFoodService {

    /**
     * Save a partyFood.
     *
     * @param partyFood the entity to save
     * @return the persisted entity
     */
    PartyFood save(PartyFood partyFood);

    /**
     * Get all the partyFoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyFood> findAll(Pageable pageable);

    /**
     * Get the "id" partyFood.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PartyFood findOne(Long id);

    /**
     * Delete the "id" partyFood.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the partyFood corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyFood> search(String query, Pageable pageable);
}
