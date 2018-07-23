package rocks.gebsattel.hochzeit.service;

<<<<<<< HEAD
=======
import rocks.gebsattel.hochzeit.domain.AllowControl;

>>>>>>> jhipster_upgrade
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;

import java.util.List;

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

    /**
     * Search for the AllowGroups belonging to the UserExtra.
     *
     * @param userExtraId the Long userId of the UserExtra
     *
     * @param pageable the pagination information
     * @return the list of entities of type "AllowControl" belonging to the given UserExtra userId
     */
    Page<AllowControl> findAllByControlGroupUserId(Long userExtraId, Pageable pageable);

    /**
     * Search for a list of all AllowControls belonging to the UserExtra.
     *
     * @param userExtraId the Long userId of the UserExtra
     *
     * @return the list of entities of type "AllowControl" belonging to the given UserExtra userId
     */
    List<AllowControl> findAllByControlGroupUserId(Long userExtraId);

    /**
     * Get one AllowControl by UserExtraId and Enum "AllowGroup" (ADRESSE, EMAIL, TELEFON).
     *
     * @param userExtraId the Long userId of the UserExtra
     *
     * @return the "AllowControl" belonging to the given UserExtra userId and AllowGroup
     */
    AllowControl findOneByControlGroupUserIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup);

    /**
     * Search for a list of all AllowControls belonging to the UserExtra and Enum "AllowGroup" (ADRESSE, EMAIL, TELEFON).
     *
     * @param userExtraId the Long userId of the UserExtra
     *
     * @return the "AllowControl" belonging to the given UserExtra userId and AllowGroup
     */
    List<AllowControl> findAllByControlledGroupsIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup);

}
