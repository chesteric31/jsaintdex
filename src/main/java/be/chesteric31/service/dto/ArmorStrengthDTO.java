package be.chesteric31.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link be.chesteric31.domain.ArmorStrength} entity.
 */
public class ArmorStrengthDTO implements Serializable {
    
    private Long id;

    private Integer value;


    private Long strengthId;

    private Long armorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getStrengthId() {
        return strengthId;
    }

    public void setStrengthId(Long strengthId) {
        this.strengthId = strengthId;
    }

    public Long getArmorId() {
        return armorId;
    }

    public void setArmorId(Long armorId) {
        this.armorId = armorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmorStrengthDTO)) {
            return false;
        }

        return id != null && id.equals(((ArmorStrengthDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorStrengthDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", strengthId=" + getStrengthId() +
            ", armorId=" + getArmorId() +
            "}";
    }
}
