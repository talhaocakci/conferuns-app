package io.urla.conferuns.service;

import io.urla.conferuns.service.dto.TopicDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.urla.conferuns.domain.Topic}.
 */
public interface TopicService {

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save.
     * @return the persisted entity.
     */
    TopicDTO save(TopicDTO topicDTO);

    /**
     * Get all the topics.
     *
     * @return the list of entities.
     */
    List<TopicDTO> findAll();


    /**
     * Get the "id" topic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicDTO> findOne(Long id);

    /**
     * Delete the "id" topic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
