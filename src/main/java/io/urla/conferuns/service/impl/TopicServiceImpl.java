package io.urla.conferuns.service.impl;

import io.urla.conferuns.service.TopicService;
import io.urla.conferuns.domain.Topic;
import io.urla.conferuns.repository.TopicRepository;
import io.urla.conferuns.service.dto.TopicDTO;
import io.urla.conferuns.service.mapper.TopicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Topic}.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Get all the topics.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TopicDTO> findAll() {
        log.debug("Request to get all Topics");
        return topicRepository.findAll().stream()
            .map(topicMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one topic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TopicDTO> findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findById(id)
            .map(topicMapper::toDto);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
    }
}
