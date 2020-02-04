package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.TalkHistory;
import io.urla.conferuns.repository.TalkHistoryRepository;
import io.urla.conferuns.service.TalkHistoryService;
import io.urla.conferuns.service.dto.TalkHistoryDTO;
import io.urla.conferuns.service.mapper.TalkHistoryMapper;
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

/**
 * Integration tests for the {@link TalkHistoryResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class TalkHistoryResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TOTAL_AUDIENCE = 1L;
    private static final Long UPDATED_TOTAL_AUDIENCE = 2L;

    private static final Double DEFAULT_TOTAL_TECHNICAL_POINTS = 1D;
    private static final Double UPDATED_TOTAL_TECHNICAL_POINTS = 2D;

    private static final Double DEFAULT_AVERAGE_TECHNICAL_POINTS = 1D;
    private static final Double UPDATED_AVERAGE_TECHNICAL_POINTS = 2D;

    private static final Double DEFAULT_TOTAL_SPEAKING_POINTS = 1D;
    private static final Double UPDATED_TOTAL_SPEAKING_POINTS = 2D;

    private static final Double DEFAULT_AVERAGE_SPEAKING_POINTS = 1D;
    private static final Double UPDATED_AVERAGE_SPEAKING_POINTS = 2D;

    private static final Double DEFAULT_TOTAL_EXCITEMENT_POINTS = 1D;
    private static final Double UPDATED_TOTAL_EXCITEMENT_POINTS = 2D;

    private static final Double DEFAULT_AVERAGE_EXCITEMENT_POINTS = 1D;
    private static final Double UPDATED_AVERAGE_EXCITEMENT_POINTS = 2D;

    @Autowired
    private TalkHistoryRepository talkHistoryRepository;

    @Autowired
    private TalkHistoryMapper talkHistoryMapper;

    @Autowired
    private TalkHistoryService talkHistoryService;

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

    private MockMvc restTalkHistoryMockMvc;

    private TalkHistory talkHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TalkHistoryResource talkHistoryResource = new TalkHistoryResource(talkHistoryService);
        this.restTalkHistoryMockMvc = MockMvcBuilders.standaloneSetup(talkHistoryResource)
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
    public static TalkHistory createEntity(EntityManager em) {
        TalkHistory talkHistory = new TalkHistory()
            .date(DEFAULT_DATE)
            .totalAudience(DEFAULT_TOTAL_AUDIENCE)
            .totalTechnicalPoints(DEFAULT_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(DEFAULT_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(DEFAULT_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(DEFAULT_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(DEFAULT_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(DEFAULT_AVERAGE_EXCITEMENT_POINTS);
        return talkHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TalkHistory createUpdatedEntity(EntityManager em) {
        TalkHistory talkHistory = new TalkHistory()
            .date(UPDATED_DATE)
            .totalAudience(UPDATED_TOTAL_AUDIENCE)
            .totalTechnicalPoints(UPDATED_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(UPDATED_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(UPDATED_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(UPDATED_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(UPDATED_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(UPDATED_AVERAGE_EXCITEMENT_POINTS);
        return talkHistory;
    }

    @BeforeEach
    public void initTest() {
        talkHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalkHistory() throws Exception {
        int databaseSizeBeforeCreate = talkHistoryRepository.findAll().size();

        // Create the TalkHistory
        TalkHistoryDTO talkHistoryDTO = talkHistoryMapper.toDto(talkHistory);
        restTalkHistoryMockMvc.perform(post("/api/talk-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TalkHistory in the database
        List<TalkHistory> talkHistoryList = talkHistoryRepository.findAll();
        assertThat(talkHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TalkHistory testTalkHistory = talkHistoryList.get(talkHistoryList.size() - 1);
        assertThat(testTalkHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTalkHistory.getTotalAudience()).isEqualTo(DEFAULT_TOTAL_AUDIENCE);
        assertThat(testTalkHistory.getTotalTechnicalPoints()).isEqualTo(DEFAULT_TOTAL_TECHNICAL_POINTS);
        assertThat(testTalkHistory.getAverageTechnicalPoints()).isEqualTo(DEFAULT_AVERAGE_TECHNICAL_POINTS);
        assertThat(testTalkHistory.getTotalSpeakingPoints()).isEqualTo(DEFAULT_TOTAL_SPEAKING_POINTS);
        assertThat(testTalkHistory.getAverageSpeakingPoints()).isEqualTo(DEFAULT_AVERAGE_SPEAKING_POINTS);
        assertThat(testTalkHistory.getTotalExcitementPoints()).isEqualTo(DEFAULT_TOTAL_EXCITEMENT_POINTS);
        assertThat(testTalkHistory.getAverageExcitementPoints()).isEqualTo(DEFAULT_AVERAGE_EXCITEMENT_POINTS);
    }

    @Test
    @Transactional
    public void createTalkHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talkHistoryRepository.findAll().size();

        // Create the TalkHistory with an existing ID
        talkHistory.setId(1L);
        TalkHistoryDTO talkHistoryDTO = talkHistoryMapper.toDto(talkHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalkHistoryMockMvc.perform(post("/api/talk-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkHistory in the database
        List<TalkHistory> talkHistoryList = talkHistoryRepository.findAll();
        assertThat(talkHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTalkHistories() throws Exception {
        // Initialize the database
        talkHistoryRepository.saveAndFlush(talkHistory);

        // Get all the talkHistoryList
        restTalkHistoryMockMvc.perform(get("/api/talk-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talkHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalAudience").value(hasItem(DEFAULT_TOTAL_AUDIENCE.intValue())))
            .andExpect(jsonPath("$.[*].totalTechnicalPoints").value(hasItem(DEFAULT_TOTAL_TECHNICAL_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageTechnicalPoints").value(hasItem(DEFAULT_AVERAGE_TECHNICAL_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpeakingPoints").value(hasItem(DEFAULT_TOTAL_SPEAKING_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageSpeakingPoints").value(hasItem(DEFAULT_AVERAGE_SPEAKING_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalExcitementPoints").value(hasItem(DEFAULT_TOTAL_EXCITEMENT_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageExcitementPoints").value(hasItem(DEFAULT_AVERAGE_EXCITEMENT_POINTS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTalkHistory() throws Exception {
        // Initialize the database
        talkHistoryRepository.saveAndFlush(talkHistory);

        // Get the talkHistory
        restTalkHistoryMockMvc.perform(get("/api/talk-histories/{id}", talkHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talkHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.totalAudience").value(DEFAULT_TOTAL_AUDIENCE.intValue()))
            .andExpect(jsonPath("$.totalTechnicalPoints").value(DEFAULT_TOTAL_TECHNICAL_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageTechnicalPoints").value(DEFAULT_AVERAGE_TECHNICAL_POINTS.doubleValue()))
            .andExpect(jsonPath("$.totalSpeakingPoints").value(DEFAULT_TOTAL_SPEAKING_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageSpeakingPoints").value(DEFAULT_AVERAGE_SPEAKING_POINTS.doubleValue()))
            .andExpect(jsonPath("$.totalExcitementPoints").value(DEFAULT_TOTAL_EXCITEMENT_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageExcitementPoints").value(DEFAULT_AVERAGE_EXCITEMENT_POINTS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTalkHistory() throws Exception {
        // Get the talkHistory
        restTalkHistoryMockMvc.perform(get("/api/talk-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalkHistory() throws Exception {
        // Initialize the database
        talkHistoryRepository.saveAndFlush(talkHistory);

        int databaseSizeBeforeUpdate = talkHistoryRepository.findAll().size();

        // Update the talkHistory
        TalkHistory updatedTalkHistory = talkHistoryRepository.findById(talkHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTalkHistory are not directly saved in db
        em.detach(updatedTalkHistory);
        updatedTalkHistory
            .date(UPDATED_DATE)
            .totalAudience(UPDATED_TOTAL_AUDIENCE)
            .totalTechnicalPoints(UPDATED_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(UPDATED_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(UPDATED_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(UPDATED_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(UPDATED_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(UPDATED_AVERAGE_EXCITEMENT_POINTS);
        TalkHistoryDTO talkHistoryDTO = talkHistoryMapper.toDto(updatedTalkHistory);

        restTalkHistoryMockMvc.perform(put("/api/talk-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TalkHistory in the database
        List<TalkHistory> talkHistoryList = talkHistoryRepository.findAll();
        assertThat(talkHistoryList).hasSize(databaseSizeBeforeUpdate);
        TalkHistory testTalkHistory = talkHistoryList.get(talkHistoryList.size() - 1);
        assertThat(testTalkHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTalkHistory.getTotalAudience()).isEqualTo(UPDATED_TOTAL_AUDIENCE);
        assertThat(testTalkHistory.getTotalTechnicalPoints()).isEqualTo(UPDATED_TOTAL_TECHNICAL_POINTS);
        assertThat(testTalkHistory.getAverageTechnicalPoints()).isEqualTo(UPDATED_AVERAGE_TECHNICAL_POINTS);
        assertThat(testTalkHistory.getTotalSpeakingPoints()).isEqualTo(UPDATED_TOTAL_SPEAKING_POINTS);
        assertThat(testTalkHistory.getAverageSpeakingPoints()).isEqualTo(UPDATED_AVERAGE_SPEAKING_POINTS);
        assertThat(testTalkHistory.getTotalExcitementPoints()).isEqualTo(UPDATED_TOTAL_EXCITEMENT_POINTS);
        assertThat(testTalkHistory.getAverageExcitementPoints()).isEqualTo(UPDATED_AVERAGE_EXCITEMENT_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTalkHistory() throws Exception {
        int databaseSizeBeforeUpdate = talkHistoryRepository.findAll().size();

        // Create the TalkHistory
        TalkHistoryDTO talkHistoryDTO = talkHistoryMapper.toDto(talkHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalkHistoryMockMvc.perform(put("/api/talk-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkHistory in the database
        List<TalkHistory> talkHistoryList = talkHistoryRepository.findAll();
        assertThat(talkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTalkHistory() throws Exception {
        // Initialize the database
        talkHistoryRepository.saveAndFlush(talkHistory);

        int databaseSizeBeforeDelete = talkHistoryRepository.findAll().size();

        // Delete the talkHistory
        restTalkHistoryMockMvc.perform(delete("/api/talk-histories/{id}", talkHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TalkHistory> talkHistoryList = talkHistoryRepository.findAll();
        assertThat(talkHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
