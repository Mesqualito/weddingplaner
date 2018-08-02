package rocks.gebsattel.hochzeit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;
import rocks.gebsattel.hochzeit.repository.AllowControlRepository;
import rocks.gebsattel.hochzeit.repository.UserExtraRepository;
import rocks.gebsattel.hochzeit.repository.search.AllowControlSearchRepository;
import rocks.gebsattel.hochzeit.security.AuthoritiesConstants;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.AllowControlService;
import rocks.gebsattel.hochzeit.service.UserService;

import java.util.List;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AllowControl.
 */
@Service
@Transactional
public class AllowControlServiceImpl implements AllowControlService {

    private final Logger log = LoggerFactory.getLogger(AllowControlServiceImpl.class);

    private final AllowControlRepository allowControlRepository;

    private final AllowControlSearchRepository allowControlSearchRepository;

    private final UserService userService;

    private final UserExtraRepository userExtraRepository;

    private UserExtra loggedInUserExtra;

    public AllowControlServiceImpl(AllowControlRepository allowControlRepository, AllowControlSearchRepository allowControlSearchRepository,
                                   UserService userService, UserExtraRepository userExtraRepository) {
        this.allowControlRepository = allowControlRepository;
        this.allowControlSearchRepository = allowControlSearchRepository;
        this.userService = userService;
        this.userExtraRepository = userExtraRepository;
    }

    /**
     * Save a allowControl.
     *
     * @param allowControl the entity to save
     * @return the persisted entity
     */
    @Override
    public AllowControl save(AllowControl allowControl) {
        log.debug("Request to save AllowControl : {}", allowControl);
        if(loggedInUserExtra == null) {
            loggedInUserExtra = this.userExtraRepository.findOneByUserLogin(userService.getUserWithAuthorities().get().getLogin());
        }

        /**
         * Add the logged in user into a relation to newly created records of the entity AllowControl.
         * The corresponding userExtra will be the controlGroup of the Message.
         */
        allowControl.setControlGroup(loggedInUserExtra);

        /**
         * group AllowControls by Enum "AllowGroup" (ADRESSE, EMAIL, TELEFON)
         */
        if (allowControl.getControlGroup().equals(loggedInUserExtra)
            && allowControlRepository.findOneByControlGroupUserIdAndAllowGroup(loggedInUserExtra.getId(), allowControl.getAllowGroup()) != null) {
            AllowControl existingAllowControl = allowControlRepository.findOneByControlGroupUserIdAndAllowGroup(loggedInUserExtra.getId(), allowControl.getAllowGroup());
            allowControl.setId(existingAllowControl.getId());
            existingAllowControl.getControlledGroups().forEach((controlledGroup) -> {
                allowControl.addControlledGroup(controlledGroup);
            });
        }

        AllowControl result = allowControlRepository.save(allowControl);

        allowControlSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the allowControls as Page
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllowControl> findAll(Pageable pageable) {

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return allowControlRepository.findAll(pageable);
        } else {
            if(loggedInUserExtra == null) {
                loggedInUserExtra = this.userExtraRepository.findOneByUserLogin(userService.getUserWithAuthorities().get().getLogin());
            }
            return allowControlRepository.findAllByControlGroupUserId(pageable, loggedInUserExtra.getId());
        }
    }

    /**
     * Get all the AllowControl with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AllowControl> findAllWithEagerRelationships(Pageable pageable) {
        return allowControlRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one allowControl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AllowControl> findOne(Long id) {
        log.debug("Request to get AllowControl : {}", id);
        return allowControlRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the allowControl by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllowControl : {}", id);
        allowControlRepository.deleteById(id);
        allowControlSearchRepository.deleteById(id);
    }

    /**
     * Search for the allowControl corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllowControl> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AllowControls for query {}", query);
        Page<AllowControl> result = allowControlSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public Page<AllowControl> findAllByControlGroupUserId(Long userId, Pageable pageable) {
        log.debug("Request to get the paged List of AllowControls  granted by UserExtra userId : {}", userId);
        return allowControlRepository.findAllByControlGroupUserId(pageable, userId);
    }

    @Override
    public List<AllowControl> findAllByControlGroupUserId(Long userId) {
        log.debug("Request to get the List of AllowControls  granted by UserExtra userId : {}", userId);
        return allowControlRepository.findAllByControlGroupUserId(userId);
    }

    @Override
    public AllowControl findOneByControlGroupUserIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup) {
        log.debug("Request to get the AllowControl with ControlGroupUserId : {} and AllowGroup : {}", userExtraId, allowGroup);
        return allowControlRepository.findOneByControlGroupUserIdAndAllowGroup(userExtraId, allowGroup);
    }

    @Override
    public List<AllowControl> findAllByControlledGroupsIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup){
        log.debug("Request to get a list of AllowControls with ControlledGroups containing userExtra userID : {} and AllowGroup : {}", userExtraId, allowGroup);
        return allowControlRepository.findAllByControlledGroupsIdAndAllowGroup(userExtraId, allowGroup);
    }
}
