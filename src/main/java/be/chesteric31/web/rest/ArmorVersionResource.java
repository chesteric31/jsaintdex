package be.chesteric31.web.rest;

import be.chesteric31.service.ArmorVersionService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.ArmorVersionDTO;

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
 * REST controller for managing {@link be.chesteric31.domain.ArmorVersion}.
 */
@RestController
@RequestMapping("/api")
public class ArmorVersionResource {

    private final Logger log = LoggerFactory.getLogger(ArmorVersionResource.class);

    private static final String ENTITY_NAME = "armorVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmorVersionService armorVersionService;

    public ArmorVersionResource(ArmorVersionService armorVersionService) {
        this.armorVersionService = armorVersionService;
    }

    /**
     * {@code POST  /armor-versions} : Create a new armorVersion.
     *
     * @param armorVersionDTO the armorVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armorVersionDTO, or with status {@code 400 (Bad Request)} if the armorVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/armor-versions")
    public ResponseEntity<ArmorVersionDTO> createArmorVersion(@RequestBody ArmorVersionDTO armorVersionDTO) throws URISyntaxException {
        log.debug("REST request to save ArmorVersion : {}", armorVersionDTO);
        if (armorVersionDTO.getId() != null) {
            throw new BadRequestAlertException("A new armorVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmorVersionDTO result = armorVersionService.save(armorVersionDTO);
        return ResponseEntity.created(new URI("/api/armor-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /armor-versions} : Updates an existing armorVersion.
     *
     * @param armorVersionDTO the armorVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armorVersionDTO,
     * or with status {@code 400 (Bad Request)} if the armorVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armorVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/armor-versions")
    public ResponseEntity<ArmorVersionDTO> updateArmorVersion(@RequestBody ArmorVersionDTO armorVersionDTO) throws URISyntaxException {
        log.debug("REST request to update ArmorVersion : {}", armorVersionDTO);
        if (armorVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmorVersionDTO result = armorVersionService.save(armorVersionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armorVersionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /armor-versions} : get all the armorVersions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armorVersions in body.
     */
    @GetMapping("/armor-versions")
    public ResponseEntity<List<ArmorVersionDTO>> getAllArmorVersions(Pageable pageable) {
        log.debug("REST request to get a page of ArmorVersions");
        Page<ArmorVersionDTO> page = armorVersionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /armor-versions/:id} : get the "id" armorVersion.
     *
     * @param id the id of the armorVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armorVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/armor-versions/{id}")
    public ResponseEntity<ArmorVersionDTO> getArmorVersion(@PathVariable Long id) {
        log.debug("REST request to get ArmorVersion : {}", id);
        Optional<ArmorVersionDTO> armorVersionDTO = armorVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armorVersionDTO);
    }

    /**
     * {@code DELETE  /armor-versions/:id} : delete the "id" armorVersion.
     *
     * @param id the id of the armorVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/armor-versions/{id}")
    public ResponseEntity<Void> deleteArmorVersion(@PathVariable Long id) {
        log.debug("REST request to delete ArmorVersion : {}", id);
        armorVersionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
