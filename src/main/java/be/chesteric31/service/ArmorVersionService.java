package be.chesteric31.service;

import be.chesteric31.domain.ArmorVersion;
import be.chesteric31.repository.ArmorVersionRepository;
import be.chesteric31.service.dto.ArmorVersionDTO;
import be.chesteric31.service.mapper.ArmorVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArmorVersion}.
 */
@Service
@Transactional
public class ArmorVersionService {

    private final Logger log = LoggerFactory.getLogger(ArmorVersionService.class);

    private final ArmorVersionRepository armorVersionRepository;

    private final ArmorVersionMapper armorVersionMapper;

    public ArmorVersionService(ArmorVersionRepository armorVersionRepository, ArmorVersionMapper armorVersionMapper) {
        this.armorVersionRepository = armorVersionRepository;
        this.armorVersionMapper = armorVersionMapper;
    }

    /**
     * Save a armorVersion.
     *
     * @param armorVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public ArmorVersionDTO save(ArmorVersionDTO armorVersionDTO) {
        log.debug("Request to save ArmorVersion : {}", armorVersionDTO);
        ArmorVersion armorVersion = armorVersionMapper.toEntity(armorVersionDTO);
        armorVersion = armorVersionRepository.save(armorVersion);
        return armorVersionMapper.toDto(armorVersion);
    }

    /**
     * Get all the armorVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArmorVersionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArmorVersions");
        return armorVersionRepository.findAll(pageable)
            .map(armorVersionMapper::toDto);
    }


    /**
     * Get one armorVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArmorVersionDTO> findOne(Long id) {
        log.debug("Request to get ArmorVersion : {}", id);
        return armorVersionRepository.findById(id)
            .map(armorVersionMapper::toDto);
    }

    /**
     * Delete the armorVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArmorVersion : {}", id);
        armorVersionRepository.deleteById(id);
    }
}
