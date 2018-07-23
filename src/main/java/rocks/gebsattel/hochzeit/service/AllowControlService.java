package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.domain.AllowControl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AllowControl.
 */
public interface AllowControlService {

    /**
     * Save a allowControl.
     *
     * @param allowControl the entity to save
     * @return the persisted entity
     */
    AllowControl save(AllowControl allowControl);

    /**
     * Get all the allowControls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AllowControl> findAll(Pageable pageable);

    /**
     * Get all the AllowControl with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<AllowControl> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" allowControl.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AllowControl> findOne(Long id);

    /**
     * Delete the "id" allowControl.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the allowControl corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AllowControl> search(String query, Pageable pageable);
}
