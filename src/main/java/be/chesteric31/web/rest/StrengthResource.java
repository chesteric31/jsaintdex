package be.chesteric31.web.rest;

import be.chesteric31.service.StrengthService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.StrengthDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link be.chesteric31.domain.Strength}.
 */
@RestController
@RequestMapping("/api")
public class StrengthResource {

    private final Logger log = LoggerFactory.getLogger(StrengthResource.class);

    private static final String ENTITY_NAME = "strength";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrengthService strengthService;

    public StrengthResource(StrengthService strengthService) {
        this.strengthService = strengthService;
    }

    /**
     * {@code POST  /strengths} : Create a new strength.
     *
     * @param strengthDTO the strengthDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strengthDTO, or with status {@code 400 (Bad Request)} if the strength has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strengths")
    public ResponseEntity<StrengthDTO> createStrength(@RequestBody StrengthDTO strengthDTO) throws URISyntaxException {
        log.debug("REST request to save Strength : {}", strengthDTO);
        if (strengthDTO.getId() != null) {
            throw new BadRequestAlertException("A new strength cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrengthDTO result = strengthService.save(strengthDTO);
        return ResponseEntity.created(new URI("/api/strengths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strengths} : Updates an existing strength.
     *
     * @param strengthDTO the strengthDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strengthDTO,
     * or with status {@code 400 (Bad Request)} if the strengthDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strengthDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strengths")
    public ResponseEntity<StrengthDTO> updateStrength(@RequestBody StrengthDTO strengthDTO) throws URISyntaxException {
        log.debug("REST request to update Strength : {}", strengthDTO);
        if (strengthDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StrengthDTO result = strengthService.save(strengthDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strengthDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /strengths} : get all the strengths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strengths in body.
     */
    @GetMapping("/strengths")
    public ResponseEntity<List<StrengthDTO>> getAllStrengths(Pageable pageable) {
        log.debug("REST request to get a page of Strengths");
        Page<StrengthDTO> page = strengthService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /strengths/:id} : get the "id" strength.
     *
     * @param id the id of the strengthDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strengthDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strengths/{id}")
    public ResponseEntity<StrengthDTO> getStrength(@PathVariable Long id) {
        log.debug("REST request to get Strength : {}", id);
        Optional<StrengthDTO> strengthDTO = strengthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strengthDTO);
    }

    /**
     * {@code DELETE  /strengths/:id} : delete the "id" strength.
     *
     * @param id the id of the strengthDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strengths/{id}")
    public ResponseEntity<Void> deleteStrength(@PathVariable Long id) {
        log.debug("REST request to delete Strength : {}", id);
        strengthService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
