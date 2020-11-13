package be.chesteric31.service;

import be.chesteric31.domain.Armor;
import be.chesteric31.repository.ArmorRepository;
import be.chesteric31.service.dto.ArmorDTO;
import be.chesteric31.service.mapper.ArmorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Armor}.
 */
@Service
@Transactional
public class ArmorService {

    private final Logger log = LoggerFactory.getLogger(ArmorService.class);

    private final ArmorRepository armorRepository;

    private final ArmorMapper armorMapper;

    public ArmorService(ArmorRepository armorRepository, ArmorMapper armorMapper) {
        this.armorRepository = armorRepository;
        this.armorMapper = armorMapper;
    }

    /**
     * Save a armor.
     *
     * @param armorDTO the entity to save.
     * @return the persisted entity.
     */
    public ArmorDTO save(ArmorDTO armorDTO) {
        log.debug("Request to save Armor : {}", armorDTO);
        Armor armor = armorMapper.toEntity(armorDTO);
        armor = armorRepository.save(armor);
        return armorMapper.toDto(armor);
    }

    /**
     * Get all the armors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArmorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Armors");
        return armorRepository.findAll(pageable)
            .map(armorMapper::toDto);
    }


    /**
     * Get one armor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArmorDTO> findOne(Long id) {
        log.debug("Request to get Armor : {}", id);
        return armorRepository.findById(id)
            .map(armorMapper::toDto);
    }

    /**
     * Delete the armor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Armor : {}", id);
        armorRepository.deleteById(id);
    }
}
