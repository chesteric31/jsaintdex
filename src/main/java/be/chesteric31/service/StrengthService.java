package be.chesteric31.service;

import be.chesteric31.domain.Strength;
import be.chesteric31.repository.StrengthRepository;
import be.chesteric31.service.dto.StrengthDTO;
import be.chesteric31.service.mapper.StrengthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Strength}.
 */
@Service
@Transactional
public class StrengthService {

    private final Logger log = LoggerFactory.getLogger(StrengthService.class);

    private final StrengthRepository strengthRepository;

    private final StrengthMapper strengthMapper;

    public StrengthService(StrengthRepository strengthRepository, StrengthMapper strengthMapper) {
        this.strengthRepository = strengthRepository;
        this.strengthMapper = strengthMapper;
    }

    /**
     * Save a strength.
     *
     * @param strengthDTO the entity to save.
     * @return the persisted entity.
     */
    public StrengthDTO save(StrengthDTO strengthDTO) {
        log.debug("Request to save Strength : {}", strengthDTO);
        Strength strength = strengthMapper.toEntity(strengthDTO);
        strength = strengthRepository.save(strength);
        return strengthMapper.toDto(strength);
    }

    /**
     * Get all the strengths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StrengthDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Strengths");
        return strengthRepository.findAll(pageable)
            .map(strengthMapper::toDto);
    }


    /**
     * Get one strength by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StrengthDTO> findOne(Long id) {
        log.debug("Request to get Strength : {}", id);
        return strengthRepository.findById(id)
            .map(strengthMapper::toDto);
    }

    /**
     * Delete the strength by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Strength : {}", id);
        strengthRepository.deleteById(id);
    }
}
