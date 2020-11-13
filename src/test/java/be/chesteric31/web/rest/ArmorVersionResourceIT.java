package be.chesteric31.web.rest;

import be.chesteric31.JsaintdexApp;
import be.chesteric31.domain.ArmorVersion;
import be.chesteric31.repository.ArmorVersionRepository;
import be.chesteric31.service.ArmorVersionService;
import be.chesteric31.service.dto.ArmorVersionDTO;
import be.chesteric31.service.mapper.ArmorVersionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArmorVersionResource} REST controller.
 */
@SpringBootTest(classes = JsaintdexApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmorVersionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ArmorVersionRepository armorVersionRepository;

    @Autowired
    private ArmorVersionMapper armorVersionMapper;

    @Autowired
    private ArmorVersionService armorVersionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArmorVersionMockMvc;

    private ArmorVersion armorVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorVersion createEntity(EntityManager em) {
        ArmorVersion armorVersion = new ArmorVersion()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return armorVersion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmorVersion createUpdatedEntity(EntityManager em) {
        ArmorVersion armorVersion = new ArmorVersion()
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return armorVersion;
    }

    @BeforeEach
    public void initTest() {
        armorVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmorVersion() throws Exception {
        int databaseSizeBeforeCreate = armorVersionRepository.findAll().size();
        // Create the ArmorVersion
        ArmorVersionDTO armorVersionDTO = armorVersionMapper.toDto(armorVersion);
        restArmorVersionMockMvc.perform(post("/api/armor-versions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmorVersion in the database
        List<ArmorVersion> armorVersionList = armorVersionRepository.findAll();
        assertThat(armorVersionList).hasSize(databaseSizeBeforeCreate + 1);
        ArmorVersion testArmorVersion = armorVersionList.get(armorVersionList.size() - 1);
        assertThat(testArmorVersion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArmorVersion.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testArmorVersion.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createArmorVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armorVersionRepository.findAll().size();

        // Create the ArmorVersion with an existing ID
        armorVersion.setId(1L);
        ArmorVersionDTO armorVersionDTO = armorVersionMapper.toDto(armorVersion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmorVersionMockMvc.perform(post("/api/armor-versions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorVersion in the database
        List<ArmorVersion> armorVersionList = armorVersionRepository.findAll();
        assertThat(armorVersionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArmorVersions() throws Exception {
        // Initialize the database
        armorVersionRepository.saveAndFlush(armorVersion);

        // Get all the armorVersionList
        restArmorVersionMockMvc.perform(get("/api/armor-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armorVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getArmorVersion() throws Exception {
        // Initialize the database
        armorVersionRepository.saveAndFlush(armorVersion);

        // Get the armorVersion
        restArmorVersionMockMvc.perform(get("/api/armor-versions/{id}", armorVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armorVersion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingArmorVersion() throws Exception {
        // Get the armorVersion
        restArmorVersionMockMvc.perform(get("/api/armor-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmorVersion() throws Exception {
        // Initialize the database
        armorVersionRepository.saveAndFlush(armorVersion);

        int databaseSizeBeforeUpdate = armorVersionRepository.findAll().size();

        // Update the armorVersion
        ArmorVersion updatedArmorVersion = armorVersionRepository.findById(armorVersion.getId()).get();
        // Disconnect from session so that the updates on updatedArmorVersion are not directly saved in db
        em.detach(updatedArmorVersion);
        updatedArmorVersion
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ArmorVersionDTO armorVersionDTO = armorVersionMapper.toDto(updatedArmorVersion);

        restArmorVersionMockMvc.perform(put("/api/armor-versions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionDTO)))
            .andExpect(status().isOk());

        // Validate the ArmorVersion in the database
        List<ArmorVersion> armorVersionList = armorVersionRepository.findAll();
        assertThat(armorVersionList).hasSize(databaseSizeBeforeUpdate);
        ArmorVersion testArmorVersion = armorVersionList.get(armorVersionList.size() - 1);
        assertThat(testArmorVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArmorVersion.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testArmorVersion.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArmorVersion() throws Exception {
        int databaseSizeBeforeUpdate = armorVersionRepository.findAll().size();

        // Create the ArmorVersion
        ArmorVersionDTO armorVersionDTO = armorVersionMapper.toDto(armorVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmorVersionMockMvc.perform(put("/api/armor-versions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armorVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmorVersion in the database
        List<ArmorVersion> armorVersionList = armorVersionRepository.findAll();
        assertThat(armorVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmorVersion() throws Exception {
        // Initialize the database
        armorVersionRepository.saveAndFlush(armorVersion);

        int databaseSizeBeforeDelete = armorVersionRepository.findAll().size();

        // Delete the armorVersion
        restArmorVersionMockMvc.perform(delete("/api/armor-versions/{id}", armorVersion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArmorVersion> armorVersionList = armorVersionRepository.findAll();
        assertThat(armorVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
