package com.btocakci.conferuns.web.rest;

import com.btocakci.conferuns.ConferunsApp;

import com.btocakci.conferuns.domain.Conference;
import com.btocakci.conferuns.repository.ConferenceRepository;
import com.btocakci.conferuns.repository.search.ConferenceSearchRepository;
import com.btocakci.conferuns.service.ConferenceService;
import com.btocakci.conferuns.service.dto.ConferenceDTO;
import com.btocakci.conferuns.service.mapper.ConferenceMapper;
import com.btocakci.conferuns.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

import com.btocakci.conferuns.domain.enumeration.ConferenceTopic;
import com.btocakci.conferuns.domain.enumeration.Language;
/**
 * Test class for the ConferenceController REST controller.
 *
 * @see ConferenceController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferunsApp.class)
public class ConferenceControllerIntTest {

    private static final Long DEFAULT_CONFERENCE_ID = 1L;
    private static final Long UPDATED_CONFERENCE_ID = 2L;

    private static final String DEFAULT_MAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUB_NAME = "BBBBBBBBBB";

    private static final ConferenceTopic DEFAULT_MAIN_TOPIC = ConferenceTopic.SOFTWARE_ENGINEERING;
    private static final ConferenceTopic UPDATED_MAIN_TOPIC = ConferenceTopic.FINANCE;

    private static final String DEFAULT_SUB_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_FREE = false;
    private static final Boolean UPDATED_IS_FREE = true;

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.TURKISH;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_TALK_SUBMISSION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_TALK_SUBMISSION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Mock
    private ConferenceRepository conferenceRepositoryMock;

    @Autowired
    private ConferenceMapper conferenceMapper;

    @Mock
    private ConferenceService conferenceServiceMock;

    @Autowired
    private ConferenceService conferenceService;

    /**
     * This repository is mocked in the com.btocakci.conferuns.repository.search test package.
     *
     * @see com.btocakci.conferuns.repository.search.ConferenceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConferenceSearchRepository mockConferenceSearchRepository;

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

    private MockMvc restConferenceMockMvc;

    private Conference conference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConferenceController conferenceController = new ConferenceController(conferenceService);
        this.restConferenceMockMvc = MockMvcBuilders.standaloneSetup(conferenceController)
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
    public static Conference createEntity(EntityManager em) {
        Conference conference = new Conference()
            .conferenceId(DEFAULT_CONFERENCE_ID)
            .mainName(DEFAULT_MAIN_NAME)
            .subName(DEFAULT_SUB_NAME)
            .mainTopic(DEFAULT_MAIN_TOPIC)
            .subTopic(DEFAULT_SUB_TOPIC)
            .description(DEFAULT_DESCRIPTION)
            .isFree(DEFAULT_IS_FREE)
            .language(DEFAULT_LANGUAGE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .lastTalkSubmissionTime(DEFAULT_LAST_TALK_SUBMISSION_TIME);
        return conference;
    }

    @Before
    public void initTest() {
        conference = createEntity(em);
    }

    @Test
    @Transactional
    public void createConference() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getConferenceId()).isEqualTo(DEFAULT_CONFERENCE_ID);
        assertThat(testConference.getMainName()).isEqualTo(DEFAULT_MAIN_NAME);
        assertThat(testConference.getSubName()).isEqualTo(DEFAULT_SUB_NAME);
        assertThat(testConference.getMainTopic()).isEqualTo(DEFAULT_MAIN_TOPIC);
        assertThat(testConference.getSubTopic()).isEqualTo(DEFAULT_SUB_TOPIC);
        assertThat(testConference.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConference.isIsFree()).isEqualTo(DEFAULT_IS_FREE);
        assertThat(testConference.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testConference.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testConference.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testConference.getLastTalkSubmissionTime()).isEqualTo(DEFAULT_LAST_TALK_SUBMISSION_TIME);

        // Validate the Conference in Elasticsearch
        verify(mockConferenceSearchRepository, times(1)).save(testConference);
    }

    @Test
    @Transactional
    public void createConferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference with an existing ID
        conference.setId(1L);
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Conference in Elasticsearch
        verify(mockConferenceSearchRepository, times(0)).save(conference);
    }

    @Test
    @Transactional
    public void getAllConferences() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get all the conferenceList
        restConferenceMockMvc.perform(get("/api/conferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conference.getId().intValue())))
            .andExpect(jsonPath("$.[*].conferenceId").value(hasItem(DEFAULT_CONFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].mainName").value(hasItem(DEFAULT_MAIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].subName").value(hasItem(DEFAULT_SUB_NAME.toString())))
            .andExpect(jsonPath("$.[*].mainTopic").value(hasItem(DEFAULT_MAIN_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].subTopic").value(hasItem(DEFAULT_SUB_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isFree").value(hasItem(DEFAULT_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastTalkSubmissionTime").value(hasItem(DEFAULT_LAST_TALK_SUBMISSION_TIME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllConferencesWithEagerRelationshipsIsEnabled() throws Exception {
        ConferenceController conferenceController = new ConferenceController(conferenceServiceMock);
        when(conferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restConferenceMockMvc = MockMvcBuilders.standaloneSetup(conferenceController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restConferenceMockMvc.perform(get("/api/conferences?eagerload=true"))
        .andExpect(status().isOk());

        verify(conferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllConferencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ConferenceController conferenceController = new ConferenceController(conferenceServiceMock);
            when(conferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restConferenceMockMvc = MockMvcBuilders.standaloneSetup(conferenceController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restConferenceMockMvc.perform(get("/api/conferences?eagerload=true"))
        .andExpect(status().isOk());

            verify(conferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", conference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conference.getId().intValue()))
            .andExpect(jsonPath("$.conferenceId").value(DEFAULT_CONFERENCE_ID.intValue()))
            .andExpect(jsonPath("$.mainName").value(DEFAULT_MAIN_NAME.toString()))
            .andExpect(jsonPath("$.subName").value(DEFAULT_SUB_NAME.toString()))
            .andExpect(jsonPath("$.mainTopic").value(DEFAULT_MAIN_TOPIC.toString()))
            .andExpect(jsonPath("$.subTopic").value(DEFAULT_SUB_TOPIC.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isFree").value(DEFAULT_IS_FREE.booleanValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.lastTalkSubmissionTime").value(DEFAULT_LAST_TALK_SUBMISSION_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConference() throws Exception {
        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Update the conference
        Conference updatedConference = conferenceRepository.findById(conference.getId()).get();
        // Disconnect from session so that the updates on updatedConference are not directly saved in db
        em.detach(updatedConference);
        updatedConference
            .conferenceId(UPDATED_CONFERENCE_ID)
            .mainName(UPDATED_MAIN_NAME)
            .subName(UPDATED_SUB_NAME)
            .mainTopic(UPDATED_MAIN_TOPIC)
            .subTopic(UPDATED_SUB_TOPIC)
            .description(UPDATED_DESCRIPTION)
            .isFree(UPDATED_IS_FREE)
            .language(UPDATED_LANGUAGE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastTalkSubmissionTime(UPDATED_LAST_TALK_SUBMISSION_TIME);
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(updatedConference);

        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isOk());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getConferenceId()).isEqualTo(UPDATED_CONFERENCE_ID);
        assertThat(testConference.getMainName()).isEqualTo(UPDATED_MAIN_NAME);
        assertThat(testConference.getSubName()).isEqualTo(UPDATED_SUB_NAME);
        assertThat(testConference.getMainTopic()).isEqualTo(UPDATED_MAIN_TOPIC);
        assertThat(testConference.getSubTopic()).isEqualTo(UPDATED_SUB_TOPIC);
        assertThat(testConference.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConference.isIsFree()).isEqualTo(UPDATED_IS_FREE);
        assertThat(testConference.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testConference.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testConference.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testConference.getLastTalkSubmissionTime()).isEqualTo(UPDATED_LAST_TALK_SUBMISSION_TIME);

        // Validate the Conference in Elasticsearch
        verify(mockConferenceSearchRepository, times(1)).save(testConference);
    }

    @Test
    @Transactional
    public void updateNonExistingConference() throws Exception {
        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Create the Conference
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Conference in Elasticsearch
        verify(mockConferenceSearchRepository, times(0)).save(conference);
    }

    @Test
    @Transactional
    public void deleteConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        int databaseSizeBeforeDelete = conferenceRepository.findAll().size();

        // Delete the conference
        restConferenceMockMvc.perform(delete("/api/conferences/{id}", conference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Conference in Elasticsearch
        verify(mockConferenceSearchRepository, times(1)).deleteById(conference.getId());
    }

    @Test
    @Transactional
    public void searchConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);
        when(mockConferenceSearchRepository.search(queryStringQuery("id:" + conference.getId())))
            .thenReturn(Collections.singletonList(conference));
        // Search the conference
        restConferenceMockMvc.perform(get("/api/_search/conferences?query=id:" + conference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conference.getId().intValue())))
            .andExpect(jsonPath("$.[*].conferenceId").value(hasItem(DEFAULT_CONFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].mainName").value(hasItem(DEFAULT_MAIN_NAME)))
            .andExpect(jsonPath("$.[*].subName").value(hasItem(DEFAULT_SUB_NAME)))
            .andExpect(jsonPath("$.[*].mainTopic").value(hasItem(DEFAULT_MAIN_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].subTopic").value(hasItem(DEFAULT_SUB_TOPIC)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isFree").value(hasItem(DEFAULT_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastTalkSubmissionTime").value(hasItem(DEFAULT_LAST_TALK_SUBMISSION_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conference.class);
        Conference conference1 = new Conference();
        conference1.setId(1L);
        Conference conference2 = new Conference();
        conference2.setId(conference1.getId());
        assertThat(conference1).isEqualTo(conference2);
        conference2.setId(2L);
        assertThat(conference1).isNotEqualTo(conference2);
        conference1.setId(null);
        assertThat(conference1).isNotEqualTo(conference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConferenceDTO.class);
        ConferenceDTO conferenceDTO1 = new ConferenceDTO();
        conferenceDTO1.setId(1L);
        ConferenceDTO conferenceDTO2 = new ConferenceDTO();
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO2.setId(conferenceDTO1.getId());
        assertThat(conferenceDTO1).isEqualTo(conferenceDTO2);
        conferenceDTO2.setId(2L);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO1.setId(null);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conferenceMapper.fromId(null)).isNull();
    }
}
