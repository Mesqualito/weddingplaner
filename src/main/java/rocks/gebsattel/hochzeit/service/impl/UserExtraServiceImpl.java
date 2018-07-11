package rocks.gebsattel.hochzeit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.repository.AllowControlRepository;
import rocks.gebsattel.hochzeit.repository.UserExtraRepository;
import rocks.gebsattel.hochzeit.repository.search.UserExtraSearchRepository;
import rocks.gebsattel.hochzeit.security.AuthoritiesConstants;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.UserExtraService;
import rocks.gebsattel.hochzeit.service.UserService;

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

    private final UserService userService;

    private final UserExtraRepository userExtraRepository;

    private final UserExtraSearchRepository userExtraSearchRepository;

    private final AllowControlRepository allowControlRepository;

    public UserExtraServiceImpl(UserService userService, UserExtraRepository userExtraRepository, UserExtraSearchRepository userExtraSearchRepository, AllowControlRepository allowControlRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraSearchRepository = userExtraSearchRepository;
        this.allowControlRepository = allowControlRepository;
        this.userService = userService;
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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return userExtraRepository.findAll();
        } else
        // for all Users in other ROLEs than 'ROLE_ADMIN'
        {
            List<UserExtra> userExtras = userExtraRepository.findAll();

            // Limiting AllowControls-List to the ones belonging to the logged in User / UserExtra
            List<AllowControl> userExtraAllowControls = allowControlRepository
                .findAllByControlGroupUserId(
                    userExtraRepository
                        .findOneByUserLogin(
                            userService.getUserWithAuthorities().get()
                                .getLogin()
                        )
                        .getId()
                );

            // Limiting the data of the UserExtras according to the allow_control and allow_control_controlled_group - join-tables
            userExtraAllowControls.forEach((userExtraAllowControl) -> {

                if (!userExtraAllowControl.getAllowGroup().equals("ADRESSE")) {
                    userExtras.forEach((userExtra) -> {
                        if (!userExtraAllowControl.getControlledGroups().contains(userExtra)) {
                            userExtra.setZipCode("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.getUser().setLastName(userExtra.getUser().getLastName().substring(0, 1) + ".");
                            userExtra.setAddressLine1("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.setAddressLine2("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.setCity("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.setCountry("no permission granted, please ask user to allow it to be shown to you");
                        }
                    });
                }
                if (!userExtraAllowControl.getAllowGroup().equals("EMAIL")) {
                    userExtras.forEach((userExtra) -> {
                        if (!userExtraAllowControl.getControlledGroups().contains(userExtra)) {
                            userExtra.getUser().setEmail("no permission granted, please ask user to allow it to be shown to you");
                        }
                    });
                }

                if (!userExtraAllowControl.getAllowGroup().equals("TELEFON")) {
                    userExtras.forEach((userExtra) -> {
                        if (!userExtraAllowControl.getControlledGroups().contains(userExtra)) {
                            userExtra.setBusinessPhoneNr("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.setMobilePhoneNr("no permission granted, please ask user to allow it to be shown to you");
                            userExtra.setPrivatePhoneNr("no permission granted, please ask user to allow it to be shown to you");
                        }
                    });
                }
            });

            return userExtras;
        }
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
                return userExtra;
            } else {
                userExtra.getUser().setLastName(userExtra.getUser().getLastName().substring(1, 2) + ".");
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
