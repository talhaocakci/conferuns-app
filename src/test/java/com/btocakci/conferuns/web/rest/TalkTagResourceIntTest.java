package com.btocakci.conferuns.web.rest;

import com.btocakci.conferuns.ConferunsApp;

import com.btocakci.conferuns.domain.TalkTag;
import com.btocakci.conferuns.repository.TalkTagRepository;
import com.btocakci.conferuns.repository.search.TalkTagSearchRepository;
import com.btocakci.conferuns.service.TalkTagService;
import com.btocakci.conferuns.service.dto.TalkTagDTO;
import com.btocakci.conferuns.service.mapper.TalkTagMapper;
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
 * Test class for the TalkTagResource REST controller.
 *
 * @see TalkTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferunsApp.class)
public class TalkTagResourceIntTest {

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

    /**
     * This repository is mocked in the com.btocakci.conferuns.repository.search test package.
     *
     * @see com.btocakci.conferuns.repository.search.TalkTagSearchRepositoryMockConfiguration
     */
    @Autowired
    private TalkTagSearchRepository mockTalkTagSearchRepository;

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

    @Before
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

    @Before
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

        // Validate the TalkTag in Elasticsearch
        verify(mockTalkTagSearchRepository, times(1)).save(testTalkTag);
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

        // Validate the TalkTag in Elasticsearch
        verify(mockTalkTagSearchRepository, times(0)).save(talkTag);
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
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
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
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
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

        // Validate the TalkTag in Elasticsearch
        verify(mockTalkTagSearchRepository, times(1)).save(testTalkTag);
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

        // Validate the TalkTag in Elasticsearch
        verify(mockTalkTagSearchRepository, times(0)).save(talkTag);
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
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TalkTag> talkTagList = talkTagRepository.findAll();
        assertThat(talkTagList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TalkTag in Elasticsearch
        verify(mockTalkTagSearchRepository, times(1)).deleteById(talkTag.getId());
    }

    @Test
    @Transactional
    public void searchTalkTag() throws Exception {
        // Initialize the database
        talkTagRepository.saveAndFlush(talkTag);
        when(mockTalkTagSearchRepository.search(queryStringQuery("id:" + talkTag.getId())))
            .thenReturn(Collections.singletonList(talkTag));
        // Search the talkTag
        restTalkTagMockMvc.perform(get("/api/_search/talk-tags?query=id:" + talkTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talkTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkTag.class);
        TalkTag talkTag1 = new TalkTag();
        talkTag1.setId(1L);
        TalkTag talkTag2 = new TalkTag();
        talkTag2.setId(talkTag1.getId());
        assertThat(talkTag1).isEqualTo(talkTag2);
        talkTag2.setId(2L);
        assertThat(talkTag1).isNotEqualTo(talkTag2);
        talkTag1.setId(null);
        assertThat(talkTag1).isNotEqualTo(talkTag2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalkTagDTO.class);
        TalkTagDTO talkTagDTO1 = new TalkTagDTO();
        talkTagDTO1.setId(1L);
        TalkTagDTO talkTagDTO2 = new TalkTagDTO();
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
        talkTagDTO2.setId(talkTagDTO1.getId());
        assertThat(talkTagDTO1).isEqualTo(talkTagDTO2);
        talkTagDTO2.setId(2L);
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
        talkTagDTO1.setId(null);
        assertThat(talkTagDTO1).isNotEqualTo(talkTagDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(talkTagMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(talkTagMapper.fromId(null)).isNull();
    }
}
