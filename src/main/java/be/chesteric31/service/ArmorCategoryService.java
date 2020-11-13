package be.chesteric31.service;

import be.chesteric31.domain.ArmorCategory;
import be.chesteric31.repository.ArmorCategoryRepository;
import be.chesteric31.service.dto.ArmorCategoryDTO;
import be.chesteric31.service.mapper.ArmorCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArmorCategory}.
 */
@Service
@Transactional
public class ArmorCategoryService {

    private final Logger log = LoggerFactory.getLogger(ArmorCategoryService.class);

    private final ArmorCategoryRepository armorCategoryRepository;

    private final ArmorCategoryMapper armorCategoryMapper;

    public ArmorCategoryService(ArmorCategoryRepository armorCategoryRepository, ArmorCategoryMapper armorCategoryMapper) {
        this.armorCategoryRepository = armorCategoryRepository;
        this.armorCategoryMapper = armorCategoryMapper;
    }

    /**
     * Save a armorCategory.
     *
     * @param armorCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ArmorCategoryDTO save(ArmorCategoryDTO armorCategoryDTO) {
        log.debug("Request to save ArmorCategory : {}", armorCategoryDTO);
        ArmorCategory armorCategory = armorCategoryMapper.toEntity(armorCategoryDTO);
        armorCategory = armorCategoryRepository.save(armorCategory);
        return armorCategoryMapper.toDto(armorCategory);
    }

    /**
     * Get all the armorCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArmorCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArmorCategories");
        return armorCategoryRepository.findAll(pageable)
            .map(armorCategoryMapper::toDto);
    }


    /**
     * Get one armorCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArmorCategoryDTO> findOne(Long id) {
        log.debug("Request to get ArmorCategory : {}", id);
        return armorCategoryRepository.findById(id)
            .map(armorCategoryMapper::toDto);
    }

    /**
     * Delete the armorCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArmorCategory : {}", id);
        armorCategoryRepository.deleteById(id);
    }
}
