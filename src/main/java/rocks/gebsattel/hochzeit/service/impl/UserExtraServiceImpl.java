package rocks.gebsattel.hochzeit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;
import rocks.gebsattel.hochzeit.repository.AllowControlRepository;
import rocks.gebsattel.hochzeit.repository.UserExtraRepository;
import rocks.gebsattel.hochzeit.repository.search.UserExtraSearchRepository;
import rocks.gebsattel.hochzeit.security.AuthoritiesConstants;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.UserExtraService;
import rocks.gebsattel.hochzeit.service.UserService;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
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

    private final List<UserExtra> userExtras;

    private final List<UserExtra> userExtraShadows;

    public UserExtraServiceImpl(UserService userService, UserExtraRepository userExtraRepository, UserExtraSearchRepository userExtraSearchRepository, AllowControlRepository allowControlRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraSearchRepository = userExtraSearchRepository;
        this.allowControlRepository = allowControlRepository;
        this.userService = userService;

        userExtras = this.userExtraRepository.findAll();
        userExtraShadows = new ArrayList<>();

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
        // ------------ for all Users in other ROLEs than 'ROLE_ADMIN' ------------
        {
            userExtras.forEach((userExtra) -> {
                this.hideAllData(userExtra);
            });

            UserExtra loggedInUserExtra = this.getLoggedInUserExtra();
            List<AllowControl> userExtraAllowControls = allowControlRepository.findAll();

            // Unlock data of the UserExtra-Objects in the list
            // according to the allow_control and allow_control_controlled_group - join-tables
            userExtraAllowControls.forEach((userExtraAllowControl) -> {
                if (userExtraAllowControl.getAllowGroup().ADRESSE.equals(userExtraAllowControl.getAllowGroup().valueOf("ADRESSE"))) {
                    userExtras.forEach((userExtra) -> {
                        if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                            && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                            this.showAddressData(userExtra);
                        }
                    });
                }

                if (userExtraAllowControl.getAllowGroup().EMAIL.equals(userExtraAllowControl.getAllowGroup().valueOf("EMAIL"))) {
                    userExtras.forEach((userExtra) -> {
                        if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                            && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                            this.showEmailData(userExtra);
                        }
                    });
                }

                if (userExtraAllowControl.getAllowGroup().TELEFON.equals(userExtraAllowControl.getAllowGroup().valueOf("TELEFON"))) {
                    userExtras.forEach((userExtra) -> {
                        if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                            && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                            this.showPhoneData(userExtra);
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
        } else
        // ------------ for all Users in other ROLEs than 'ROLE_ADMIN' ------------
        {
            UserExtra loggedInUserExtra = this.getLoggedInUserExtra();
            UserExtra userExtra = userExtraRepository.findOne(id);
            this.hideAllData(userExtra);

            List<AllowControl> allowControls = allowControlRepository.findAll();

            // Unlock data of the UserExtra-Object in the list
            // according to the allow_control and allow_control_controlled_group - join-tables
            allowControls.forEach((allowControl) -> {

                if (allowControlRepository.findAllByControlledGroupsIdAndAllowGroup(loggedInUserExtra.getId(), allowControl.getControlGroup().valueOf("ADRESSE")
                        .contains(allowControl))) {
                    if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                        && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                        this.showAddressData(userExtra);
                    }
                }

                if (userExtraAllowControl.getAllowGroup().EMAIL.equals(userExtraAllowControl.getAllowGroup().valueOf("EMAIL"))) {
                    if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                        && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                        this.showEmailData(userExtra);
                    }
                }

                if (userExtraAllowControl.getAllowGroup().TELEFON.equals(userExtraAllowControl.getAllowGroup().valueOf("TELEFON"))) {
                    if (userExtraAllowControl.getControlledGroups().contains(loggedInUserExtra)
                        && userExtraAllowControl.getControlGroup().equals(userExtra)) {
                        this.showPhoneData(userExtra);
                    }
                }
            });
            return userExtra;
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

    private UserExtra hideAllData(UserExtra userExtra) {

        UserExtra loggedInUserExtra = this.getLoggedInUserExtra();
        UserExtra userExtraShadow = userExtra.copyForAllowControl();
        userExtraShadows.add(userExtraShadow);

        // address-related fields
        userExtra.getUser().setLastName(userExtra.getUser().getLastName().substring(0, 1) + ".");
        userExtra.setAddressLine1("no permission granted");
        userExtra.setAddressLine2("no permission granted");
        userExtra.setZipCode("no permission granted");
        userExtra.setCity("no permission granted");
        userExtra.setCountry("no permission granted");
        // eMail-related field
        userExtra.getUser().setEmail("no permission granted");
        // phone-related fields
        userExtra.setBusinessPhoneNr("no permission granted");
        userExtra.setMobilePhoneNr("no permission granted");
        userExtra.setPrivatePhoneNr("no permission granted");
        // the rest of the fields
        userExtra.setAgeGroup(null);
        userExtra.setCode(null);
        userExtra.setGender(null);
        userExtra.setGuestInvitationDate(null);
        userExtra.setGuestCommitted(null);

        // Own data is shown
        if (userExtra.equals(loggedInUserExtra)) {
            userExtra.getUser().setFirstName(userExtraShadow.getUser().getFirstName());
            userExtra.getUser().setLastName(userExtraShadow.getUser().getLastName());
            userExtra.getUser().setEmail(userExtraShadow.getUser().getEmail());
            userExtra.setAddressLine1(userExtraShadow.getAddressLine1());
            userExtra.setAddressLine2(userExtraShadow.getAddressLine2());
            userExtra.setZipCode(userExtraShadow.getZipCode());
            userExtra.setCity(userExtraShadow.getCity());
            userExtra.setCountry(userExtraShadow.getCountry());
            userExtra.setAgeGroup(userExtraShadow.getAgeGroup());
            userExtra.setCode(userExtraShadow.getCode());
            userExtra.setGender(userExtra.getGender());
        }
        return userExtra;
    }

    private UserExtra showAddressData(UserExtra userExtra) {

        UserExtra userExtraShadow = userExtraShadows.get(userExtraShadows.indexOf(userExtra));
        userExtra.getUser().setFirstName(userExtraShadow.getUser().getFirstName());
        userExtra.getUser().setLastName(userExtraShadow.getUser().getLastName());
        userExtra.setAddressLine1(userExtraShadow.getAddressLine1());
        userExtra.setAddressLine2(userExtraShadow.getAddressLine2());
        userExtra.setZipCode(userExtraShadow.getZipCode());
        userExtra.setCity(userExtraShadow.getCity());
        userExtra.setCountry(userExtraShadow.getCountry());

        return userExtra;
    }

    private UserExtra showEmailData(UserExtra userExtra) {

        UserExtra userExtraShadow = userExtraShadows.get(userExtraShadows.indexOf(userExtra));
        userExtra.getUser().setEmail((userExtraShadow.getUser().getEmail()));

        return userExtra;
    }

    private UserExtra showPhoneData(UserExtra userExtra) {

        UserExtra userExtraShadow = userExtraShadows.get(userExtraShadows.indexOf(userExtra));
        userExtra.setBusinessPhoneNr(userExtraShadow.getBusinessPhoneNr());
        userExtra.setMobilePhoneNr(userExtraShadow.getMobilePhoneNr());
        userExtra.setPrivatePhoneNr(userExtraShadow.getPrivatePhoneNr());

        return userExtra;
    }

    private UserExtra getLoggedInUserExtra() {

        UserExtra loggedInUserExtra = userExtraRepository
            .findOneByUserLogin(
                userService.getUserWithAuthorities().get()
                    .getLogin()
            );

        return loggedInUserExtra;

    }

}
