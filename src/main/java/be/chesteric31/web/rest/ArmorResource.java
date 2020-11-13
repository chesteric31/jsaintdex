package be.chesteric31.web.rest;

import be.chesteric31.service.ArmorService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.ArmorDTO;

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
 * REST controller for managing {@link be.chesteric31.domain.Armor}.
 */
@RestController
@RequestMapping("/api")
public class ArmorResource {

    private final Logger log = LoggerFactory.getLogger(ArmorResource.class);

    private static final String ENTITY_NAME = "armor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmorService armorService;

    public ArmorResource(ArmorService armorService) {
        this.armorService = armorService;
    }

    /**
     * {@code POST  /armors} : Create a new armor.
     *
     * @param armorDTO the armorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armorDTO, or with status {@code 400 (Bad Request)} if the armor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/armors")
    public ResponseEntity<ArmorDTO> createArmor(@RequestBody ArmorDTO armorDTO) throws URISyntaxException {
        log.debug("REST request to save Armor : {}", armorDTO);
        if (armorDTO.getId() != null) {
            throw new BadRequestAlertException("A new armor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmorDTO result = armorService.save(armorDTO);
        return ResponseEntity.created(new URI("/api/armors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /armors} : Updates an existing armor.
     *
     * @param armorDTO the armorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armorDTO,
     * or with status {@code 400 (Bad Request)} if the armorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/armors")
    public ResponseEntity<ArmorDTO> updateArmor(@RequestBody ArmorDTO armorDTO) throws URISyntaxException {
        log.debug("REST request to update Armor : {}", armorDTO);
        if (armorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmorDTO result = armorService.save(armorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /armors} : get all the armors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armors in body.
     */
    @GetMapping("/armors")
    public ResponseEntity<List<ArmorDTO>> getAllArmors(Pageable pageable) {
        log.debug("REST request to get a page of Armors");
        Page<ArmorDTO> page = armorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /armors/:id} : get the "id" armor.
     *
     * @param id the id of the armorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/armors/{id}")
    public ResponseEntity<ArmorDTO> getArmor(@PathVariable Long id) {
        log.debug("REST request to get Armor : {}", id);
        Optional<ArmorDTO> armorDTO = armorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armorDTO);
    }

    /**
     * {@code DELETE  /armors/:id} : delete the "id" armor.
     *
     * @param id the id of the armorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/armors/{id}")
    public ResponseEntity<Void> deleteArmor(@PathVariable Long id) {
        log.debug("REST request to delete Armor : {}", id);
        armorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
