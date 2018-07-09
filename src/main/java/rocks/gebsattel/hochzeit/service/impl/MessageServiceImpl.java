package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.security.AuthoritiesConstants;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.MessageService;
import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.repository.MessageRepository;
import rocks.gebsattel.hochzeit.repository.search.MessageSearchRepository;
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
import static rocks.gebsattel.hochzeit.domain.PartyFood_.userExtra;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageRepository messageRepository;

    private final MessageSearchRepository messageSearchRepository;

    private final UserService userService;

    private final UserExtraService userExtraService;

    public MessageServiceImpl(MessageRepository messageRepository, MessageSearchRepository messageSearchRepository,
                              UserService userService, UserExtraService userExtraService) {
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
        this.userService = userService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    @Override
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);

        /**
         * Add the logged in user into a relation to newly created records of the entity Message.
         * The corresponding userExtra will be the sender of the Message.
         */
        UserExtra userExtra = this.getUserExtra();
        log.debug("userExtra found : {}", userExtra.getUser().getLogin());
        message.setFrom(userExtra);

        Message result = messageRepository.save(message);
        messageSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Message> findAll(Pageable pageable) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all Messages");
            return messageRepository.findAll(pageable);
        } else {
            UserExtra userExtra = this.getUserExtra();
            log.debug("userExtra found : {}", userExtra.getUser().getLogin());
            return messageRepository.findAllByTos(pageable, userExtra);
        }
    }

    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Message findOne(Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all Messages");
            return messageRepository.findOneWithEagerRelationships(id);
        } else {
            UserExtra userExtra = this.getUserExtra();
            log.debug("userExtra found : {}", userExtra.getUser().getLogin());
            Message returnMessage = messageRepository.findOneWithEagerRelationships(id);
            if(returnMessage.getTos().contains(userExtra)){
                return returnMessage;
            } else
                return null;
        }

    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
        messageSearchRepository.delete(id);
    }

    /**
     * Search for the message corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Message> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Messages for query {}", query);
        Page<Message> result = messageSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    public User getLoggedInUser() {

        final Optional<User> isUser = userService.getUserWithAuthorities();
        if (!isUser.isPresent()) {
            log.debug("User is not logged in");
        }

        final User user = isUser.get();
        return user;
    }

    public UserExtra getUserExtra() {
        return userExtraService.findOneByUserId(this.getLoggedInUser().getId());
    }
}
