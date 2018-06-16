package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.UserExtra;
import rocks.gebsattel.hochzeit.domain.User;
import rocks.gebsattel.hochzeit.domain.PartyFood;
import rocks.gebsattel.hochzeit.repository.UserExtraRepository;
import rocks.gebsattel.hochzeit.service.UserExtraService;
import rocks.gebsattel.hochzeit.repository.search.UserExtraSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserExtraResource REST controller.
 *
 * @see UserExtraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class UserExtraResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_PHONE_NR = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_PHONE_NR = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVATE_PHONE_NR = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATE_PHONE_NR = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NR = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE_NR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GUEST_INVITATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GUEST_INVITATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GUEST_COMMITTED = false;
    private static final Boolean UPDATED_GUEST_COMMITTED = true;

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private UserExtraService userExtraService;

    @Autowired
    private UserExtraSearchRepository userExtraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserExtraResource userExtraResource = new UserExtraResource(userExtraService);
        this.restUserExtraMockMvc = MockMvcBuilders.standaloneSetup(userExtraResource)
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
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .code(DEFAULT_CODE)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .zipCode(DEFAULT_ZIP_CODE)
            .country(DEFAULT_COUNTRY)
            .businessPhoneNr(DEFAULT_BUSINESS_PHONE_NR)
            .privatePhoneNr(DEFAULT_PRIVATE_PHONE_NR)
            .mobilePhoneNr(DEFAULT_MOBILE_PHONE_NR)
            .guestInvitationDate(DEFAULT_GUEST_INVITATION_DATE)
            .guestCommitted(DEFAULT_GUEST_COMMITTED);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userExtra.setUser(user);
        // Add required entity
        PartyFood partyFood = PartyFoodResourceIntTest.createEntity(em);
        em.persist(partyFood);
        em.flush();
        userExtra.getPartyFoods().add(partyFood);
        return userExtra;
    }

    @Before
    public void initTest() {
        userExtraSearchRepository.deleteAll();
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserExtra.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testUserExtra.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testUserExtra.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUserExtra.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testUserExtra.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testUserExtra.getBusinessPhoneNr()).isEqualTo(DEFAULT_BUSINESS_PHONE_NR);
        assertThat(testUserExtra.getPrivatePhoneNr()).isEqualTo(DEFAULT_PRIVATE_PHONE_NR);
        assertThat(testUserExtra.getMobilePhoneNr()).isEqualTo(DEFAULT_MOBILE_PHONE_NR);
        assertThat(testUserExtra.getGuestInvitationDate()).isEqualTo(DEFAULT_GUEST_INVITATION_DATE);
        assertThat(testUserExtra.isGuestCommitted()).isEqualTo(DEFAULT_GUEST_COMMITTED);

        // Validate the UserExtra in Elasticsearch
        UserExtra userExtraEs = userExtraSearchRepository.findOne(testUserExtra.getId());
        assertThat(userExtraEs).isEqualToIgnoringGivenFields(testUserExtra);
    }

    @Test
    @Transactional
    public void createUserExtraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra with an existing ID
        userExtra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtraRepository.findAll().size();
        // set the field null
        userExtra.setCode(null);

        // Create the UserExtra, which fails.

        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].businessPhoneNr").value(hasItem(DEFAULT_BUSINESS_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].privatePhoneNr").value(hasItem(DEFAULT_PRIVATE_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].mobilePhoneNr").value(hasItem(DEFAULT_MOBILE_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].guestInvitationDate").value(hasItem(DEFAULT_GUEST_INVITATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCommitted").value(hasItem(DEFAULT_GUEST_COMMITTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.businessPhoneNr").value(DEFAULT_BUSINESS_PHONE_NR.toString()))
            .andExpect(jsonPath("$.privatePhoneNr").value(DEFAULT_PRIVATE_PHONE_NR.toString()))
            .andExpect(jsonPath("$.mobilePhoneNr").value(DEFAULT_MOBILE_PHONE_NR.toString()))
            .andExpect(jsonPath("$.guestInvitationDate").value(DEFAULT_GUEST_INVITATION_DATE.toString()))
            .andExpect(jsonPath("$.guestCommitted").value(DEFAULT_GUEST_COMMITTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtra() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findOne(userExtra.getId());
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .code(UPDATED_CODE)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY)
            .businessPhoneNr(UPDATED_BUSINESS_PHONE_NR)
            .privatePhoneNr(UPDATED_PRIVATE_PHONE_NR)
            .mobilePhoneNr(UPDATED_MOBILE_PHONE_NR)
            .guestInvitationDate(UPDATED_GUEST_INVITATION_DATE)
            .guestCommitted(UPDATED_GUEST_COMMITTED);

        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtra)))
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserExtra.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testUserExtra.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testUserExtra.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserExtra.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testUserExtra.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUserExtra.getBusinessPhoneNr()).isEqualTo(UPDATED_BUSINESS_PHONE_NR);
        assertThat(testUserExtra.getPrivatePhoneNr()).isEqualTo(UPDATED_PRIVATE_PHONE_NR);
        assertThat(testUserExtra.getMobilePhoneNr()).isEqualTo(UPDATED_MOBILE_PHONE_NR);
        assertThat(testUserExtra.getGuestInvitationDate()).isEqualTo(UPDATED_GUEST_INVITATION_DATE);
        assertThat(testUserExtra.isGuestCommitted()).isEqualTo(UPDATED_GUEST_COMMITTED);

        // Validate the UserExtra in Elasticsearch
        UserExtra userExtraEs = userExtraSearchRepository.findOne(testUserExtra.getId());
        assertThat(userExtraEs).isEqualToIgnoringGivenFields(testUserExtra);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Create the UserExtra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Get the userExtra
        restUserExtraMockMvc.perform(delete("/api/user-extras/{id}", userExtra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userExtraExistsInEs = userExtraSearchRepository.exists(userExtra.getId());
        assertThat(userExtraExistsInEs).isFalse();

        // Validate the database is empty
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserExtra() throws Exception {
        // Initialize the database
        userExtraService.save(userExtra);

        // Search the userExtra
        restUserExtraMockMvc.perform(get("/api/_search/user-extras?query=id:" + userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].businessPhoneNr").value(hasItem(DEFAULT_BUSINESS_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].privatePhoneNr").value(hasItem(DEFAULT_PRIVATE_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].mobilePhoneNr").value(hasItem(DEFAULT_MOBILE_PHONE_NR.toString())))
            .andExpect(jsonPath("$.[*].guestInvitationDate").value(hasItem(DEFAULT_GUEST_INVITATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCommitted").value(hasItem(DEFAULT_GUEST_COMMITTED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtra.class);
        UserExtra userExtra1 = new UserExtra();
        userExtra1.setId(1L);
        UserExtra userExtra2 = new UserExtra();
        userExtra2.setId(userExtra1.getId());
        assertThat(userExtra1).isEqualTo(userExtra2);
        userExtra2.setId(2L);
        assertThat(userExtra1).isNotEqualTo(userExtra2);
        userExtra1.setId(null);
        assertThat(userExtra1).isNotEqualTo(userExtra2);
    }
}
