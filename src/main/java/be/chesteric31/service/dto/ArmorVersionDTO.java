package be.chesteric31.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link be.chesteric31.domain.ArmorVersion} entity.
 */
public class ArmorVersionDTO implements Serializable {
    
    private Long id;

    private String name;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long armorId;
    
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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
        if (!(o instanceof ArmorVersionDTO)) {
            return false;
        }

        return id != null && id.equals(((ArmorVersionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorVersionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", armorId=" + getArmorId() +
            "}";
    }
}
