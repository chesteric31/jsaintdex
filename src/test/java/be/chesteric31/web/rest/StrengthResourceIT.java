package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.Strength;
import be.chesteric31.repository.StrengthRepository;
import be.chesteric31.service.StrengthService;
import be.chesteric31.service.dto.StrengthDTO;
import be.chesteric31.service.mapper.StrengthMapper;

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
 * Integration tests for the {@link StrengthResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StrengthResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StrengthRepository strengthRepository;

    @Autowired
    private StrengthMapper strengthMapper;

    @Autowired
    private StrengthService strengthService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrengthMockMvc;

    private Strength strength;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strength createEntity(EntityManager em) {
        Strength strength = new Strength()
            .name(DEFAULT_NAME);
        return strength;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strength createUpdatedEntity(EntityManager em) {
        Strength strength = new Strength()
            .name(UPDATED_NAME);
        return strength;
    }

    @BeforeEach
    public void initTest() {
        strength = createEntity(em);
    }

    @Test
    @Transactional
    public void createStrength() throws Exception {
        int databaseSizeBeforeCreate = strengthRepository.findAll().size();
        // Create the Strength
        StrengthDTO strengthDTO = strengthMapper.toDto(strength);
        restStrengthMockMvc.perform(post("/api/strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(strengthDTO)))
            .andExpect(status().isCreated());

        // Validate the Strength in the database
        List<Strength> strengthList = strengthRepository.findAll();
        assertThat(strengthList).hasSize(databaseSizeBeforeCreate + 1);
        Strength testStrength = strengthList.get(strengthList.size() - 1);
        assertThat(testStrength.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStrengthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = strengthRepository.findAll().size();

        // Create the Strength with an existing ID
        strength.setId(1L);
        StrengthDTO strengthDTO = strengthMapper.toDto(strength);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrengthMockMvc.perform(post("/api/strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(strengthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Strength in the database
        List<Strength> strengthList = strengthRepository.findAll();
        assertThat(strengthList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStrengths() throws Exception {
        // Initialize the database
        strengthRepository.saveAndFlush(strength);

        // Get all the strengthList
        restStrengthMockMvc.perform(get("/api/strengths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strength.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStrength() throws Exception {
        // Initialize the database
        strengthRepository.saveAndFlush(strength);

        // Get the strength
        restStrengthMockMvc.perform(get("/api/strengths/{id}", strength.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strength.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingStrength() throws Exception {
        // Get the strength
        restStrengthMockMvc.perform(get("/api/strengths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStrength() throws Exception {
        // Initialize the database
        strengthRepository.saveAndFlush(strength);

        int databaseSizeBeforeUpdate = strengthRepository.findAll().size();

        // Update the strength
        Strength updatedStrength = strengthRepository.findById(strength.getId()).get();
        // Disconnect from session so that the updates on updatedStrength are not directly saved in db
        em.detach(updatedStrength);
        updatedStrength
            .name(UPDATED_NAME);
        StrengthDTO strengthDTO = strengthMapper.toDto(updatedStrength);

        restStrengthMockMvc.perform(put("/api/strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(strengthDTO)))
            .andExpect(status().isOk());

        // Validate the Strength in the database
        List<Strength> strengthList = strengthRepository.findAll();
        assertThat(strengthList).hasSize(databaseSizeBeforeUpdate);
        Strength testStrength = strengthList.get(strengthList.size() - 1);
        assertThat(testStrength.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStrength() throws Exception {
        int databaseSizeBeforeUpdate = strengthRepository.findAll().size();

        // Create the Strength
        StrengthDTO strengthDTO = strengthMapper.toDto(strength);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrengthMockMvc.perform(put("/api/strengths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(strengthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Strength in the database
        List<Strength> strengthList = strengthRepository.findAll();
        assertThat(strengthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStrength() throws Exception {
        // Initialize the database
        strengthRepository.saveAndFlush(strength);

        int databaseSizeBeforeDelete = strengthRepository.findAll().size();

        // Delete the strength
        restStrengthMockMvc.perform(delete("/api/strengths/{id}", strength.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Strength> strengthList = strengthRepository.findAll();
        assertThat(strengthList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
