package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.Fee;
import io.urla.conferuns.repository.FeeRepository;
import io.urla.conferuns.service.FeeService;
import io.urla.conferuns.service.dto.FeeDTO;
import io.urla.conferuns.service.mapper.FeeMapper;
import io.urla.conferuns.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.urla.conferuns.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.urla.conferuns.domain.enumeration.SpecialParticipantType;
/**
 * Integration tests for the {@link FeeResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class FeeResourceIT {

    private static final Long DEFAULT_CONFERENCE_ID = 1L;
    private static final Long UPDATED_CONFERENCE_ID = 2L;

    private static final String DEFAULT_FEE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FEE_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TILL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TILL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final SpecialParticipantType DEFAULT_SPECIAL_TO = SpecialParticipantType.STUDENT;
    private static final SpecialParticipantType UPDATED_SPECIAL_TO = SpecialParticipantType.DISABLE;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private FeeMapper feeMapper;

    @Autowired
    private FeeService feeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFeeMockMvc;

    private Fee fee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeeResource feeResource = new FeeResource(feeService);
        this.restFeeMockMvc = MockMvcBuilders.standaloneSetup(feeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fee createEntity(EntityManager em) {
        Fee fee = new Fee()
            .conferenceId(DEFAULT_CONFERENCE_ID)
            .feeLabel(DEFAULT_FEE_LABEL)
            .fromTime(DEFAULT_FROM_TIME)
            .tillTime(DEFAULT_TILL_TIME)
            .price(DEFAULT_PRICE)
            .specialTo(DEFAULT_SPECIAL_TO);
        return fee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fee createUpdatedEntity(EntityManager em) {
        Fee fee = new Fee()
            .conferenceId(UPDATED_CONFERENCE_ID)
            .feeLabel(UPDATED_FEE_LABEL)
            .fromTime(UPDATED_FROM_TIME)
            .tillTime(UPDATED_TILL_TIME)
            .price(UPDATED_PRICE)
            .specialTo(UPDATED_SPECIAL_TO);
        return fee;
    }

    @BeforeEach
    public void initTest() {
        fee = createEntity(em);
    }

    @Test
    @Transactional
    public void createFee() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);
        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isCreated());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate + 1);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getConferenceId()).isEqualTo(DEFAULT_CONFERENCE_ID);
        assertThat(testFee.getFeeLabel()).isEqualTo(DEFAULT_FEE_LABEL);
        assertThat(testFee.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testFee.getTillTime()).isEqualTo(DEFAULT_TILL_TIME);
        assertThat(testFee.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFee.getSpecialTo()).isEqualTo(DEFAULT_SPECIAL_TO);
    }

    @Test
    @Transactional
    public void createFeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // Create the Fee with an existing ID
        fee.setId(1L);
        FeeDTO feeDTO = feeMapper.toDto(fee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFees() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get all the feeList
        restFeeMockMvc.perform(get("/api/fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fee.getId().intValue())))
            .andExpect(jsonPath("$.[*].conferenceId").value(hasItem(DEFAULT_CONFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].feeLabel").value(hasItem(DEFAULT_FEE_LABEL)))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].tillTime").value(hasItem(DEFAULT_TILL_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].specialTo").value(hasItem(DEFAULT_SPECIAL_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", fee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fee.getId().intValue()))
            .andExpect(jsonPath("$.conferenceId").value(DEFAULT_CONFERENCE_ID.intValue()))
            .andExpect(jsonPath("$.feeLabel").value(DEFAULT_FEE_LABEL))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.tillTime").value(DEFAULT_TILL_TIME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.specialTo").value(DEFAULT_SPECIAL_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFee() throws Exception {
        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee
        Fee updatedFee = feeRepository.findById(fee.getId()).get();
        // Disconnect from session so that the updates on updatedFee are not directly saved in db
        em.detach(updatedFee);
        updatedFee
            .conferenceId(UPDATED_CONFERENCE_ID)
            .feeLabel(UPDATED_FEE_LABEL)
            .fromTime(UPDATED_FROM_TIME)
            .tillTime(UPDATED_TILL_TIME)
            .price(UPDATED_PRICE)
            .specialTo(UPDATED_SPECIAL_TO);
        FeeDTO feeDTO = feeMapper.toDto(updatedFee);

        restFeeMockMvc.perform(put("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getConferenceId()).isEqualTo(UPDATED_CONFERENCE_ID);
        assertThat(testFee.getFeeLabel()).isEqualTo(UPDATED_FEE_LABEL);
        assertThat(testFee.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testFee.getTillTime()).isEqualTo(UPDATED_TILL_TIME);
        assertThat(testFee.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFee.getSpecialTo()).isEqualTo(UPDATED_SPECIAL_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingFee() throws Exception {
        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeMockMvc.perform(put("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeDelete = feeRepository.findAll().size();

        // Delete the fee
        restFeeMockMvc.perform(delete("/api/fees/{id}", fee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
