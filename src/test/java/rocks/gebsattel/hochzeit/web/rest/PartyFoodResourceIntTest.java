package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import rocks.gebsattel.hochzeit.repository.PartyFoodRepository;
import rocks.gebsattel.hochzeit.service.PartyFoodService;
import rocks.gebsattel.hochzeit.repository.search.PartyFoodSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartyFoodResource REST controller.
 *
 * @see PartyFoodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class PartyFoodResourceIntTest {

    private static final String DEFAULT_FOOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FOOD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOOD_SHORT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FOOD_SHORT_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_FOOD_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FOOD_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FOOD_QUANTITY_PERSONS = 1;
    private static final Integer UPDATED_FOOD_QUANTITY_PERSONS = 2;

    private static final Instant DEFAULT_FOOD_BEST_SERVE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FOOD_BEST_SERVE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FOOD_PROPOSAL_ACCEPTED = false;
    private static final Boolean UPDATED_FOOD_PROPOSAL_ACCEPTED = true;

    @Autowired
    private PartyFoodRepository partyFoodRepository;

    @Autowired
    private PartyFoodService partyFoodService;

    @Autowired
    private PartyFoodSearchRepository partyFoodSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartyFoodMockMvc;

    private PartyFood partyFood;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyFoodResource partyFoodResource = new PartyFoodResource(partyFoodService);
        this.restPartyFoodMockMvc = MockMvcBuilders.standaloneSetup(partyFoodResource)
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
    public static PartyFood createEntity(EntityManager em) {
        PartyFood partyFood = new PartyFood()
            .foodName(DEFAULT_FOOD_NAME)
            .foodShortDescription(DEFAULT_FOOD_SHORT_DESCRIPTION)
            .foodLongDescription(DEFAULT_FOOD_LONG_DESCRIPTION)
            .foodQuantityPersons(DEFAULT_FOOD_QUANTITY_PERSONS)
            .foodBestServeTime(DEFAULT_FOOD_BEST_SERVE_TIME)
            .foodProposalAccepted(DEFAULT_FOOD_PROPOSAL_ACCEPTED);
        return partyFood;
    }

    @Before
    public void initTest() {
        partyFoodSearchRepository.deleteAll();
        partyFood = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyFood() throws Exception {
        int databaseSizeBeforeCreate = partyFoodRepository.findAll().size();

        // Create the PartyFood
        restPartyFoodMockMvc.perform(post("/api/party-foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyFood)))
            .andExpect(status().isCreated());

        // Validate the PartyFood in the database
        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeCreate + 1);
        PartyFood testPartyFood = partyFoodList.get(partyFoodList.size() - 1);
        assertThat(testPartyFood.getFoodName()).isEqualTo(DEFAULT_FOOD_NAME);
        assertThat(testPartyFood.getFoodShortDescription()).isEqualTo(DEFAULT_FOOD_SHORT_DESCRIPTION);
        assertThat(testPartyFood.getFoodLongDescription()).isEqualTo(DEFAULT_FOOD_LONG_DESCRIPTION);
        assertThat(testPartyFood.getFoodQuantityPersons()).isEqualTo(DEFAULT_FOOD_QUANTITY_PERSONS);
        assertThat(testPartyFood.getFoodBestServeTime()).isEqualTo(DEFAULT_FOOD_BEST_SERVE_TIME);
        assertThat(testPartyFood.isFoodProposalAccepted()).isEqualTo(DEFAULT_FOOD_PROPOSAL_ACCEPTED);

        // Validate the PartyFood in Elasticsearch
        PartyFood partyFoodEs = partyFoodSearchRepository.findOne(testPartyFood.getId());
        assertThat(partyFoodEs).isEqualToIgnoringGivenFields(testPartyFood);
    }

    @Test
    @Transactional
    public void createPartyFoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyFoodRepository.findAll().size();

        // Create the PartyFood with an existing ID
        partyFood.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyFoodMockMvc.perform(post("/api/party-foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyFood)))
            .andExpect(status().isBadRequest());

        // Validate the PartyFood in the database
        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFoodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyFoodRepository.findAll().size();
        // set the field null
        partyFood.setFoodName(null);

        // Create the PartyFood, which fails.

        restPartyFoodMockMvc.perform(post("/api/party-foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyFood)))
            .andExpect(status().isBadRequest());

        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyFoods() throws Exception {
        // Initialize the database
        partyFoodRepository.saveAndFlush(partyFood);

        // Get all the partyFoodList
        restPartyFoodMockMvc.perform(get("/api/party-foods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyFood.getId().intValue())))
            .andExpect(jsonPath("$.[*].foodName").value(hasItem(DEFAULT_FOOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].foodShortDescription").value(hasItem(DEFAULT_FOOD_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foodLongDescription").value(hasItem(DEFAULT_FOOD_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foodQuantityPersons").value(hasItem(DEFAULT_FOOD_QUANTITY_PERSONS)))
            .andExpect(jsonPath("$.[*].foodBestServeTime").value(hasItem(DEFAULT_FOOD_BEST_SERVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].foodProposalAccepted").value(hasItem(DEFAULT_FOOD_PROPOSAL_ACCEPTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPartyFood() throws Exception {
        // Initialize the database
        partyFoodRepository.saveAndFlush(partyFood);

        // Get the partyFood
        restPartyFoodMockMvc.perform(get("/api/party-foods/{id}", partyFood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyFood.getId().intValue()))
            .andExpect(jsonPath("$.foodName").value(DEFAULT_FOOD_NAME.toString()))
            .andExpect(jsonPath("$.foodShortDescription").value(DEFAULT_FOOD_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.foodLongDescription").value(DEFAULT_FOOD_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.foodQuantityPersons").value(DEFAULT_FOOD_QUANTITY_PERSONS))
            .andExpect(jsonPath("$.foodBestServeTime").value(DEFAULT_FOOD_BEST_SERVE_TIME.toString()))
            .andExpect(jsonPath("$.foodProposalAccepted").value(DEFAULT_FOOD_PROPOSAL_ACCEPTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyFood() throws Exception {
        // Get the partyFood
        restPartyFoodMockMvc.perform(get("/api/party-foods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyFood() throws Exception {
        // Initialize the database
        partyFoodService.save(partyFood);

        int databaseSizeBeforeUpdate = partyFoodRepository.findAll().size();

        // Update the partyFood
        PartyFood updatedPartyFood = partyFoodRepository.findOne(partyFood.getId());
        // Disconnect from session so that the updates on updatedPartyFood are not directly saved in db
        em.detach(updatedPartyFood);
        updatedPartyFood
            .foodName(UPDATED_FOOD_NAME)
            .foodShortDescription(UPDATED_FOOD_SHORT_DESCRIPTION)
            .foodLongDescription(UPDATED_FOOD_LONG_DESCRIPTION)
            .foodQuantityPersons(UPDATED_FOOD_QUANTITY_PERSONS)
            .foodBestServeTime(UPDATED_FOOD_BEST_SERVE_TIME)
            .foodProposalAccepted(UPDATED_FOOD_PROPOSAL_ACCEPTED);

        restPartyFoodMockMvc.perform(put("/api/party-foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyFood)))
            .andExpect(status().isOk());

        // Validate the PartyFood in the database
        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeUpdate);
        PartyFood testPartyFood = partyFoodList.get(partyFoodList.size() - 1);
        assertThat(testPartyFood.getFoodName()).isEqualTo(UPDATED_FOOD_NAME);
        assertThat(testPartyFood.getFoodShortDescription()).isEqualTo(UPDATED_FOOD_SHORT_DESCRIPTION);
        assertThat(testPartyFood.getFoodLongDescription()).isEqualTo(UPDATED_FOOD_LONG_DESCRIPTION);
        assertThat(testPartyFood.getFoodQuantityPersons()).isEqualTo(UPDATED_FOOD_QUANTITY_PERSONS);
        assertThat(testPartyFood.getFoodBestServeTime()).isEqualTo(UPDATED_FOOD_BEST_SERVE_TIME);
        assertThat(testPartyFood.isFoodProposalAccepted()).isEqualTo(UPDATED_FOOD_PROPOSAL_ACCEPTED);

        // Validate the PartyFood in Elasticsearch
        PartyFood partyFoodEs = partyFoodSearchRepository.findOne(testPartyFood.getId());
        assertThat(partyFoodEs).isEqualToIgnoringGivenFields(testPartyFood);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyFood() throws Exception {
        int databaseSizeBeforeUpdate = partyFoodRepository.findAll().size();

        // Create the PartyFood

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartyFoodMockMvc.perform(put("/api/party-foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyFood)))
            .andExpect(status().isCreated());

        // Validate the PartyFood in the database
        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePartyFood() throws Exception {
        // Initialize the database
        partyFoodService.save(partyFood);

        int databaseSizeBeforeDelete = partyFoodRepository.findAll().size();

        // Get the partyFood
        restPartyFoodMockMvc.perform(delete("/api/party-foods/{id}", partyFood.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean partyFoodExistsInEs = partyFoodSearchRepository.exists(partyFood.getId());
        assertThat(partyFoodExistsInEs).isFalse();

        // Validate the database is empty
        List<PartyFood> partyFoodList = partyFoodRepository.findAll();
        assertThat(partyFoodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPartyFood() throws Exception {
        // Initialize the database
        partyFoodService.save(partyFood);

        // Search the partyFood
        restPartyFoodMockMvc.perform(get("/api/_search/party-foods?query=id:" + partyFood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyFood.getId().intValue())))
            .andExpect(jsonPath("$.[*].foodName").value(hasItem(DEFAULT_FOOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].foodShortDescription").value(hasItem(DEFAULT_FOOD_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foodLongDescription").value(hasItem(DEFAULT_FOOD_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foodQuantityPersons").value(hasItem(DEFAULT_FOOD_QUANTITY_PERSONS)))
            .andExpect(jsonPath("$.[*].foodBestServeTime").value(hasItem(DEFAULT_FOOD_BEST_SERVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].foodProposalAccepted").value(hasItem(DEFAULT_FOOD_PROPOSAL_ACCEPTED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyFood.class);
        PartyFood partyFood1 = new PartyFood();
        partyFood1.setId(1L);
        PartyFood partyFood2 = new PartyFood();
        partyFood2.setId(partyFood1.getId());
        assertThat(partyFood1).isEqualTo(partyFood2);
        partyFood2.setId(2L);
        assertThat(partyFood1).isNotEqualTo(partyFood2);
        partyFood1.setId(null);
        assertThat(partyFood1).isNotEqualTo(partyFood2);
    }
}
