package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.service.AllowControlService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AllowControl.
 */
@RestController
@RequestMapping("/api")
public class AllowControlResource {

    private final Logger log = LoggerFactory.getLogger(AllowControlResource.class);

    private static final String ENTITY_NAME = "allowControl";

    private final AllowControlService allowControlService;

    public AllowControlResource(AllowControlService allowControlService) {
        this.allowControlService = allowControlService;
    }

    /**
     * POST  /allow-controls : Create a new allowControl.
     *
     * @param allowControl the allowControl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allowControl, or with status 400 (Bad Request) if the allowControl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allow-controls")
    @Timed
    public ResponseEntity<AllowControl> createAllowControl(@RequestBody AllowControl allowControl) throws URISyntaxException {
        log.debug("REST request to save AllowControl : {}", allowControl);
        if (allowControl.getId() != null) {
            throw new BadRequestAlertException("A new allowControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllowControl result = allowControlService.save(allowControl);
        return ResponseEntity.created(new URI("/api/allow-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allow-controls : Updates an existing allowControl.
     *
     * @param allowControl the allowControl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allowControl,
     * or with status 400 (Bad Request) if the allowControl is not valid,
     * or with status 500 (Internal Server Error) if the allowControl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allow-controls")
    @Timed
    public ResponseEntity<AllowControl> updateAllowControl(@RequestBody AllowControl allowControl) throws URISyntaxException {
        log.debug("REST request to update AllowControl : {}", allowControl);
        if (allowControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllowControl result = allowControlService.save(allowControl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allowControl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allow-controls : get all the allowControls.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of allowControls in body
     */
    @GetMapping("/allow-controls")
    @Timed
    public ResponseEntity<List<AllowControl>> getAllAllowControls(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of AllowControls");
        Page<AllowControl> page;
        if (eagerload) {
            page = allowControlService.findAllWithEagerRelationships(pageable);
        } else {
            page = allowControlService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/allow-controls?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /allow-controls/:id : get the "id" allowControl.
     *
     * @param id the id of the allowControl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allowControl, or with status 404 (Not Found)
     */
    @GetMapping("/allow-controls/{id}")
    @Timed
    public ResponseEntity<AllowControl> getAllowControl(@PathVariable Long id) {
        log.debug("REST request to get AllowControl : {}", id);
        Optional<AllowControl> allowControl = allowControlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allowControl);
    }

    /**
     * DELETE  /allow-controls/:id : delete the "id" allowControl.
     *
     * @param id the id of the allowControl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allow-controls/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllowControl(@PathVariable Long id) {
        log.debug("REST request to delete AllowControl : {}", id);
        allowControlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/allow-controls?query=:query : search for the allowControl corresponding
     * to the query.
     *
     * @param query the query of the allowControl search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/allow-controls")
    @Timed
    public ResponseEntity<List<AllowControl>> searchAllowControls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AllowControls for query {}", query);
        Page<AllowControl> page = allowControlService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/allow-controls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
