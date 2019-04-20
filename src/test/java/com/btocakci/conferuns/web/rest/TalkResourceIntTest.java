package com.btocakci.conferuns.web.rest;

import com.btocakci.conferuns.ConferunsApp;

import com.btocakci.conferuns.domain.Talk;
import com.btocakci.conferuns.repository.TalkRepository;
import com.btocakci.conferuns.repository.search.TalkSearchRepository;
import com.btocakci.conferuns.service.TalkService;
import com.btocakci.conferuns.service.dto.TalkDTO;
import com.btocakci.conferuns.service.mapper.TalkMapper;
import com.btocakci.conferuns.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.btocakci.conferuns.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.btocakci.conferuns.domain.enumeration.Language;
import com.btocakci.conferuns.domain.enumeration.TalkStatus;
/**
 * Test class for the TalkResource REST controller.
 *
 * @see TalkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferunsApp.class)
public class TalkResourceIntTest {

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.TURKISH;

    private static final String DEFAULT_MAIN_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TOPIC = "BBBBBBBBBB";

    private static final TalkStatus DEFAULT_STATUS = TalkStatus.DRAFT;
    private static final TalkStatus UPDATED_STATUS = TalkStatus.IN_REVIEW;

    @Autowired
    private TalkRepository talkRepository;

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private TalkService talkService;

    /**
     * This repository is mocked in the com.btocakci.conferuns.repository.search test package.
     *
     * @see com.btocakci.conferuns.repository.search.TalkSearchRepositoryMockConfiguration
     */
    @Autowired
    private TalkSearchRepository mockTalkSearchRepository;

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

    private MockMvc restTalkMockMvc;

    private Talk talk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TalkResource talkResource = new TalkResource(talkService);
        this.restTalkMockMvc = MockMvcBuilders.standaloneSetup(talkResource)
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
    public static Talk createEntity(EntityManager em) {
        Talk talk = new Talk()
            .language(DEFAULT_LANGUAGE)
            .mainTopic(DEFAULT_MAIN_TOPIC)
            .subTopic(DEFAULT_SUB_TOPIC)
            .status(DEFAULT_STATUS);
        return talk;
    }

    @Before
    public void initTest() {
        talk = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalk() throws Exception {
        int databaseSizeBeforeCreate = talkRepository.findAll().size();

        // Create the Talk
        TalkDTO talkDTO = talkMapper.toDto(talk);
        restTalkMockMvc.perform(post("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkDTO)))
            .andExpect(status().isCreated());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeCreate + 1);
        Talk testTalk = talkList.get(talkList.size() - 1);
        assertThat(testTalk.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testTalk.getMainTopic()).isEqualTo(DEFAULT_MAIN_TOPIC);
        assertThat(testTalk.getSubTopic()).isEqualTo(DEFAULT_SUB_TOPIC);
        assertThat(testTalk.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Talk in Elasticsearch
        verify(mockTalkSearchRepository, times(1)).save(testTalk);
    }

    @Test
    @Transactional
    public void createTalkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talkRepository.findAll().size();

        // Create the Talk with an existing ID
        talk.setId(1L);
        TalkDTO talkDTO = talkMapper.toDto(talk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalkMockMvc.perform(post("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeCreate);

        // Validate the Talk in Elasticsearch
        verify(mockTalkSearchRepository, times(0)).save(talk);
    }

    @Test
    @Transactional
    public void getAllTalks() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        // Get all the talkList
        restTalkMockMvc.perform(get("/api/talks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talk.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].mainTopic").value(hasItem(DEFAULT_MAIN_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].subTopic").value(hasItem(DEFAULT_SUB_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        // Get the talk
        restTalkMockMvc.perform(get("/api/talks/{id}", talk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talk.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.mainTopic").value(DEFAULT_MAIN_TOPIC.toString()))
            .andExpect(jsonPath("$.subTopic").value(DEFAULT_SUB_TOPIC.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTalk() throws Exception {
        // Get the talk
        restTalkMockMvc.perform(get("/api/talks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        int databaseSizeBeforeUpdate = talkRepository.findAll().size();

        // Update the talk
        Talk updatedTalk = talkRepository.findById(talk.getId()).get();
        // Disconnect from session so that the updates on updatedTalk are not directly saved in db
        em.detach(updatedTalk);
        updatedTalk
            .language(UPDATED_LANGUAGE)
            .mainTopic(UPDATED_MAIN_TOPIC)
            .subTopic(UPDATED_SUB_TOPIC)
            .status(UPDATED_STATUS);
        TalkDTO talkDTO = talkMapper.toDto(updatedTalk);

        restTalkMockMvc.perform(put("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkDTO)))
            .andExpect(status().isOk());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeUpdate);
        Talk testTalk = talkList.get(talkList.size() - 1);
        assertThat(testTalk.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testTalk.getMainTopic()).isEqualTo(UPDATED_MAIN_TOPIC);
        assertThat(testTalk.getSubTopic()).isEqualTo(UPDATED_SUB_TOPIC);
        assertThat(testTalk.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Talk in Elasticsearch
        verify(mockTalkSearchRepository, times(1)).save(testTalk);
    }

    @Test
    @Transactional
    public void updateNonExistingTalk() throws Exception {
        int databaseSizeBeforeUpdate = talkRepository.findAll().size();

        // Create the Talk
        TalkDTO talkDTO = talkMapper.toDto(talk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalkMockMvc.perform(put("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Talk in Elasticsearch
        verify(mockTalkSearchRepository, times(0)).save(talk);
    }

    @Test
    @Transactional
    public void deleteTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        int databaseSizeBeforeDelete = talkRepository.findAll().size();

        // Delete the talk
        restTalkMockMvc.perform(delete("/api/talks/{id}", talk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Talk in Elasticsearch
        verify(mockTalkSearchRepository, times(1)).deleteById(talk.getId());
    }

    @Test
    @Transactional
    public void searchTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);
        when(mockTalkSearchRepository.search(queryStringQuery("id:" + talk.getId())))
            .thenReturn(Collections.singletonList(talk));
        // Search the talk
        restTalkMockMvc.perform(get("/api/_search/talks?query=id:" + talk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talk.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].mainTopic").value(hasItem(DEFAULT_MAIN_TOPIC)))
            .andExpect(jsonPath("$.[*].subTopic").value(hasItem(DEFAULT_SUB_TOPIC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Talk.class);
        Talk talk1 = new Talk();
        talk1.setId(1L);
        Talk talk2 = new Talk();
        talk2.setId(talk1.getId());
        assertThat(talk1).isEqualTo(talk2);
        talk2.setId(2L);
        assertThat(talk1).isNotEqualTo(talk2);
        talk1.setId(null);
        assertThat(talk1).isNotEqualTo(talk2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkDTO.class);
        TalkDTO talkDTO1 = new TalkDTO();
        talkDTO1.setId(1L);
        TalkDTO talkDTO2 = new TalkDTO();
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
        talkDTO2.setId(talkDTO1.getId());
        assertThat(talkDTO1).isEqualTo(talkDTO2);
        talkDTO2.setId(2L);
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
        talkDTO1.setId(null);
        assertThat(talkDTO1).isNotEqualTo(talkDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(talkMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(talkMapper.fromId(null)).isNull();
    }
}
