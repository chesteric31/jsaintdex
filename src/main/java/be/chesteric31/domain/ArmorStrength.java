package be.chesteric31.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ArmorStrength.
 */
@Entity
@Table(name = "armor_strength")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArmorStrength implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JsonIgnoreProperties(value = "armorStrengths", allowSetters = true)
    private Strength strength;

    @ManyToOne
    @JsonIgnoreProperties(value = "strengths", allowSetters = true)
    private Armor armor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public ArmorStrength value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Strength getStrength() {
        return strength;
    }

    public ArmorStrength strength(Strength strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    public Armor getArmor() {
        return armor;
    }

    public ArmorStrength armor(Armor armor) {
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
        if (!(o instanceof ArmorStrength)) {
            return false;
        }
        return id != null && id.equals(((ArmorStrength) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorStrength{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
