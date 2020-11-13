package be.chesteric31.web.rest;

import be.chesteric31.service.ArmorVersionAttackService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.ArmorVersionAttackDTO;

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
 * REST controller for managing {@link be.chesteric31.domain.ArmorVersionAttack}.
 */
@RestController
@RequestMapping("/api")
public class ArmorVersionAttackResource {

    private final Logger log = LoggerFactory.getLogger(ArmorVersionAttackResource.class);

    private static final String ENTITY_NAME = "armorVersionAttack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmorVersionAttackService armorVersionAttackService;

    public ArmorVersionAttackResource(ArmorVersionAttackService armorVersionAttackService) {
        this.armorVersionAttackService = armorVersionAttackService;
    }

    /**
     * {@code POST  /armor-version-attacks} : Create a new armorVersionAttack.
     *
     * @param armorVersionAttackDTO the armorVersionAttackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armorVersionAttackDTO, or with status {@code 400 (Bad Request)} if the armorVersionAttack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/armor-version-attacks")
    public ResponseEntity<ArmorVersionAttackDTO> createArmorVersionAttack(@RequestBody ArmorVersionAttackDTO armorVersionAttackDTO) throws URISyntaxException {
        log.debug("REST request to save ArmorVersionAttack : {}", armorVersionAttackDTO);
        if (armorVersionAttackDTO.getId() != null) {
            throw new BadRequestAlertException("A new armorVersionAttack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmorVersionAttackDTO result = armorVersionAttackService.save(armorVersionAttackDTO);
        return ResponseEntity.created(new URI("/api/armor-version-attacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /armor-version-attacks} : Updates an existing armorVersionAttack.
     *
     * @param armorVersionAttackDTO the armorVersionAttackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armorVersionAttackDTO,
     * or with status {@code 400 (Bad Request)} if the armorVersionAttackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armorVersionAttackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/armor-version-attacks")
    public ResponseEntity<ArmorVersionAttackDTO> updateArmorVersionAttack(@RequestBody ArmorVersionAttackDTO armorVersionAttackDTO) throws URISyntaxException {
        log.debug("REST request to update ArmorVersionAttack : {}", armorVersionAttackDTO);
        if (armorVersionAttackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmorVersionAttackDTO result = armorVersionAttackService.save(armorVersionAttackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armorVersionAttackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /armor-version-attacks} : get all the armorVersionAttacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armorVersionAttacks in body.
     */
    @GetMapping("/armor-version-attacks")
    public ResponseEntity<List<ArmorVersionAttackDTO>> getAllArmorVersionAttacks(Pageable pageable) {
        log.debug("REST request to get a page of ArmorVersionAttacks");
        Page<ArmorVersionAttackDTO> page = armorVersionAttackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /armor-version-attacks/:id} : get the "id" armorVersionAttack.
     *
     * @param id the id of the armorVersionAttackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armorVersionAttackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/armor-version-attacks/{id}")
    public ResponseEntity<ArmorVersionAttackDTO> getArmorVersionAttack(@PathVariable Long id) {
        log.debug("REST request to get ArmorVersionAttack : {}", id);
        Optional<ArmorVersionAttackDTO> armorVersionAttackDTO = armorVersionAttackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armorVersionAttackDTO);
    }

    /**
     * {@code DELETE  /armor-version-attacks/:id} : delete the "id" armorVersionAttack.
     *
     * @param id the id of the armorVersionAttackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/armor-version-attacks/{id}")
    public ResponseEntity<Void> deleteArmorVersionAttack(@PathVariable Long id) {
        log.debug("REST request to delete ArmorVersionAttack : {}", id);
        armorVersionAttackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
