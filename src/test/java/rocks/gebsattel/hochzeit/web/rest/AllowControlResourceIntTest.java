package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.repository.AllowControlRepository;
import rocks.gebsattel.hochzeit.service.AllowControlService;
import rocks.gebsattel.hochzeit.repository.search.AllowControlSearchRepository;
import rocks.gebsattel.hochzeit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;
/**
 * Test class for the AllowControlResource REST controller.
 *
 * @see AllowControlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class AllowControlResourceIntTest {

    private static final AllowGroup DEFAULT_ALLOW_GROUP = AllowGroup.ADRESSE;
    private static final AllowGroup UPDATED_ALLOW_GROUP = AllowGroup.EMAIL;

    @Autowired
    private AllowControlRepository allowControlRepository;

    @Autowired
    private AllowControlService allowControlService;

    @Autowired
    private AllowControlSearchRepository allowControlSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAllowControlMockMvc;

    private AllowControl allowControl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllowControlResource allowControlResource = new AllowControlResource(allowControlService);
        this.restAllowControlMockMvc = MockMvcBuilders.standaloneSetup(allowControlResource)
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
    public static AllowControl createEntity(EntityManager em) {
        AllowControl allowControl = new AllowControl()
            .allowGroup(DEFAULT_ALLOW_GROUP);
        return allowControl;
    }

    @Before
    public void initTest() {
        allowControlSearchRepository.deleteAll();
        allowControl = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllowControl() throws Exception {
        int databaseSizeBeforeCreate = allowControlRepository.findAll().size();

        // Create the AllowControl
        restAllowControlMockMvc.perform(post("/api/allow-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowControl)))
            .andExpect(status().isCreated());

        // Validate the AllowControl in the database
        List<AllowControl> allowControlList = allowControlRepository.findAll();
        assertThat(allowControlList).hasSize(databaseSizeBeforeCreate + 1);
        AllowControl testAllowControl = allowControlList.get(allowControlList.size() - 1);
        assertThat(testAllowControl.getAllowGroup()).isEqualTo(DEFAULT_ALLOW_GROUP);

        // Validate the AllowControl in Elasticsearch
        AllowControl allowControlEs = allowControlSearchRepository.findOne(testAllowControl.getId());
        assertThat(allowControlEs).isEqualToIgnoringGivenFields(testAllowControl);
    }

    @Test
    @Transactional
    public void createAllowControlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allowControlRepository.findAll().size();

        // Create the AllowControl with an existing ID
        allowControl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllowControlMockMvc.perform(post("/api/allow-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowControl)))
            .andExpect(status().isBadRequest());

        // Validate the AllowControl in the database
        List<AllowControl> allowControlList = allowControlRepository.findAll();
        assertThat(allowControlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAllowControls() throws Exception {
        // Initialize the database
        allowControlRepository.saveAndFlush(allowControl);

        // Get all the allowControlList
        restAllowControlMockMvc.perform(get("/api/allow-controls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allowControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowGroup").value(hasItem(DEFAULT_ALLOW_GROUP.toString())));
    }

    @Test
    @Transactional
    public void getAllowControl() throws Exception {
        // Initialize the database
        allowControlRepository.saveAndFlush(allowControl);

        // Get the allowControl
        restAllowControlMockMvc.perform(get("/api/allow-controls/{id}", allowControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allowControl.getId().intValue()))
            .andExpect(jsonPath("$.allowGroup").value(DEFAULT_ALLOW_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllowControl() throws Exception {
        // Get the allowControl
        restAllowControlMockMvc.perform(get("/api/allow-controls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllowControl() throws Exception {
        // Initialize the database
        allowControlService.save(allowControl);

        int databaseSizeBeforeUpdate = allowControlRepository.findAll().size();

        // Update the allowControl
        AllowControl updatedAllowControl = allowControlRepository.findOne(allowControl.getId());
        // Disconnect from session so that the updates on updatedAllowControl are not directly saved in db
        em.detach(updatedAllowControl);
        updatedAllowControl
            .allowGroup(UPDATED_ALLOW_GROUP);

        restAllowControlMockMvc.perform(put("/api/allow-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllowControl)))
            .andExpect(status().isOk());

        // Validate the AllowControl in the database
        List<AllowControl> allowControlList = allowControlRepository.findAll();
        assertThat(allowControlList).hasSize(databaseSizeBeforeUpdate);
        AllowControl testAllowControl = allowControlList.get(allowControlList.size() - 1);
        assertThat(testAllowControl.getAllowGroup()).isEqualTo(UPDATED_ALLOW_GROUP);

        // Validate the AllowControl in Elasticsearch
        AllowControl allowControlEs = allowControlSearchRepository.findOne(testAllowControl.getId());
        assertThat(allowControlEs).isEqualToIgnoringGivenFields(testAllowControl);
    }

    @Test
    @Transactional
    public void updateNonExistingAllowControl() throws Exception {
        int databaseSizeBeforeUpdate = allowControlRepository.findAll().size();

        // Create the AllowControl

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAllowControlMockMvc.perform(put("/api/allow-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowControl)))
            .andExpect(status().isCreated());

        // Validate the AllowControl in the database
        List<AllowControl> allowControlList = allowControlRepository.findAll();
        assertThat(allowControlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAllowControl() throws Exception {
        // Initialize the database
        allowControlService.save(allowControl);

        int databaseSizeBeforeDelete = allowControlRepository.findAll().size();

        // Get the allowControl
        restAllowControlMockMvc.perform(delete("/api/allow-controls/{id}", allowControl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean allowControlExistsInEs = allowControlSearchRepository.exists(allowControl.getId());
        assertThat(allowControlExistsInEs).isFalse();

        // Validate the database is empty
        List<AllowControl> allowControlList = allowControlRepository.findAll();
        assertThat(allowControlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAllowControl() throws Exception {
        // Initialize the database
        allowControlService.save(allowControl);

        // Search the allowControl
        restAllowControlMockMvc.perform(get("/api/_search/allow-controls?query=id:" + allowControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allowControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowGroup").value(hasItem(DEFAULT_ALLOW_GROUP.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllowControl.class);
        AllowControl allowControl1 = new AllowControl();
        allowControl1.setId(1L);
        AllowControl allowControl2 = new AllowControl();
        allowControl2.setId(allowControl1.getId());
        assertThat(allowControl1).isEqualTo(allowControl2);
        allowControl2.setId(2L);
        assertThat(allowControl1).isNotEqualTo(allowControl2);
        allowControl1.setId(null);
        assertThat(allowControl1).isNotEqualTo(allowControl2);
    }
}
