package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.Armor;
import be.chesteric31.repository.ArmorRepository;
import be.chesteric31.service.ArmorService;
import be.chesteric31.service.dto.ArmorDTO;
import be.chesteric31.service.mapper.ArmorMapper;

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
 * Integration tests for the {@link ArmorResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ArmorRepository armorRepository;

    @Autowired
    private ArmorMapper armorMapper;

    @Autowired
    private ArmorService armorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArmorMockMvc;

    private Armor armor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Armor createEntity(EntityManager em) {
        Armor armor = new Armor()
            .name(DEFAULT_NAME);
        return armor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Armor createUpdatedEntity(EntityManager em) {
        Armor armor = new Armor()
            .name(UPDATED_NAME);
        return armor;
    }

    @BeforeEach
    public void initTest() {
        armor = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmor() throws Exception {
        int databaseSizeBeforeCreate = armorRepository.findAll().size();
        // Create the Armor
        ArmorDTO armorDTO = armorMapper.toDto(armor);
        restArmorMockMvc.perform(post("/api/armors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorDTO)))
            .andExpect(status().isCreated());

        // Validate the Armor in the database
        List<Armor> armorList = armorRepository.findAll();
        assertThat(armorList).hasSize(databaseSizeBeforeCreate + 1);
        Armor testArmor = armorList.get(armorList.size() - 1);
        assertThat(testArmor.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createArmorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armorRepository.findAll().size();

        // Create the Armor with an existing ID
        armor.setId(1L);
        ArmorDTO armorDTO = armorMapper.toDto(armor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmorMockMvc.perform(post("/api/armors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Armor in the database
        List<Armor> armorList = armorRepository.findAll();
        assertThat(armorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArmors() throws Exception {
        // Initialize the database
        armorRepository.saveAndFlush(armor);

        // Get all the armorList
        restArmorMockMvc.perform(get("/api/armors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getArmor() throws Exception {
        // Initialize the database
        armorRepository.saveAndFlush(armor);

        // Get the armor
        restArmorMockMvc.perform(get("/api/armors/{id}", armor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingArmor() throws Exception {
        // Get the armor
        restArmorMockMvc.perform(get("/api/armors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmor() throws Exception {
        // Initialize the database
        armorRepository.saveAndFlush(armor);

        int databaseSizeBeforeUpdate = armorRepository.findAll().size();

        // Update the armor
        Armor updatedArmor = armorRepository.findById(armor.getId()).get();
        // Disconnect from session so that the updates on updatedArmor are not directly saved in db
        em.detach(updatedArmor);
        updatedArmor
            .name(UPDATED_NAME);
        ArmorDTO armorDTO = armorMapper.toDto(updatedArmor);

        restArmorMockMvc.perform(put("/api/armors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorDTO)))
            .andExpect(status().isOk());

        // Validate the Armor in the database
        List<Armor> armorList = armorRepository.findAll();
        assertThat(armorList).hasSize(databaseSizeBeforeUpdate);
        Armor testArmor = armorList.get(armorList.size() - 1);
        assertThat(testArmor.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingArmor() throws Exception {
        int databaseSizeBeforeUpdate = armorRepository.findAll().size();

        // Create the Armor
        ArmorDTO armorDTO = armorMapper.toDto(armor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmorMockMvc.perform(put("/api/armors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Armor in the database
        List<Armor> armorList = armorRepository.findAll();
        assertThat(armorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmor() throws Exception {
        // Initialize the database
        armorRepository.saveAndFlush(armor);

        int databaseSizeBeforeDelete = armorRepository.findAll().size();

        // Delete the armor
        restArmorMockMvc.perform(delete("/api/armors/{id}", armor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Armor> armorList = armorRepository.findAll();
        assertThat(armorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
