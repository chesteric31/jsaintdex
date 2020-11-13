package be.chesteric31.web.rest;

import be.chesteric31.service.ArmorCategoryService;
import be.chesteric31.web.rest.errors.BadRequestAlertException;
import be.chesteric31.service.dto.ArmorCategoryDTO;

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
 * REST controller for managing {@link be.chesteric31.domain.ArmorCategory}.
 */
@RestController
@RequestMapping("/api")
public class ArmorCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ArmorCategoryResource.class);

    private static final String ENTITY_NAME = "armorCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmorCategoryService armorCategoryService;

    public ArmorCategoryResource(ArmorCategoryService armorCategoryService) {
        this.armorCategoryService = armorCategoryService;
    }

    /**
     * {@code POST  /armor-categories} : Create a new armorCategory.
     *
     * @param armorCategoryDTO the armorCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armorCategoryDTO, or with status {@code 400 (Bad Request)} if the armorCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/armor-categories")
    public ResponseEntity<ArmorCategoryDTO> createArmorCategory(@RequestBody ArmorCategoryDTO armorCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ArmorCategory : {}", armorCategoryDTO);
        if (armorCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new armorCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmorCategoryDTO result = armorCategoryService.save(armorCategoryDTO);
        return ResponseEntity.created(new URI("/api/armor-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /armor-categories} : Updates an existing armorCategory.
     *
     * @param armorCategoryDTO the armorCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armorCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the armorCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armorCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/armor-categories")
    public ResponseEntity<ArmorCategoryDTO> updateArmorCategory(@RequestBody ArmorCategoryDTO armorCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ArmorCategory : {}", armorCategoryDTO);
        if (armorCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmorCategoryDTO result = armorCategoryService.save(armorCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armorCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /armor-categories} : get all the armorCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armorCategories in body.
     */
    @GetMapping("/armor-categories")
    public ResponseEntity<List<ArmorCategoryDTO>> getAllArmorCategories(Pageable pageable) {
        log.debug("REST request to get a page of ArmorCategories");
        Page<ArmorCategoryDTO> page = armorCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /armor-categories/:id} : get the "id" armorCategory.
     *
     * @param id the id of the armorCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armorCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/armor-categories/{id}")
    public ResponseEntity<ArmorCategoryDTO> getArmorCategory(@PathVariable Long id) {
        log.debug("REST request to get ArmorCategory : {}", id);
        Optional<ArmorCategoryDTO> armorCategoryDTO = armorCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armorCategoryDTO);
    }

    /**
     * {@code DELETE  /armor-categories/:id} : delete the "id" armorCategory.
     *
     * @param id the id of the armorCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/armor-categories/{id}")
    public ResponseEntity<Void> deleteArmorCategory(@PathVariable Long id) {
        log.debug("REST request to delete ArmorCategory : {}", id);
        armorCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
