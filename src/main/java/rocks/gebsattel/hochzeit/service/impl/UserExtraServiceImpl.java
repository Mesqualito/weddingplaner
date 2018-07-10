package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.security.AuthoritiesConstants;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.UserExtraService;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.repository.UserExtraRepository;
import rocks.gebsattel.hochzeit.repository.search.UserExtraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserExtraServiceImpl implements UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraServiceImpl.class);

    private final UserExtraRepository userExtraRepository;

    private final UserExtraSearchRepository userExtraSearchRepository;

    public UserExtraServiceImpl(UserExtraRepository userExtraRepository, UserExtraSearchRepository userExtraSearchRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraSearchRepository = userExtraSearchRepository;
    }

    /**
     * Save a userExtra.
     *
     * @param userExtra the entity to save
     * @return the persisted entity
     */
    @Override
    public UserExtra save(UserExtra userExtra) {
        log.debug("Request to save UserExtra : {}", userExtra);
        UserExtra result = userExtraRepository.save(userExtra);
        userExtraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userExtras.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserExtra> findAll() {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll();
    }

    /**
     * Get one userExtra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserExtra findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return userExtraRepository.findOne(id);
        } else {
            UserExtra userExtra = userExtraRepository.findOne(id);
            if (userExtra.getAllowedUsers().equals(userExtra)) {
                userExtra.getUser().setLastName(userExtra.getUser().getLastName().substring(1, 2) + ". baba");
                return userExtra;
            } else {
                userExtra.getUser().setLastName(userExtra.getUser().getLastName().substring(1, 2) + ". baba");
                return userExtra;
            }
        }
    }

    /**
     * Delete the userExtra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.delete(id);
        userExtraSearchRepository.delete(id);
    }

    /**
     * Search for the userExtra corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserExtra> search(String query) {
        log.debug("Request to search UserExtras for query {}", query);
        return StreamSupport
            .stream(userExtraSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public UserExtra findOneByUserLogin(String login) {
        log.debug("Request to search UserExtras for user {}", login);
        return userExtraRepository.findOneByUserLogin(login);
    }
}
