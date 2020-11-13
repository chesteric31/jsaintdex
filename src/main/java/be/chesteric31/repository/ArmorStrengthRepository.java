package be.chesteric31.repository;

import be.chesteric31.domain.ArmorStrength;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArmorStrength entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmorStrengthRepository extends JpaRepository<ArmorStrength, Long> {
}
