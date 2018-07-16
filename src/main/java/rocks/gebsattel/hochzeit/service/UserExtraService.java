package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import java.util.List;

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
     * @return the list of entities
     */
    List<UserExtra> findAll();

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
     * @return the list of entities
     */
    List<UserExtra> search(String query);

    /**
     * Search for the userExtra corresponding to the user.
     *
     * @param login the login of the user
     *
     * @return the userExtra
     */
    UserExtra findOneByUserLogin(String login);

}
