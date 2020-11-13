package be.chesteric31.repository;

import be.chesteric31.domain.Strength;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Strength entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrengthRepository extends JpaRepository<Strength, Long> {
}
