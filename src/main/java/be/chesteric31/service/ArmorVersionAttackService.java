package be.chesteric31.service;

import be.chesteric31.domain.ArmorVersionAttack;
import be.chesteric31.repository.ArmorVersionAttackRepository;
import be.chesteric31.service.dto.ArmorVersionAttackDTO;
import be.chesteric31.service.mapper.ArmorVersionAttackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArmorVersionAttack}.
 */
@Service
@Transactional
public class ArmorVersionAttackService {

    private final Logger log = LoggerFactory.getLogger(ArmorVersionAttackService.class);

    private final ArmorVersionAttackRepository armorVersionAttackRepository;

    private final ArmorVersionAttackMapper armorVersionAttackMapper;

    public ArmorVersionAttackService(ArmorVersionAttackRepository armorVersionAttackRepository, ArmorVersionAttackMapper armorVersionAttackMapper) {
        this.armorVersionAttackRepository = armorVersionAttackRepository;
        this.armorVersionAttackMapper = armorVersionAttackMapper;
    }

    /**
     * Save a armorVersionAttack.
     *
     * @param armorVersionAttackDTO the entity to save.
     * @return the persisted entity.
     */
    public ArmorVersionAttackDTO save(ArmorVersionAttackDTO armorVersionAttackDTO) {
        log.debug("Request to save ArmorVersionAttack : {}", armorVersionAttackDTO);
        ArmorVersionAttack armorVersionAttack = armorVersionAttackMapper.toEntity(armorVersionAttackDTO);
        armorVersionAttack = armorVersionAttackRepository.save(armorVersionAttack);
        return armorVersionAttackMapper.toDto(armorVersionAttack);
    }

    /**
     * Get all the armorVersionAttacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArmorVersionAttackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArmorVersionAttacks");
        return armorVersionAttackRepository.findAll(pageable)
            .map(armorVersionAttackMapper::toDto);
    }


    /**
     * Get one armorVersionAttack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArmorVersionAttackDTO> findOne(Long id) {
        log.debug("Request to get ArmorVersionAttack : {}", id);
        return armorVersionAttackRepository.findById(id)
            .map(armorVersionAttackMapper::toDto);
    }

    /**
     * Delete the armorVersionAttack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArmorVersionAttack : {}", id);
        armorVersionAttackRepository.deleteById(id);
    }
}
