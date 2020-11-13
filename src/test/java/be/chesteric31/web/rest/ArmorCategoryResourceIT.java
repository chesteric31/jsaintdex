package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.ArmorCategory;
import be.chesteric31.repository.ArmorCategoryRepository;
import be.chesteric31.service.ArmorCategoryService;
import be.chesteric31.service.dto.ArmorCategoryDTO;
import be.chesteric31.service.mapper.ArmorCategoryMapper;

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
 * Integration tests for the {@link ArmorCategoryResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmorCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ArmorCategoryRepository armorCategoryRepository;

    @Autowired
    private ArmorCategoryMapper armorCategoryMapper;

    @Autowired
    private ArmorCategoryService armorCategoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArmorCategoryMockMvc;

    private ArmorCategory armorCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorCategory createEntity(EntityManager em) {
        ArmorCategory armorCategory = new ArmorCategory()
            .name(DEFAULT_NAME);
        return armorCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorCategory createUpdatedEntity(EntityManager em) {
        ArmorCategory armorCategory = new ArmorCategory()
            .name(UPDATED_NAME);
        return armorCategory;
    }

    @BeforeEach
    public void initTest() {
        armorCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmorCategory() throws Exception {
        int databaseSizeBeforeCreate = armorCategoryRepository.findAll().size();
        // Create the ArmorCategory
        ArmorCategoryDTO armorCategoryDTO = armorCategoryMapper.toDto(armorCategory);
        restArmorCategoryMockMvc.perform(post("/api/armor-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmorCategory in the database
        List<ArmorCategory> armorCategoryList = armorCategoryRepository.findAll();
        assertThat(armorCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ArmorCategory testArmorCategory = armorCategoryList.get(armorCategoryList.size() - 1);
        assertThat(testArmorCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createArmorCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armorCategoryRepository.findAll().size();

        // Create the ArmorCategory with an existing ID
        armorCategory.setId(1L);
        ArmorCategoryDTO armorCategoryDTO = armorCategoryMapper.toDto(armorCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmorCategoryMockMvc.perform(post("/api/armor-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorCategory in the database
        List<ArmorCategory> armorCategoryList = armorCategoryRepository.findAll();
        assertThat(armorCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArmorCategories() throws Exception {
        // Initialize the database
        armorCategoryRepository.saveAndFlush(armorCategory);

        // Get all the armorCategoryList
        restArmorCategoryMockMvc.perform(get("/api/armor-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armorCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getArmorCategory() throws Exception {
        // Initialize the database
        armorCategoryRepository.saveAndFlush(armorCategory);

        // Get the armorCategory
        restArmorCategoryMockMvc.perform(get("/api/armor-categories/{id}", armorCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armorCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingArmorCategory() throws Exception {
        // Get the armorCategory
        restArmorCategoryMockMvc.perform(get("/api/armor-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmorCategory() throws Exception {
        // Initialize the database
        armorCategoryRepository.saveAndFlush(armorCategory);

        int databaseSizeBeforeUpdate = armorCategoryRepository.findAll().size();

        // Update the armorCategory
        ArmorCategory updatedArmorCategory = armorCategoryRepository.findById(armorCategory.getId()).get();
        // Disconnect from session so that the updates on updatedArmorCategory are not directly saved in db
        em.detach(updatedArmorCategory);
        updatedArmorCategory
            .name(UPDATED_NAME);
        ArmorCategoryDTO armorCategoryDTO = armorCategoryMapper.toDto(updatedArmorCategory);

        restArmorCategoryMockMvc.perform(put("/api/armor-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ArmorCategory in the database
        List<ArmorCategory> armorCategoryList = armorCategoryRepository.findAll();
        assertThat(armorCategoryList).hasSize(databaseSizeBeforeUpdate);
        ArmorCategory testArmorCategory = armorCategoryList.get(armorCategoryList.size() - 1);
        assertThat(testArmorCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingArmorCategory() throws Exception {
        int databaseSizeBeforeUpdate = armorCategoryRepository.findAll().size();

        // Create the ArmorCategory
        ArmorCategoryDTO armorCategoryDTO = armorCategoryMapper.toDto(armorCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmorCategoryMockMvc.perform(put("/api/armor-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorCategory in the database
        List<ArmorCategory> armorCategoryList = armorCategoryRepository.findAll();
        assertThat(armorCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmorCategory() throws Exception {
        // Initialize the database
        armorCategoryRepository.saveAndFlush(armorCategory);

        int databaseSizeBeforeDelete = armorCategoryRepository.findAll().size();

        // Delete the armorCategory
        restArmorCategoryMockMvc.perform(delete("/api/armor-categories/{id}", armorCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArmorCategory> armorCategoryList = armorCategoryRepository.findAll();
        assertThat(armorCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
