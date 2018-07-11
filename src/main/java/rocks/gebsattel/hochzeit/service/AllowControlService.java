package rocks.gebsattel.hochzeit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocks.gebsattel.hochzeit.domain.AllowControl;

import java.awt.print.Pageable;
import java.util.List;

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
     * Get the "id" allowControl.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AllowControl findOne(Long id);

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

    /**
     * Search for the AllowGroups belonging to the UserExtra.
     *
     * @param userId the Long userId of the UserExtra
     * @return the list of entities of type "AllowControl" belonging to the given UserExtra userId
     */
    List<AllowControl> findAllByControlGroupUserId(Long userId);
}
