package be.chesteric31.repository;

import be.chesteric31.domain.ArmorCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArmorCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArmorCategoryRepository extends JpaRepository<ArmorCategory, Long> {
}
