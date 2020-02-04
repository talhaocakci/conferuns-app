package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.Presenter;
import io.urla.conferuns.repository.PresenterRepository;
import io.urla.conferuns.service.PresenterService;
import io.urla.conferuns.service.dto.PresenterDTO;
import io.urla.conferuns.service.mapper.PresenterMapper;
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
import java.util.List;

import static io.urla.conferuns.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PresenterResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class PresenterResourceIT {

    private static final Long DEFAULT_PRESENTER_ID = 1L;
    private static final Long UPDATED_PRESENTER_ID = 2L;

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
    private PresenterRepository presenterRepository;

    @Autowired
    private PresenterMapper presenterMapper;

    @Autowired
    private PresenterService presenterService;

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

    private MockMvc restPresenterMockMvc;

    private Presenter presenter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PresenterResource presenterResource = new PresenterResource(presenterService);
        this.restPresenterMockMvc = MockMvcBuilders.standaloneSetup(presenterResource)
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
    public static Presenter createEntity(EntityManager em) {
        Presenter presenter = new Presenter()
            .presenterId(DEFAULT_PRESENTER_ID)
            .totalTechnicalPoints(DEFAULT_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(DEFAULT_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(DEFAULT_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(DEFAULT_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(DEFAULT_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(DEFAULT_AVERAGE_EXCITEMENT_POINTS);
        return presenter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presenter createUpdatedEntity(EntityManager em) {
        Presenter presenter = new Presenter()
            .presenterId(UPDATED_PRESENTER_ID)
            .totalTechnicalPoints(UPDATED_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(UPDATED_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(UPDATED_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(UPDATED_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(UPDATED_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(UPDATED_AVERAGE_EXCITEMENT_POINTS);
        return presenter;
    }

    @BeforeEach
    public void initTest() {
        presenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresenter() throws Exception {
        int databaseSizeBeforeCreate = presenterRepository.findAll().size();

        // Create the Presenter
        PresenterDTO presenterDTO = presenterMapper.toDto(presenter);
        restPresenterMockMvc.perform(post("/api/presenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presenterDTO)))
            .andExpect(status().isCreated());

        // Validate the Presenter in the database
        List<Presenter> presenterList = presenterRepository.findAll();
        assertThat(presenterList).hasSize(databaseSizeBeforeCreate + 1);
        Presenter testPresenter = presenterList.get(presenterList.size() - 1);
        assertThat(testPresenter.getPresenterId()).isEqualTo(DEFAULT_PRESENTER_ID);
        assertThat(testPresenter.getTotalTechnicalPoints()).isEqualTo(DEFAULT_TOTAL_TECHNICAL_POINTS);
        assertThat(testPresenter.getAverageTechnicalPoints()).isEqualTo(DEFAULT_AVERAGE_TECHNICAL_POINTS);
        assertThat(testPresenter.getTotalSpeakingPoints()).isEqualTo(DEFAULT_TOTAL_SPEAKING_POINTS);
        assertThat(testPresenter.getAverageSpeakingPoints()).isEqualTo(DEFAULT_AVERAGE_SPEAKING_POINTS);
        assertThat(testPresenter.getTotalExcitementPoints()).isEqualTo(DEFAULT_TOTAL_EXCITEMENT_POINTS);
        assertThat(testPresenter.getAverageExcitementPoints()).isEqualTo(DEFAULT_AVERAGE_EXCITEMENT_POINTS);
    }

    @Test
    @Transactional
    public void createPresenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presenterRepository.findAll().size();

        // Create the Presenter with an existing ID
        presenter.setId(1L);
        PresenterDTO presenterDTO = presenterMapper.toDto(presenter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresenterMockMvc.perform(post("/api/presenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Presenter in the database
        List<Presenter> presenterList = presenterRepository.findAll();
        assertThat(presenterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPresenters() throws Exception {
        // Initialize the database
        presenterRepository.saveAndFlush(presenter);

        // Get all the presenterList
        restPresenterMockMvc.perform(get("/api/presenters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].presenterId").value(hasItem(DEFAULT_PRESENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalTechnicalPoints").value(hasItem(DEFAULT_TOTAL_TECHNICAL_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageTechnicalPoints").value(hasItem(DEFAULT_AVERAGE_TECHNICAL_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpeakingPoints").value(hasItem(DEFAULT_TOTAL_SPEAKING_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageSpeakingPoints").value(hasItem(DEFAULT_AVERAGE_SPEAKING_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalExcitementPoints").value(hasItem(DEFAULT_TOTAL_EXCITEMENT_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].averageExcitementPoints").value(hasItem(DEFAULT_AVERAGE_EXCITEMENT_POINTS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPresenter() throws Exception {
        // Initialize the database
        presenterRepository.saveAndFlush(presenter);

        // Get the presenter
        restPresenterMockMvc.perform(get("/api/presenters/{id}", presenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presenter.getId().intValue()))
            .andExpect(jsonPath("$.presenterId").value(DEFAULT_PRESENTER_ID.intValue()))
            .andExpect(jsonPath("$.totalTechnicalPoints").value(DEFAULT_TOTAL_TECHNICAL_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageTechnicalPoints").value(DEFAULT_AVERAGE_TECHNICAL_POINTS.doubleValue()))
            .andExpect(jsonPath("$.totalSpeakingPoints").value(DEFAULT_TOTAL_SPEAKING_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageSpeakingPoints").value(DEFAULT_AVERAGE_SPEAKING_POINTS.doubleValue()))
            .andExpect(jsonPath("$.totalExcitementPoints").value(DEFAULT_TOTAL_EXCITEMENT_POINTS.doubleValue()))
            .andExpect(jsonPath("$.averageExcitementPoints").value(DEFAULT_AVERAGE_EXCITEMENT_POINTS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPresenter() throws Exception {
        // Get the presenter
        restPresenterMockMvc.perform(get("/api/presenters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresenter() throws Exception {
        // Initialize the database
        presenterRepository.saveAndFlush(presenter);

        int databaseSizeBeforeUpdate = presenterRepository.findAll().size();

        // Update the presenter
        Presenter updatedPresenter = presenterRepository.findById(presenter.getId()).get();
        // Disconnect from session so that the updates on updatedPresenter are not directly saved in db
        em.detach(updatedPresenter);
        updatedPresenter
            .presenterId(UPDATED_PRESENTER_ID)
            .totalTechnicalPoints(UPDATED_TOTAL_TECHNICAL_POINTS)
            .averageTechnicalPoints(UPDATED_AVERAGE_TECHNICAL_POINTS)
            .totalSpeakingPoints(UPDATED_TOTAL_SPEAKING_POINTS)
            .averageSpeakingPoints(UPDATED_AVERAGE_SPEAKING_POINTS)
            .totalExcitementPoints(UPDATED_TOTAL_EXCITEMENT_POINTS)
            .averageExcitementPoints(UPDATED_AVERAGE_EXCITEMENT_POINTS);
        PresenterDTO presenterDTO = presenterMapper.toDto(updatedPresenter);

        restPresenterMockMvc.perform(put("/api/presenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presenterDTO)))
            .andExpect(status().isOk());

        // Validate the Presenter in the database
        List<Presenter> presenterList = presenterRepository.findAll();
        assertThat(presenterList).hasSize(databaseSizeBeforeUpdate);
        Presenter testPresenter = presenterList.get(presenterList.size() - 1);
        assertThat(testPresenter.getPresenterId()).isEqualTo(UPDATED_PRESENTER_ID);
        assertThat(testPresenter.getTotalTechnicalPoints()).isEqualTo(UPDATED_TOTAL_TECHNICAL_POINTS);
        assertThat(testPresenter.getAverageTechnicalPoints()).isEqualTo(UPDATED_AVERAGE_TECHNICAL_POINTS);
        assertThat(testPresenter.getTotalSpeakingPoints()).isEqualTo(UPDATED_TOTAL_SPEAKING_POINTS);
        assertThat(testPresenter.getAverageSpeakingPoints()).isEqualTo(UPDATED_AVERAGE_SPEAKING_POINTS);
        assertThat(testPresenter.getTotalExcitementPoints()).isEqualTo(UPDATED_TOTAL_EXCITEMENT_POINTS);
        assertThat(testPresenter.getAverageExcitementPoints()).isEqualTo(UPDATED_AVERAGE_EXCITEMENT_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPresenter() throws Exception {
        int databaseSizeBeforeUpdate = presenterRepository.findAll().size();

        // Create the Presenter
        PresenterDTO presenterDTO = presenterMapper.toDto(presenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPresenterMockMvc.perform(put("/api/presenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Presenter in the database
        List<Presenter> presenterList = presenterRepository.findAll();
        assertThat(presenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePresenter() throws Exception {
        // Initialize the database
        presenterRepository.saveAndFlush(presenter);

        int databaseSizeBeforeDelete = presenterRepository.findAll().size();

        // Delete the presenter
        restPresenterMockMvc.perform(delete("/api/presenters/{id}", presenter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Presenter> presenterList = presenterRepository.findAll();
        assertThat(presenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
