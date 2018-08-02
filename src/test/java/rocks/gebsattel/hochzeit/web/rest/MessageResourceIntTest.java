package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.repository.MessageRepository;
import rocks.gebsattel.hochzeit.repository.search.MessageSearchRepository;
import rocks.gebsattel.hochzeit.service.MessageService;
import rocks.gebsattel.hochzeit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class MessageResourceIntTest {

    private static final String DEFAULT_MESSAGE_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_MESSAGE_INIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MESSAGE_INIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MESSAGE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_TEXT = "BBBBBBBBBB";

    private static final Instant DEFAULT_MESSAGE_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MESSAGE_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MESSAGE_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MESSAGE_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private MessageRepository messageRepository;
    @Mock
    private MessageRepository messageRepositoryMock;
    
    @Mock
    private MessageService messageServiceMock;

    @Autowired
    private MessageService messageService;

    /**
     * This repository is mocked in the rocks.gebsattel.hochzeit.repository.search test package.
     *
     * @see rocks.gebsattel.hochzeit.repository.search.MessageSearchRepositoryMockConfiguration
     */
    @Autowired
    private MessageSearchRepository mockMessageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageMockMvc;

    private Message message;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageResource messageResource = new MessageResource(messageService);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .messageShortDescription(DEFAULT_MESSAGE_SHORT_DESCRIPTION)
            .messageInitTime(DEFAULT_MESSAGE_INIT_TIME)
            .messageText(DEFAULT_MESSAGE_TEXT)
            .messageValidFrom(DEFAULT_MESSAGE_VALID_FROM)
            .messageValidUntil(DEFAULT_MESSAGE_VALID_UNTIL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        UserExtra userExtra = UserExtraResourceIntTest.createEntity(em);
        em.persist(userExtra);
        em.flush();
        message.getTos().add(userExtra);
        // Add required entity
        message.setFrom(userExtra);
        return message;
    }

    @Before
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessageShortDescription()).isEqualTo(DEFAULT_MESSAGE_SHORT_DESCRIPTION);
        assertThat(testMessage.getMessageInitTime()).isEqualTo(DEFAULT_MESSAGE_INIT_TIME);
        assertThat(testMessage.getMessageText()).isEqualTo(DEFAULT_MESSAGE_TEXT);
        assertThat(testMessage.getMessageValidFrom()).isEqualTo(DEFAULT_MESSAGE_VALID_FROM);
        assertThat(testMessage.getMessageValidUntil()).isEqualTo(DEFAULT_MESSAGE_VALID_UNTIL);
        assertThat(testMessage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMessage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).save(testMessage);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(0)).save(message);
    }

    @Test
    @Transactional
    public void checkMessageShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setMessageShortDescription(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageShortDescription").value(hasItem(DEFAULT_MESSAGE_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].messageInitTime").value(hasItem(DEFAULT_MESSAGE_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].messageText").value(hasItem(DEFAULT_MESSAGE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].messageValidFrom").value(hasItem(DEFAULT_MESSAGE_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].messageValidUntil").value(hasItem(DEFAULT_MESSAGE_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    public void getAllMessagesWithEagerRelationshipsIsEnabled() throws Exception {
        MessageResource messageResource = new MessageResource(messageServiceMock);
        when(messageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMessageMockMvc.perform(get("/api/messages?eagerload=true"))
        .andExpect(status().isOk());

        verify(messageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllMessagesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MessageResource messageResource = new MessageResource(messageServiceMock);
            when(messageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMessageMockMvc.perform(get("/api/messages?eagerload=true"))
        .andExpect(status().isOk());

            verify(messageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.messageShortDescription").value(DEFAULT_MESSAGE_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.messageInitTime").value(DEFAULT_MESSAGE_INIT_TIME.toString()))
            .andExpect(jsonPath("$.messageText").value(DEFAULT_MESSAGE_TEXT.toString()))
            .andExpect(jsonPath("$.messageValidFrom").value(DEFAULT_MESSAGE_VALID_FROM.toString()))
            .andExpect(jsonPath("$.messageValidUntil").value(DEFAULT_MESSAGE_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageService.save(message);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMessageSearchRepository);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .messageShortDescription(UPDATED_MESSAGE_SHORT_DESCRIPTION)
            .messageInitTime(UPDATED_MESSAGE_INIT_TIME)
            .messageText(UPDATED_MESSAGE_TEXT)
            .messageValidFrom(UPDATED_MESSAGE_VALID_FROM)
            .messageValidUntil(UPDATED_MESSAGE_VALID_UNTIL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessage)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessageShortDescription()).isEqualTo(UPDATED_MESSAGE_SHORT_DESCRIPTION);
        assertThat(testMessage.getMessageInitTime()).isEqualTo(UPDATED_MESSAGE_INIT_TIME);
        assertThat(testMessage.getMessageText()).isEqualTo(UPDATED_MESSAGE_TEXT);
        assertThat(testMessage.getMessageValidFrom()).isEqualTo(UPDATED_MESSAGE_VALID_FROM);
        assertThat(testMessage.getMessageValidUntil()).isEqualTo(UPDATED_MESSAGE_VALID_UNTIL);
        assertThat(testMessage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMessage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).save(testMessage);
    }

    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(0)).save(message);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageService.save(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Get the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).deleteById(message.getId());
    }

    @Test
    @Transactional
    public void searchMessage() throws Exception {
        // Initialize the database
        messageService.save(message);
        when(mockMessageSearchRepository.search(queryStringQuery("id:" + message.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(message), PageRequest.of(0, 1), 1));
        // Search the message
        restMessageMockMvc.perform(get("/api/_search/messages?query=id:" + message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageShortDescription").value(hasItem(DEFAULT_MESSAGE_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].messageInitTime").value(hasItem(DEFAULT_MESSAGE_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].messageText").value(hasItem(DEFAULT_MESSAGE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].messageValidFrom").value(hasItem(DEFAULT_MESSAGE_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].messageValidUntil").value(hasItem(DEFAULT_MESSAGE_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = new Message();
        message1.setId(1L);
        Message message2 = new Message();
        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);
        message2.setId(2L);
        assertThat(message1).isNotEqualTo(message2);
        message1.setId(null);
        assertThat(message1).isNotEqualTo(message2);
    }
}
