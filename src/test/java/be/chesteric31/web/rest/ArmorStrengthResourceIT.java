package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.ArmorStrength;
import be.chesteric31.repository.ArmorStrengthRepository;
import be.chesteric31.service.ArmorStrengthService;
import be.chesteric31.service.dto.ArmorStrengthDTO;
import be.chesteric31.service.mapper.ArmorStrengthMapper;

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
 * Integration tests for the {@link ArmorStrengthResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmorStrengthResourceIT {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private ArmorStrengthRepository armorStrengthRepository;

    @Autowired
    private ArmorStrengthMapper armorStrengthMapper;

    @Autowired
    private ArmorStrengthService armorStrengthService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArmorStrengthMockMvc;

    private ArmorStrength armorStrength;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorStrength createEntity(EntityManager em) {
        ArmorStrength armorStrength = new ArmorStrength()
            .value(DEFAULT_VALUE);
        return armorStrength;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorStrength createUpdatedEntity(EntityManager em) {
        ArmorStrength armorStrength = new ArmorStrength()
            .value(UPDATED_VALUE);
        return armorStrength;
    }

    @BeforeEach
    public void initTest() {
        armorStrength = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmorStrength() throws Exception {
        int databaseSizeBeforeCreate = armorStrengthRepository.findAll().size();
        // Create the ArmorStrength
        ArmorStrengthDTO armorStrengthDTO = armorStrengthMapper.toDto(armorStrength);
        restArmorStrengthMockMvc.perform(post("/api/armor-strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorStrengthDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmorStrength in the database
        List<ArmorStrength> armorStrengthList = armorStrengthRepository.findAll();
        assertThat(armorStrengthList).hasSize(databaseSizeBeforeCreate + 1);
        ArmorStrength testArmorStrength = armorStrengthList.get(armorStrengthList.size() - 1);
        assertThat(testArmorStrength.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createArmorStrengthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armorStrengthRepository.findAll().size();

        // Create the ArmorStrength with an existing ID
        armorStrength.setId(1L);
        ArmorStrengthDTO armorStrengthDTO = armorStrengthMapper.toDto(armorStrength);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmorStrengthMockMvc.perform(post("/api/armor-strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorStrengthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorStrength in the database
        List<ArmorStrength> armorStrengthList = armorStrengthRepository.findAll();
        assertThat(armorStrengthList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArmorStrengths() throws Exception {
        // Initialize the database
        armorStrengthRepository.saveAndFlush(armorStrength);

        // Get all the armorStrengthList
        restArmorStrengthMockMvc.perform(get("/api/armor-strengths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armorStrength.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getArmorStrength() throws Exception {
        // Initialize the database
        armorStrengthRepository.saveAndFlush(armorStrength);

        // Get the armorStrength
        restArmorStrengthMockMvc.perform(get("/api/armor-strengths/{id}", armorStrength.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armorStrength.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingArmorStrength() throws Exception {
        // Get the armorStrength
        restArmorStrengthMockMvc.perform(get("/api/armor-strengths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmorStrength() throws Exception {
        // Initialize the database
        armorStrengthRepository.saveAndFlush(armorStrength);

        int databaseSizeBeforeUpdate = armorStrengthRepository.findAll().size();

        // Update the armorStrength
        ArmorStrength updatedArmorStrength = armorStrengthRepository.findById(armorStrength.getId()).get();
        // Disconnect from session so that the updates on updatedArmorStrength are not directly saved in db
        em.detach(updatedArmorStrength);
        updatedArmorStrength
            .value(UPDATED_VALUE);
        ArmorStrengthDTO armorStrengthDTO = armorStrengthMapper.toDto(updatedArmorStrength);

        restArmorStrengthMockMvc.perform(put("/api/armor-strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorStrengthDTO)))
            .andExpect(status().isOk());

        // Validate the ArmorStrength in the database
        List<ArmorStrength> armorStrengthList = armorStrengthRepository.findAll();
        assertThat(armorStrengthList).hasSize(databaseSizeBeforeUpdate);
        ArmorStrength testArmorStrength = armorStrengthList.get(armorStrengthList.size() - 1);
        assertThat(testArmorStrength.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingArmorStrength() throws Exception {
        int databaseSizeBeforeUpdate = armorStrengthRepository.findAll().size();

        // Create the ArmorStrength
        ArmorStrengthDTO armorStrengthDTO = armorStrengthMapper.toDto(armorStrength);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmorStrengthMockMvc.perform(put("/api/armor-strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorStrengthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorStrength in the database
        List<ArmorStrength> armorStrengthList = armorStrengthRepository.findAll();
        assertThat(armorStrengthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmorStrength() throws Exception {
        // Initialize the database
        armorStrengthRepository.saveAndFlush(armorStrength);

        int databaseSizeBeforeDelete = armorStrengthRepository.findAll().size();

        // Delete the armorStrength
        restArmorStrengthMockMvc.perform(delete("/api/armor-strengths/{id}", armorStrength.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArmorStrength> armorStrengthList = armorStrengthRepository.findAll();
        assertThat(armorStrengthList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
