package com.btocakci.conferuns.web.rest;

import com.btocakci.conferuns.ConferunsApp;

import com.btocakci.conferuns.domain.TalkParticipant;
import com.btocakci.conferuns.repository.TalkParticipantRepository;
import com.btocakci.conferuns.repository.search.TalkParticipantSearchRepository;
import com.btocakci.conferuns.service.TalkParticipantService;
import com.btocakci.conferuns.service.dto.TalkParticipantDTO;
import com.btocakci.conferuns.service.mapper.TalkParticipantMapper;
import com.btocakci.conferuns.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.btocakci.conferuns.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TalkParticipantResource REST controller.
 *
 * @see TalkParticipantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferunsApp.class)
public class TalkParticipantResourceIntTest {

    private static final Boolean DEFAULT_CHECKED_IN = false;
    private static final Boolean UPDATED_CHECKED_IN = true;

    private static final Boolean DEFAULT_PLANNED_TO_GO = false;
    private static final Boolean UPDATED_PLANNED_TO_GO = true;

    private static final Boolean DEFAULT_FAVORITED = false;
    private static final Boolean UPDATED_FAVORITED = true;

    @Autowired
    private TalkParticipantRepository talkParticipantRepository;

    @Mock
    private TalkParticipantRepository talkParticipantRepositoryMock;

    @Autowired
    private TalkParticipantMapper talkParticipantMapper;

    @Mock
    private TalkParticipantService talkParticipantServiceMock;

    @Autowired
    private TalkParticipantService talkParticipantService;

    /**
     * This repository is mocked in the com.btocakci.conferuns.repository.search test package.
     *
     * @see com.btocakci.conferuns.repository.search.TalkParticipantSearchRepositoryMockConfiguration
     */
    @Autowired
    private TalkParticipantSearchRepository mockTalkParticipantSearchRepository;

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

    private MockMvc restTalkParticipantMockMvc;

    private TalkParticipant talkParticipant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TalkParticipantResource talkParticipantResource = new TalkParticipantResource(talkParticipantService);
        this.restTalkParticipantMockMvc = MockMvcBuilders.standaloneSetup(talkParticipantResource)
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
    public static TalkParticipant createEntity(EntityManager em) {
        TalkParticipant talkParticipant = new TalkParticipant()
            .checkedIn(DEFAULT_CHECKED_IN)
            .plannedToGo(DEFAULT_PLANNED_TO_GO)
            .favorited(DEFAULT_FAVORITED);
        return talkParticipant;
    }

    @Before
    public void initTest() {
        talkParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalkParticipant() throws Exception {
        int databaseSizeBeforeCreate = talkParticipantRepository.findAll().size();

        // Create the TalkParticipant
        TalkParticipantDTO talkParticipantDTO = talkParticipantMapper.toDto(talkParticipant);
        restTalkParticipantMockMvc.perform(post("/api/talk-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the TalkParticipant in the database
        List<TalkParticipant> talkParticipantList = talkParticipantRepository.findAll();
        assertThat(talkParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        TalkParticipant testTalkParticipant = talkParticipantList.get(talkParticipantList.size() - 1);
        assertThat(testTalkParticipant.isCheckedIn()).isEqualTo(DEFAULT_CHECKED_IN);
        assertThat(testTalkParticipant.isPlannedToGo()).isEqualTo(DEFAULT_PLANNED_TO_GO);
        assertThat(testTalkParticipant.isFavorited()).isEqualTo(DEFAULT_FAVORITED);

        // Validate the TalkParticipant in Elasticsearch
        verify(mockTalkParticipantSearchRepository, times(1)).save(testTalkParticipant);
    }

    @Test
    @Transactional
    public void createTalkParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talkParticipantRepository.findAll().size();

        // Create the TalkParticipant with an existing ID
        talkParticipant.setId(1L);
        TalkParticipantDTO talkParticipantDTO = talkParticipantMapper.toDto(talkParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalkParticipantMockMvc.perform(post("/api/talk-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkParticipant in the database
        List<TalkParticipant> talkParticipantList = talkParticipantRepository.findAll();
        assertThat(talkParticipantList).hasSize(databaseSizeBeforeCreate);

        // Validate the TalkParticipant in Elasticsearch
        verify(mockTalkParticipantSearchRepository, times(0)).save(talkParticipant);
    }

    @Test
    @Transactional
    public void getAllTalkParticipants() throws Exception {
        // Initialize the database
        talkParticipantRepository.saveAndFlush(talkParticipant);

        // Get all the talkParticipantList
        restTalkParticipantMockMvc.perform(get("/api/talk-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talkParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkedIn").value(hasItem(DEFAULT_CHECKED_IN.booleanValue())))
            .andExpect(jsonPath("$.[*].plannedToGo").value(hasItem(DEFAULT_PLANNED_TO_GO.booleanValue())))
            .andExpect(jsonPath("$.[*].favorited").value(hasItem(DEFAULT_FAVORITED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTalkParticipantsWithEagerRelationshipsIsEnabled() throws Exception {
        TalkParticipantResource talkParticipantResource = new TalkParticipantResource(talkParticipantServiceMock);
        when(talkParticipantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTalkParticipantMockMvc = MockMvcBuilders.standaloneSetup(talkParticipantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTalkParticipantMockMvc.perform(get("/api/talk-participants?eagerload=true"))
        .andExpect(status().isOk());

        verify(talkParticipantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTalkParticipantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TalkParticipantResource talkParticipantResource = new TalkParticipantResource(talkParticipantServiceMock);
            when(talkParticipantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTalkParticipantMockMvc = MockMvcBuilders.standaloneSetup(talkParticipantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTalkParticipantMockMvc.perform(get("/api/talk-participants?eagerload=true"))
        .andExpect(status().isOk());

            verify(talkParticipantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTalkParticipant() throws Exception {
        // Initialize the database
        talkParticipantRepository.saveAndFlush(talkParticipant);

        // Get the talkParticipant
        restTalkParticipantMockMvc.perform(get("/api/talk-participants/{id}", talkParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talkParticipant.getId().intValue()))
            .andExpect(jsonPath("$.checkedIn").value(DEFAULT_CHECKED_IN.booleanValue()))
            .andExpect(jsonPath("$.plannedToGo").value(DEFAULT_PLANNED_TO_GO.booleanValue()))
            .andExpect(jsonPath("$.favorited").value(DEFAULT_FAVORITED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTalkParticipant() throws Exception {
        // Get the talkParticipant
        restTalkParticipantMockMvc.perform(get("/api/talk-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalkParticipant() throws Exception {
        // Initialize the database
        talkParticipantRepository.saveAndFlush(talkParticipant);

        int databaseSizeBeforeUpdate = talkParticipantRepository.findAll().size();

        // Update the talkParticipant
        TalkParticipant updatedTalkParticipant = talkParticipantRepository.findById(talkParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedTalkParticipant are not directly saved in db
        em.detach(updatedTalkParticipant);
        updatedTalkParticipant
            .checkedIn(UPDATED_CHECKED_IN)
            .plannedToGo(UPDATED_PLANNED_TO_GO)
            .favorited(UPDATED_FAVORITED);
        TalkParticipantDTO talkParticipantDTO = talkParticipantMapper.toDto(updatedTalkParticipant);

        restTalkParticipantMockMvc.perform(put("/api/talk-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the TalkParticipant in the database
        List<TalkParticipant> talkParticipantList = talkParticipantRepository.findAll();
        assertThat(talkParticipantList).hasSize(databaseSizeBeforeUpdate);
        TalkParticipant testTalkParticipant = talkParticipantList.get(talkParticipantList.size() - 1);
        assertThat(testTalkParticipant.isCheckedIn()).isEqualTo(UPDATED_CHECKED_IN);
        assertThat(testTalkParticipant.isPlannedToGo()).isEqualTo(UPDATED_PLANNED_TO_GO);
        assertThat(testTalkParticipant.isFavorited()).isEqualTo(UPDATED_FAVORITED);

        // Validate the TalkParticipant in Elasticsearch
        verify(mockTalkParticipantSearchRepository, times(1)).save(testTalkParticipant);
    }

    @Test
    @Transactional
    public void updateNonExistingTalkParticipant() throws Exception {
        int databaseSizeBeforeUpdate = talkParticipantRepository.findAll().size();

        // Create the TalkParticipant
        TalkParticipantDTO talkParticipantDTO = talkParticipantMapper.toDto(talkParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalkParticipantMockMvc.perform(put("/api/talk-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkParticipant in the database
        List<TalkParticipant> talkParticipantList = talkParticipantRepository.findAll();
        assertThat(talkParticipantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TalkParticipant in Elasticsearch
        verify(mockTalkParticipantSearchRepository, times(0)).save(talkParticipant);
    }

    @Test
    @Transactional
    public void deleteTalkParticipant() throws Exception {
        // Initialize the database
        talkParticipantRepository.saveAndFlush(talkParticipant);

        int databaseSizeBeforeDelete = talkParticipantRepository.findAll().size();

        // Delete the talkParticipant
        restTalkParticipantMockMvc.perform(delete("/api/talk-participants/{id}", talkParticipant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TalkParticipant> talkParticipantList = talkParticipantRepository.findAll();
        assertThat(talkParticipantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TalkParticipant in Elasticsearch
        verify(mockTalkParticipantSearchRepository, times(1)).deleteById(talkParticipant.getId());
    }

    @Test
    @Transactional
    public void searchTalkParticipant() throws Exception {
        // Initialize the database
        talkParticipantRepository.saveAndFlush(talkParticipant);
        when(mockTalkParticipantSearchRepository.search(queryStringQuery("id:" + talkParticipant.getId())))
            .thenReturn(Collections.singletonList(talkParticipant));
        // Search the talkParticipant
        restTalkParticipantMockMvc.perform(get("/api/_search/talk-participants?query=id:" + talkParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talkParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkedIn").value(hasItem(DEFAULT_CHECKED_IN.booleanValue())))
            .andExpect(jsonPath("$.[*].plannedToGo").value(hasItem(DEFAULT_PLANNED_TO_GO.booleanValue())))
            .andExpect(jsonPath("$.[*].favorited").value(hasItem(DEFAULT_FAVORITED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkParticipant.class);
        TalkParticipant talkParticipant1 = new TalkParticipant();
        talkParticipant1.setId(1L);
        TalkParticipant talkParticipant2 = new TalkParticipant();
        talkParticipant2.setId(talkParticipant1.getId());
        assertThat(talkParticipant1).isEqualTo(talkParticipant2);
        talkParticipant2.setId(2L);
        assertThat(talkParticipant1).isNotEqualTo(talkParticipant2);
        talkParticipant1.setId(null);
        assertThat(talkParticipant1).isNotEqualTo(talkParticipant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkParticipantDTO.class);
        TalkParticipantDTO talkParticipantDTO1 = new TalkParticipantDTO();
        talkParticipantDTO1.setId(1L);
        TalkParticipantDTO talkParticipantDTO2 = new TalkParticipantDTO();
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
        talkParticipantDTO2.setId(talkParticipantDTO1.getId());
        assertThat(talkParticipantDTO1).isEqualTo(talkParticipantDTO2);
        talkParticipantDTO2.setId(2L);
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
        talkParticipantDTO1.setId(null);
        assertThat(talkParticipantDTO1).isNotEqualTo(talkParticipantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(talkParticipantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(talkParticipantMapper.fromId(null)).isNull();
    }
}
