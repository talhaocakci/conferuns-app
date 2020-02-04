package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.FileReview;
import io.urla.conferuns.repository.FileReviewRepository;
import io.urla.conferuns.service.FileReviewService;
import io.urla.conferuns.service.dto.FileReviewDTO;
import io.urla.conferuns.service.mapper.FileReviewMapper;
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

import io.urla.conferuns.domain.enumeration.FileReviewStatus;
/**
 * Integration tests for the {@link FileReviewResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class FileReviewResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEWER = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER = "BBBBBBBBBB";

    private static final FileReviewStatus DEFAULT_STATUS = FileReviewStatus.NEED_MORE_REVIEW;
    private static final FileReviewStatus UPDATED_STATUS = FileReviewStatus.REJECTED;

    @Autowired
    private FileReviewRepository fileReviewRepository;

    @Autowired
    private FileReviewMapper fileReviewMapper;

    @Autowired
    private FileReviewService fileReviewService;

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

    private MockMvc restFileReviewMockMvc;

    private FileReview fileReview;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileReviewResource fileReviewResource = new FileReviewResource(fileReviewService);
        this.restFileReviewMockMvc = MockMvcBuilders.standaloneSetup(fileReviewResource)
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
    public static FileReview createEntity(EntityManager em) {
        FileReview fileReview = new FileReview()
            .date(DEFAULT_DATE)
            .comment(DEFAULT_COMMENT)
            .reviewer(DEFAULT_REVIEWER)
            .status(DEFAULT_STATUS);
        return fileReview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileReview createUpdatedEntity(EntityManager em) {
        FileReview fileReview = new FileReview()
            .date(UPDATED_DATE)
            .comment(UPDATED_COMMENT)
            .reviewer(UPDATED_REVIEWER)
            .status(UPDATED_STATUS);
        return fileReview;
    }

    @BeforeEach
    public void initTest() {
        fileReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileReview() throws Exception {
        int databaseSizeBeforeCreate = fileReviewRepository.findAll().size();

        // Create the FileReview
        FileReviewDTO fileReviewDTO = fileReviewMapper.toDto(fileReview);
        restFileReviewMockMvc.perform(post("/api/file-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileReviewDTO)))
            .andExpect(status().isCreated());

        // Validate the FileReview in the database
        List<FileReview> fileReviewList = fileReviewRepository.findAll();
        assertThat(fileReviewList).hasSize(databaseSizeBeforeCreate + 1);
        FileReview testFileReview = fileReviewList.get(fileReviewList.size() - 1);
        assertThat(testFileReview.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFileReview.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testFileReview.getReviewer()).isEqualTo(DEFAULT_REVIEWER);
        assertThat(testFileReview.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFileReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileReviewRepository.findAll().size();

        // Create the FileReview with an existing ID
        fileReview.setId(1L);
        FileReviewDTO fileReviewDTO = fileReviewMapper.toDto(fileReview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileReviewMockMvc.perform(post("/api/file-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileReview in the database
        List<FileReview> fileReviewList = fileReviewRepository.findAll();
        assertThat(fileReviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFileReviews() throws Exception {
        // Initialize the database
        fileReviewRepository.saveAndFlush(fileReview);

        // Get all the fileReviewList
        restFileReviewMockMvc.perform(get("/api/file-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].reviewer").value(hasItem(DEFAULT_REVIEWER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFileReview() throws Exception {
        // Initialize the database
        fileReviewRepository.saveAndFlush(fileReview);

        // Get the fileReview
        restFileReviewMockMvc.perform(get("/api/file-reviews/{id}", fileReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileReview.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.reviewer").value(DEFAULT_REVIEWER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileReview() throws Exception {
        // Get the fileReview
        restFileReviewMockMvc.perform(get("/api/file-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileReview() throws Exception {
        // Initialize the database
        fileReviewRepository.saveAndFlush(fileReview);

        int databaseSizeBeforeUpdate = fileReviewRepository.findAll().size();

        // Update the fileReview
        FileReview updatedFileReview = fileReviewRepository.findById(fileReview.getId()).get();
        // Disconnect from session so that the updates on updatedFileReview are not directly saved in db
        em.detach(updatedFileReview);
        updatedFileReview
            .date(UPDATED_DATE)
            .comment(UPDATED_COMMENT)
            .reviewer(UPDATED_REVIEWER)
            .status(UPDATED_STATUS);
        FileReviewDTO fileReviewDTO = fileReviewMapper.toDto(updatedFileReview);

        restFileReviewMockMvc.perform(put("/api/file-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileReviewDTO)))
            .andExpect(status().isOk());

        // Validate the FileReview in the database
        List<FileReview> fileReviewList = fileReviewRepository.findAll();
        assertThat(fileReviewList).hasSize(databaseSizeBeforeUpdate);
        FileReview testFileReview = fileReviewList.get(fileReviewList.size() - 1);
        assertThat(testFileReview.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFileReview.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testFileReview.getReviewer()).isEqualTo(UPDATED_REVIEWER);
        assertThat(testFileReview.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFileReview() throws Exception {
        int databaseSizeBeforeUpdate = fileReviewRepository.findAll().size();

        // Create the FileReview
        FileReviewDTO fileReviewDTO = fileReviewMapper.toDto(fileReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileReviewMockMvc.perform(put("/api/file-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileReview in the database
        List<FileReview> fileReviewList = fileReviewRepository.findAll();
        assertThat(fileReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileReview() throws Exception {
        // Initialize the database
        fileReviewRepository.saveAndFlush(fileReview);

        int databaseSizeBeforeDelete = fileReviewRepository.findAll().size();

        // Delete the fileReview
        restFileReviewMockMvc.perform(delete("/api/file-reviews/{id}", fileReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileReview> fileReviewList = fileReviewRepository.findAll();
        assertThat(fileReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
