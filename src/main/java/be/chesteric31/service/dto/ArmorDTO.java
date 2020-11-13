package be.chesteric31.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link be.chesteric31.domain.Armor} entity.
 */
public class ArmorDTO implements Serializable {
    
    private Long id;

    private String name;


    private Long categoryId;
    
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long armorCategoryId) {
        this.categoryId = armorCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmorDTO)) {
            return false;
        }

        return id != null && id.equals(((ArmorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
