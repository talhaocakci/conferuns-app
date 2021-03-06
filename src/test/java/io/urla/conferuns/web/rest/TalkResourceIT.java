package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.Talk;
import io.urla.conferuns.repository.TalkRepository;
import io.urla.conferuns.service.TalkService;
import io.urla.conferuns.service.dto.TalkDTO;
import io.urla.conferuns.service.mapper.TalkMapper;
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

import io.urla.conferuns.domain.enumeration.Language;
import io.urla.conferuns.domain.enumeration.TalkStatus;
/**
 * Integration tests for the {@link TalkResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class TalkResourceIT {

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

    @BeforeEach
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
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Talk createUpdatedEntity(EntityManager em) {
        Talk talk = new Talk()
            .language(UPDATED_LANGUAGE)
            .mainTopic(UPDATED_MAIN_TOPIC)
            .subTopic(UPDATED_SUB_TOPIC)
            .status(UPDATED_STATUS);
        return talk;
    }

    @BeforeEach
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
            .andExpect(jsonPath("$.[*].mainTopic").value(hasItem(DEFAULT_MAIN_TOPIC)))
            .andExpect(jsonPath("$.[*].subTopic").value(hasItem(DEFAULT_SUB_TOPIC)))
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
            .andExpect(jsonPath("$.mainTopic").value(DEFAULT_MAIN_TOPIC))
            .andExpect(jsonPath("$.subTopic").value(DEFAULT_SUB_TOPIC))
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
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
