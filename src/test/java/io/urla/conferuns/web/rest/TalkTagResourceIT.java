package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.TalkTag;
import io.urla.conferuns.repository.TalkTagRepository;
import io.urla.conferuns.service.TalkTagService;
import io.urla.conferuns.service.dto.TalkTagDTO;
import io.urla.conferuns.service.mapper.TalkTagMapper;
import io.urla.conferuns.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static io.urla.conferuns.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TalkTagResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class TalkTagResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Autowired
    private TalkTagRepository talkTagRepository;

    @Mock
    private TalkTagRepository talkTagRepositoryMock;

    @Autowired
    private TalkTagMapper talkTagMapper;

    @Mock
    private TalkTagService talkTagServiceMock;

    @Autowired
    private TalkTagService talkTagService;

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

    private MockMvc restTalkTagMockMvc;

    private TalkTag talkTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TalkTagResource talkTagResource = new TalkTagResource(talkTagService);
        this.restTalkTagMockMvc = MockMvcBuilders.standaloneSetup(talkTagResource)
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
    public static TalkTag createEntity(EntityManager em) {
        TalkTag talkTag = new TalkTag()
            .tag(DEFAULT_TAG);
        return talkTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TalkTag createUpdatedEntity(EntityManager em) {
        TalkTag talkTag = new TalkTag()
            .tag(UPDATED_TAG);
        return talkTag;
    }

    @BeforeEach
    public void initTest() {
        talkTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalkTag() throws Exception {
        int databaseSizeBeforeCreate = talkTagRepository.findAll().size();

        // Create the TalkTag
        TalkTagDTO talkTagDTO = talkTagMapper.toDto(talkTag);
        restTalkTagMockMvc.perform(post("/api/talk-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkTagDTO)))
            .andExpect(status().isCreated());

        // Validate the TalkTag in the database
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeCreate + 1);
        TalkTag testTalkTag = talkTagList.get(talkTagList.size() - 1);
        assertThat(testTalkTag.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createTalkTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talkTagRepository.findAll().size();

        // Create the TalkTag with an existing ID
        talkTag.setId(1L);
        TalkTagDTO talkTagDTO = talkTagMapper.toDto(talkTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalkTagMockMvc.perform(post("/api/talk-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkTag in the database
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTalkTags() throws Exception {
        // Initialize the database
        talkTagRepository.saveAndFlush(talkTag);

        // Get all the talkTagList
        restTalkTagMockMvc.perform(get("/api/talk-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talkTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTalkTagsWithEagerRelationshipsIsEnabled() throws Exception {
        TalkTagResource talkTagResource = new TalkTagResource(talkTagServiceMock);
        when(talkTagServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTalkTagMockMvc = MockMvcBuilders.standaloneSetup(talkTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTalkTagMockMvc.perform(get("/api/talk-tags?eagerload=true"))
        .andExpect(status().isOk());

        verify(talkTagServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTalkTagsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TalkTagResource talkTagResource = new TalkTagResource(talkTagServiceMock);
            when(talkTagServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTalkTagMockMvc = MockMvcBuilders.standaloneSetup(talkTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTalkTagMockMvc.perform(get("/api/talk-tags?eagerload=true"))
        .andExpect(status().isOk());

            verify(talkTagServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTalkTag() throws Exception {
        // Initialize the database
        talkTagRepository.saveAndFlush(talkTag);

        // Get the talkTag
        restTalkTagMockMvc.perform(get("/api/talk-tags/{id}", talkTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talkTag.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG));
    }

    @Test
    @Transactional
    public void getNonExistingTalkTag() throws Exception {
        // Get the talkTag
        restTalkTagMockMvc.perform(get("/api/talk-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalkTag() throws Exception {
        // Initialize the database
        talkTagRepository.saveAndFlush(talkTag);

        int databaseSizeBeforeUpdate = talkTagRepository.findAll().size();

        // Update the talkTag
        TalkTag updatedTalkTag = talkTagRepository.findById(talkTag.getId()).get();
        // Disconnect from session so that the updates on updatedTalkTag are not directly saved in db
        em.detach(updatedTalkTag);
        updatedTalkTag
            .tag(UPDATED_TAG);
        TalkTagDTO talkTagDTO = talkTagMapper.toDto(updatedTalkTag);

        restTalkTagMockMvc.perform(put("/api/talk-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkTagDTO)))
            .andExpect(status().isOk());

        // Validate the TalkTag in the database
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeUpdate);
        TalkTag testTalkTag = talkTagList.get(talkTagList.size() - 1);
        assertThat(testTalkTag.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingTalkTag() throws Exception {
        int databaseSizeBeforeUpdate = talkTagRepository.findAll().size();

        // Create the TalkTag
        TalkTagDTO talkTagDTO = talkTagMapper.toDto(talkTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalkTagMockMvc.perform(put("/api/talk-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talkTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TalkTag in the database
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTalkTag() throws Exception {
        // Initialize the database
        talkTagRepository.saveAndFlush(talkTag);

        int databaseSizeBeforeDelete = talkTagRepository.findAll().size();

        // Delete the talkTag
        restTalkTagMockMvc.perform(delete("/api/talk-tags/{id}", talkTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
