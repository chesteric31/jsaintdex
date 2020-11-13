package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.ArmorVersionAttack;
import be.chesteric31.repository.ArmorVersionAttackRepository;
import be.chesteric31.service.ArmorVersionAttackService;
import be.chesteric31.service.dto.ArmorVersionAttackDTO;
import be.chesteric31.service.mapper.ArmorVersionAttackMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArmorVersionAttackResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmorVersionAttackResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ArmorVersionAttackRepository armorVersionAttackRepository;

    @Autowired
    private ArmorVersionAttackMapper armorVersionAttackMapper;

    @Autowired
    private ArmorVersionAttackService armorVersionAttackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArmorVersionAttackMockMvc;

    private ArmorVersionAttack armorVersionAttack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorVersionAttack createEntity(EntityManager em) {
        ArmorVersionAttack armorVersionAttack = new ArmorVersionAttack()
            .name(DEFAULT_NAME);
        return armorVersionAttack;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorVersionAttack createUpdatedEntity(EntityManager em) {
        ArmorVersionAttack armorVersionAttack = new ArmorVersionAttack()
            .name(UPDATED_NAME);
        return armorVersionAttack;
    }

    @BeforeEach
    public void initTest() {
        armorVersionAttack = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmorVersionAttack() throws Exception {
        int databaseSizeBeforeCreate = armorVersionAttackRepository.findAll().size();
        // Create the ArmorVersionAttack
        ArmorVersionAttackDTO armorVersionAttackDTO = armorVersionAttackMapper.toDto(armorVersionAttack);
        restArmorVersionAttackMockMvc.perform(post("/api/armor-version-attacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionAttackDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmorVersionAttack in the database
        List<ArmorVersionAttack> armorVersionAttackList = armorVersionAttackRepository.findAll();
        assertThat(armorVersionAttackList).hasSize(databaseSizeBeforeCreate + 1);
        ArmorVersionAttack testArmorVersionAttack = armorVersionAttackList.get(armorVersionAttackList.size() - 1);
        assertThat(testArmorVersionAttack.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createArmorVersionAttackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armorVersionAttackRepository.findAll().size();

        // Create the ArmorVersionAttack with an existing ID
        armorVersionAttack.setId(1L);
        ArmorVersionAttackDTO armorVersionAttackDTO = armorVersionAttackMapper.toDto(armorVersionAttack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmorVersionAttackMockMvc.perform(post("/api/armor-version-attacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionAttackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorVersionAttack in the database
        List<ArmorVersionAttack> armorVersionAttackList = armorVersionAttackRepository.findAll();
        assertThat(armorVersionAttackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArmorVersionAttacks() throws Exception {
        // Initialize the database
        armorVersionAttackRepository.saveAndFlush(armorVersionAttack);

        // Get all the armorVersionAttackList
        restArmorVersionAttackMockMvc.perform(get("/api/armor-version-attacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armorVersionAttack.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getArmorVersionAttack() throws Exception {
        // Initialize the database
        armorVersionAttackRepository.saveAndFlush(armorVersionAttack);

        // Get the armorVersionAttack
        restArmorVersionAttackMockMvc.perform(get("/api/armor-version-attacks/{id}", armorVersionAttack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armorVersionAttack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingArmorVersionAttack() throws Exception {
        // Get the armorVersionAttack
        restArmorVersionAttackMockMvc.perform(get("/api/armor-version-attacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmorVersionAttack() throws Exception {
        // Initialize the database
        armorVersionAttackRepository.saveAndFlush(armorVersionAttack);

        int databaseSizeBeforeUpdate = armorVersionAttackRepository.findAll().size();

        // Update the armorVersionAttack
        ArmorVersionAttack updatedArmorVersionAttack = armorVersionAttackRepository.findById(armorVersionAttack.getId()).get();
        // Disconnect from session so that the updates on updatedArmorVersionAttack are not directly saved in db
        em.detach(updatedArmorVersionAttack);
        updatedArmorVersionAttack
            .name(UPDATED_NAME);
        ArmorVersionAttackDTO armorVersionAttackDTO = armorVersionAttackMapper.toDto(updatedArmorVersionAttack);

        restArmorVersionAttackMockMvc.perform(put("/api/armor-version-attacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionAttackDTO)))
            .andExpect(status().isOk());

        // Validate the ArmorVersionAttack in the database
        List<ArmorVersionAttack> armorVersionAttackList = armorVersionAttackRepository.findAll();
        assertThat(armorVersionAttackList).hasSize(databaseSizeBeforeUpdate);
        ArmorVersionAttack testArmorVersionAttack = armorVersionAttackList.get(armorVersionAttackList.size() - 1);
        assertThat(testArmorVersionAttack.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingArmorVersionAttack() throws Exception {
        int databaseSizeBeforeUpdate = armorVersionAttackRepository.findAll().size();

        // Create the ArmorVersionAttack
        ArmorVersionAttackDTO armorVersionAttackDTO = armorVersionAttackMapper.toDto(armorVersionAttack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmorVersionAttackMockMvc.perform(put("/api/armor-version-attacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionAttackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorVersionAttack in the database
        List<ArmorVersionAttack> armorVersionAttackList = armorVersionAttackRepository.findAll();
        assertThat(armorVersionAttackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmorVersionAttack() throws Exception {
        // Initialize the database
        armorVersionAttackRepository.saveAndFlush(armorVersionAttack);

        int databaseSizeBeforeDelete = armorVersionAttackRepository.findAll().size();

        // Delete the armorVersionAttack
        restArmorVersionAttackMockMvc.perform(delete("/api/armor-version-attacks/{id}", armorVersionAttack.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArmorVersionAttack> armorVersionAttackList = armorVersionAttackRepository.findAll();
        assertThat(armorVersionAttackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
