package be.chesteric31.service;

import be.chesteric31.domain.ArmorStrength;
import be.chesteric31.repository.ArmorStrengthRepository;
import be.chesteric31.service.dto.ArmorStrengthDTO;
import be.chesteric31.service.mapper.ArmorStrengthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArmorStrength}.
 */
@Service
@Transactional
public class ArmorStrengthService {

    private final Logger log = LoggerFactory.getLogger(ArmorStrengthService.class);

    private final ArmorStrengthRepository armorStrengthRepository;

    private final ArmorStrengthMapper armorStrengthMapper;

    public ArmorStrengthService(ArmorStrengthRepository armorStrengthRepository, ArmorStrengthMapper armorStrengthMapper) {
        this.armorStrengthRepository = armorStrengthRepository;
        this.armorStrengthMapper = armorStrengthMapper;
    }

    /**
     * Save a armorStrength.
     *
     * @param armorStrengthDTO the entity to save.
     * @return the persisted entity.
     */
    public ArmorStrengthDTO save(ArmorStrengthDTO armorStrengthDTO) {
        log.debug("Request to save ArmorStrength : {}", armorStrengthDTO);
        ArmorStrength armorStrength = armorStrengthMapper.toEntity(armorStrengthDTO);
        armorStrength = armorStrengthRepository.save(armorStrength);
        return armorStrengthMapper.toDto(armorStrength);
    }

    /**
     * Get all the armorStrengths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArmorStrengthDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArmorStrengths");
        return armorStrengthRepository.findAll(pageable)
            .map(armorStrengthMapper::toDto);
    }


    /**
     * Get one armorStrength by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArmorStrengthDTO> findOne(Long id) {
        log.debug("Request to get ArmorStrength : {}", id);
        return armorStrengthRepository.findById(id)
            .map(armorStrengthMapper::toDto);
    }

    /**
     * Delete the armorStrength by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArmorStrength : {}", id);
        armorStrengthRepository.deleteById(id);
    }
}
