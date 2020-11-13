package be.chesteric31.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link be.chesteric31.domain.ArmorVersionAttack} entity.
 */
public class ArmorVersionAttackDTO implements Serializable {
    
    private Long id;

    private String name;


    private Long versionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long armorVersionId) {
        this.versionId = armorVersionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmorVersionAttackDTO)) {
            return false;
        }

        return id != null && id.equals(((ArmorVersionAttackDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorVersionAttackDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", versionId=" + getVersionId() +
            "}";
    }
}
