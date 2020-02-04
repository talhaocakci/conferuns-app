package io.urla.conferuns.web.rest;

import io.urla.conferuns.ConferunsApp;
import io.urla.conferuns.domain.ScheduleItem;
import io.urla.conferuns.repository.ScheduleItemRepository;
import io.urla.conferuns.service.ScheduleItemService;
import io.urla.conferuns.service.dto.ScheduleItemDTO;
import io.urla.conferuns.service.mapper.ScheduleItemMapper;
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
 * Integration tests for the {@link ScheduleItemResource} REST controller.
 */
@SpringBootTest(classes = ConferunsApp.class)
public class ScheduleItemResourceIT {

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TILL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TILL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Autowired
    private ScheduleItemMapper scheduleItemMapper;

    @Autowired
    private ScheduleItemService scheduleItemService;

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

    private MockMvc restScheduleItemMockMvc;

    private ScheduleItem scheduleItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScheduleItemResource scheduleItemResource = new ScheduleItemResource(scheduleItemService);
        this.restScheduleItemMockMvc = MockMvcBuilders.standaloneSetup(scheduleItemResource)
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
    public static ScheduleItem createEntity(EntityManager em) {
        ScheduleItem scheduleItem = new ScheduleItem()
            .fromTime(DEFAULT_FROM_TIME)
            .tillTime(DEFAULT_TILL_TIME);
        return scheduleItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleItem createUpdatedEntity(EntityManager em) {
        ScheduleItem scheduleItem = new ScheduleItem()
            .fromTime(UPDATED_FROM_TIME)
            .tillTime(UPDATED_TILL_TIME);
        return scheduleItem;
    }

    @BeforeEach
    public void initTest() {
        scheduleItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleItem() throws Exception {
        int databaseSizeBeforeCreate = scheduleItemRepository.findAll().size();

        // Create the ScheduleItem
        ScheduleItemDTO scheduleItemDTO = scheduleItemMapper.toDto(scheduleItem);
        restScheduleItemMockMvc.perform(post("/api/schedule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleItem in the database
        List<ScheduleItem> scheduleItemList = scheduleItemRepository.findAll();
        assertThat(scheduleItemList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleItem testScheduleItem = scheduleItemList.get(scheduleItemList.size() - 1);
        assertThat(testScheduleItem.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testScheduleItem.getTillTime()).isEqualTo(DEFAULT_TILL_TIME);
    }

    @Test
    @Transactional
    public void createScheduleItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleItemRepository.findAll().size();

        // Create the ScheduleItem with an existing ID
        scheduleItem.setId(1L);
        ScheduleItemDTO scheduleItemDTO = scheduleItemMapper.toDto(scheduleItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleItemMockMvc.perform(post("/api/schedule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleItem in the database
        List<ScheduleItem> scheduleItemList = scheduleItemRepository.findAll();
        assertThat(scheduleItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScheduleItems() throws Exception {
        // Initialize the database
        scheduleItemRepository.saveAndFlush(scheduleItem);

        // Get all the scheduleItemList
        restScheduleItemMockMvc.perform(get("/api/schedule-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].tillTime").value(hasItem(DEFAULT_TILL_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getScheduleItem() throws Exception {
        // Initialize the database
        scheduleItemRepository.saveAndFlush(scheduleItem);

        // Get the scheduleItem
        restScheduleItemMockMvc.perform(get("/api/schedule-items/{id}", scheduleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleItem.getId().intValue()))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.tillTime").value(DEFAULT_TILL_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScheduleItem() throws Exception {
        // Get the scheduleItem
        restScheduleItemMockMvc.perform(get("/api/schedule-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleItem() throws Exception {
        // Initialize the database
        scheduleItemRepository.saveAndFlush(scheduleItem);

        int databaseSizeBeforeUpdate = scheduleItemRepository.findAll().size();

        // Update the scheduleItem
        ScheduleItem updatedScheduleItem = scheduleItemRepository.findById(scheduleItem.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleItem are not directly saved in db
        em.detach(updatedScheduleItem);
        updatedScheduleItem
            .fromTime(UPDATED_FROM_TIME)
            .tillTime(UPDATED_TILL_TIME);
        ScheduleItemDTO scheduleItemDTO = scheduleItemMapper.toDto(updatedScheduleItem);

        restScheduleItemMockMvc.perform(put("/api/schedule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleItemDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleItem in the database
        List<ScheduleItem> scheduleItemList = scheduleItemRepository.findAll();
        assertThat(scheduleItemList).hasSize(databaseSizeBeforeUpdate);
        ScheduleItem testScheduleItem = scheduleItemList.get(scheduleItemList.size() - 1);
        assertThat(testScheduleItem.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testScheduleItem.getTillTime()).isEqualTo(UPDATED_TILL_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleItem() throws Exception {
        int databaseSizeBeforeUpdate = scheduleItemRepository.findAll().size();

        // Create the ScheduleItem
        ScheduleItemDTO scheduleItemDTO = scheduleItemMapper.toDto(scheduleItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleItemMockMvc.perform(put("/api/schedule-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleItem in the database
        List<ScheduleItem> scheduleItemList = scheduleItemRepository.findAll();
        assertThat(scheduleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleItem() throws Exception {
        // Initialize the database
        scheduleItemRepository.saveAndFlush(scheduleItem);

        int databaseSizeBeforeDelete = scheduleItemRepository.findAll().size();

        // Delete the scheduleItem
        restScheduleItemMockMvc.perform(delete("/api/schedule-items/{id}", scheduleItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleItem> scheduleItemList = scheduleItemRepository.findAll();
        assertThat(scheduleItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
