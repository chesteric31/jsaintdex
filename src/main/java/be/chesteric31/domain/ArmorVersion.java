package be.chesteric31.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArmorVersion.
 */
@Entity
@Table(name = "armor_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArmorVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "version")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ArmorVersionAttack> attacks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "versions", allowSetters = true)
    private Armor armor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArmorVersion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public ArmorVersion image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public ArmorVersion imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<ArmorVersionAttack> getAttacks() {
        return attacks;
    }

    public ArmorVersion attacks(Set<ArmorVersionAttack> armorVersionAttacks) {
        this.attacks = armorVersionAttacks;
        return this;
    }

    public ArmorVersion addAttacks(ArmorVersionAttack armorVersionAttack) {
        this.attacks.add(armorVersionAttack);
        armorVersionAttack.setVersion(this);
        return this;
    }

    public ArmorVersion removeAttacks(ArmorVersionAttack armorVersionAttack) {
        this.attacks.remove(armorVersionAttack);
        armorVersionAttack.setVersion(null);
        return this;
    }

    public void setAttacks(Set<ArmorVersionAttack> armorVersionAttacks) {
        this.attacks = armorVersionAttacks;
    }

    public Armor getArmor() {
        return armor;
    }

    public ArmorVersion armor(Armor armor) {
        this.armor = armor;
        return this;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmorVersion)) {
            return false;
        }
        return id != null && id.equals(((ArmorVersion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorVersion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
