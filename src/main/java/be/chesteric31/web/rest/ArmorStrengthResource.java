package be.chesteric31.web.rest;

import be.chesteric31.service.ArmorStrengthService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.ArmorStrengthDTO;

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
 * REST controller for managing {@link be.chesteric31.domain.ArmorStrength}.
 */
@RestController
@RequestMapping("/api")
public class ArmorStrengthResource {

    private final Logger log = LoggerFactory.getLogger(ArmorStrengthResource.class);

    private static final String ENTITY_NAME = "armorStrength";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmorStrengthService armorStrengthService;

    public ArmorStrengthResource(ArmorStrengthService armorStrengthService) {
        this.armorStrengthService = armorStrengthService;
    }

    /**
     * {@code POST  /armor-strengths} : Create a new armorStrength.
     *
     * @param armorStrengthDTO the armorStrengthDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armorStrengthDTO, or with status {@code 400 (Bad Request)} if the armorStrength has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/armor-strengths")
    public ResponseEntity<ArmorStrengthDTO> createArmorStrength(@RequestBody ArmorStrengthDTO armorStrengthDTO) throws URISyntaxException {
        log.debug("REST request to save ArmorStrength : {}", armorStrengthDTO);
        if (armorStrengthDTO.getId() != null) {
            throw new BadRequestAlertException("A new armorStrength cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmorStrengthDTO result = armorStrengthService.save(armorStrengthDTO);
        return ResponseEntity.created(new URI("/api/armor-strengths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /armor-strengths} : Updates an existing armorStrength.
     *
     * @param armorStrengthDTO the armorStrengthDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armorStrengthDTO,
     * or with status {@code 400 (Bad Request)} if the armorStrengthDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armorStrengthDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/armor-strengths")
    public ResponseEntity<ArmorStrengthDTO> updateArmorStrength(@RequestBody ArmorStrengthDTO armorStrengthDTO) throws URISyntaxException {
        log.debug("REST request to update ArmorStrength : {}", armorStrengthDTO);
        if (armorStrengthDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmorStrengthDTO result = armorStrengthService.save(armorStrengthDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armorStrengthDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /armor-strengths} : get all the armorStrengths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armorStrengths in body.
     */
    @GetMapping("/armor-strengths")
    public ResponseEntity<List<ArmorStrengthDTO>> getAllArmorStrengths(Pageable pageable) {
        log.debug("REST request to get a page of ArmorStrengths");
        Page<ArmorStrengthDTO> page = armorStrengthService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /armor-strengths/:id} : get the "id" armorStrength.
     *
     * @param id the id of the armorStrengthDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armorStrengthDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/armor-strengths/{id}")
    public ResponseEntity<ArmorStrengthDTO> getArmorStrength(@PathVariable Long id) {
        log.debug("REST request to get ArmorStrength : {}", id);
        Optional<ArmorStrengthDTO> armorStrengthDTO = armorStrengthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armorStrengthDTO);
    }

    /**
     * {@code DELETE  /armor-strengths/:id} : delete the "id" armorStrength.
     *
     * @param id the id of the armorStrengthDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/armor-strengths/{id}")
    public ResponseEntity<Void> deleteArmorStrength(@PathVariable Long id) {
        log.debug("REST request to delete ArmorStrength : {}", id);
        armorStrengthService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
