package be.chesteric31.repository;

import be.chesteric31.domain.ArmorVersion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArmorVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmorVersionRepository extends JpaRepository<ArmorVersion, Long> {
}
