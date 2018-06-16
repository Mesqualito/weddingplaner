package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.domain.UserExtra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserExtra.
 */
public interface UserExtraService {

    /**
     * Save a userExtra.
     *
     * @param userExtra the entity to save
     * @return the persisted entity
     */
    UserExtra save(UserExtra userExtra);

    /**
     * Get all the userExtras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserExtra> findAll(Pageable pageable);

    /**
     * Get the "id" userExtra.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserExtra findOne(Long id);

    /**
     * Delete the "id" userExtra.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userExtra corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserExtra> search(String query, Pageable pageable);
}
