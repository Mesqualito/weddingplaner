package rocks.gebsattel.hochzeit.service.impl;

import org.elasticsearch.common.inject.Inject;
import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.service.PartyFoodService;
import rocks.gebsattel.hochzeit.domain.PartyFood;
import rocks.gebsattel.hochzeit.repository.PartyFoodRepository;
import rocks.gebsattel.hochzeit.repository.search.PartyFoodSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.gebsattel.hochzeit.service.UserExtraService;
import rocks.gebsattel.hochzeit.service.UserService;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PartyFood.
 */
@Service
@Transactional
public class PartyFoodServiceImpl implements PartyFoodService {

    private final Logger log = LoggerFactory.getLogger(PartyFoodServiceImpl.class);

    private final PartyFoodRepository partyFoodRepository;

    private final PartyFoodSearchRepository partyFoodSearchRepository;

    private final UserService userService;

    private final UserExtraService userExtraService;

    public PartyFoodServiceImpl(PartyFoodRepository partyFoodRepository, PartyFoodSearchRepository partyFoodSearchRepository,
                                UserService userService, UserExtraService userExtraService) {
        this.partyFoodRepository = partyFoodRepository;
        this.partyFoodSearchRepository = partyFoodSearchRepository;
        this.userService = userService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a partyFood.
     *
     * @param partyFood the entity to save
     * @return the persisted entity
     */
    @Override
    public PartyFood save(PartyFood partyFood) {
<<<<<<< HEAD
        log.debug("Request to save PartyFood : {}", partyFood);

     /**
     * Add the logged in user into a relation to newly created records of the entity PartyFood.
     * The corresponding userExtra will be the owner of the PartyFood.
     */

        final Optional<User> isUser = userService.getUserWithAuthorities();
        if (!isUser.isPresent()) {
            log.debug("User is not logged in");
        }

        final User user = isUser.get();

        UserExtra userExtra = new UserExtra();
        userExtra = userExtraService.findOneByUserLogin(user.getLogin());
        log.debug("userExtra found : {}", userExtra.getUser());
        partyFood.setUserExtra(userExtra);

        PartyFood result = partyFoodRepository.save(partyFood);
=======
        log.debug("Request to save PartyFood : {}", partyFood);        PartyFood result = partyFoodRepository.save(partyFood);
>>>>>>> jhipster_upgrade
        partyFoodSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the partyFoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyFood> findAll(Pageable pageable) {
        log.debug("Request to get all PartyFoods");
        return partyFoodRepository.findAll(pageable);
    }


    /**
     * Get one partyFood by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyFood> findOne(Long id) {
        log.debug("Request to get PartyFood : {}", id);
        return partyFoodRepository.findById(id);
    }

    /**
     * Delete the partyFood by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyFood : {}", id);
        partyFoodRepository.deleteById(id);
        partyFoodSearchRepository.deleteById(id);
    }

    /**
     * Search for the partyFood corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyFood> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PartyFoods for query {}", query);
        return partyFoodSearchRepository.search(queryStringQuery(query), pageable);    }
}
