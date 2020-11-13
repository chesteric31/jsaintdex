package be.chesteric31.repository;

import be.chesteric31.domain.ArmorVersionAttack;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArmorVersionAttack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmorVersionAttackRepository extends JpaRepository<ArmorVersionAttack, Long> {
}
