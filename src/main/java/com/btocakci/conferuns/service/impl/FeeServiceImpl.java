package com.btocakci.conferuns.service.impl;

import com.btocakci.conferuns.service.FeeService;
import com.btocakci.conferuns.domain.Fee;
import com.btocakci.conferuns.repository.FeeRepository;
import com.btocakci.conferuns.repository.search.FeeSearchRepository;
import com.btocakci.conferuns.service.dto.FeeDTO;
import com.btocakci.conferuns.service.mapper.FeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Fee.
 */
@Service
@Transactional
public class FeeServiceImpl implements FeeService {

    private final Logger log = LoggerFactory.getLogger(FeeServiceImpl.class);

    private final FeeRepository feeRepository;

    private final FeeMapper feeMapper;

    private final FeeSearchRepository feeSearchRepository;

    public FeeServiceImpl(FeeRepository feeRepository, FeeMapper feeMapper, FeeSearchRepository feeSearchRepository) {
        this.feeRepository = feeRepository;
        this.feeMapper = feeMapper;
        this.feeSearchRepository = feeSearchRepository;
    }

    /**
     * Save a fee.
     *
     * @param feeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeeDTO save(FeeDTO feeDTO) {
        log.debug("Request to save Fee : {}", feeDTO);
        Fee fee = feeMapper.toEntity(feeDTO);
        fee = feeRepository.save(fee);
        FeeDTO result = feeMapper.toDto(fee);
        feeSearchRepository.save(fee);
        return result;
    }

    /**
     * Get all the fees.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeeDTO> findAll() {
        log.debug("Request to get all Fees");
        return feeRepository.findAll().stream()
            .map(feeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FeeDTO> findOne(Long id) {
        log.debug("Request to get Fee : {}", id);
        return feeRepository.findById(id)
            .map(feeMapper::toDto);
    }

    /**
     * Delete the fee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fee : {}", id);
        feeRepository.deleteById(id);
        feeSearchRepository.deleteById(id);
    }

    /**
     * Search for the fee corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeeDTO> search(String query) {
        log.debug("Request to search Fees for query {}", query);
        return StreamSupport
            .stream(feeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(feeMapper::toDto)
            .collect(Collectors.toList());
    }
}
