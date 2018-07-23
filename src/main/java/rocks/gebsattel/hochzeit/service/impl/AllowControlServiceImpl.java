package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.AllowControlService;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.repository.AllowControlRepository;
import rocks.gebsattel.hochzeit.repository.search.AllowControlSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AllowControl.
 */
@Service
@Transactional
public class AllowControlServiceImpl implements AllowControlService {

    private final Logger log = LoggerFactory.getLogger(AllowControlServiceImpl.class);

    private final AllowControlRepository allowControlRepository;

    private final AllowControlSearchRepository allowControlSearchRepository;

    public AllowControlServiceImpl(AllowControlRepository allowControlRepository, AllowControlSearchRepository allowControlSearchRepository) {
        this.allowControlRepository = allowControlRepository;
        this.allowControlSearchRepository = allowControlSearchRepository;
    }

    /**
     * Save a allowControl.
     *
     * @param allowControl the entity to save
     * @return the persisted entity
     */
    @Override
    public AllowControl save(AllowControl allowControl) {
        log.debug("Request to save AllowControl : {}", allowControl);
        AllowControl result = allowControlRepository.save(allowControl);
        allowControlSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the allowControls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllowControl> findAll(Pageable pageable) {
        log.debug("Request to get all AllowControls");
        return allowControlRepository.findAll(pageable);
    }

    /**
     * Get one allowControl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AllowControl findOne(Long id) {
        log.debug("Request to get AllowControl : {}", id);
        return allowControlRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the allowControl by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllowControl : {}", id);
        allowControlRepository.delete(id);
        allowControlSearchRepository.delete(id);
    }

    /**
     * Search for the allowControl corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllowControl> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AllowControls for query {}", query);
        Page<AllowControl> result = allowControlSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
