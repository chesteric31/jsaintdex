package be.chesteric31.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Armor.
 */
@Entity
@Table(name = "armor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Armor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "armor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ArmorVersion> versions = new HashSet<>();

    @OneToMany(mappedBy = "armor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ArmorStrength> strengths = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "armors", allowSetters = true)
    private ArmorCategory category;

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

    public Armor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ArmorVersion> getVersions() {
        return versions;
    }

    public Armor versions(Set<ArmorVersion> armorVersions) {
        this.versions = armorVersions;
        return this;
    }

    public Armor addVersions(ArmorVersion armorVersion) {
        this.versions.add(armorVersion);
        armorVersion.setArmor(this);
        return this;
    }

    public Armor removeVersions(ArmorVersion armorVersion) {
        this.versions.remove(armorVersion);
        armorVersion.setArmor(null);
        return this;
    }

    public void setVersions(Set<ArmorVersion> armorVersions) {
        this.versions = armorVersions;
    }

    public Set<ArmorStrength> getStrengths() {
        return strengths;
    }

    public Armor strengths(Set<ArmorStrength> armorStrengths) {
        this.strengths = armorStrengths;
        return this;
    }

    public Armor addStrengths(ArmorStrength armorStrength) {
        this.strengths.add(armorStrength);
        armorStrength.setArmor(this);
        return this;
    }

    public Armor removeStrengths(ArmorStrength armorStrength) {
        this.strengths.remove(armorStrength);
        armorStrength.setArmor(null);
        return this;
    }

    public void setStrengths(Set<ArmorStrength> armorStrengths) {
        this.strengths = armorStrengths;
    }

    public ArmorCategory getCategory() {
        return category;
    }

    public Armor category(ArmorCategory armorCategory) {
        this.category = armorCategory;
        return this;
    }

    public void setCategory(ArmorCategory armorCategory) {
        this.category = armorCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Armor)) {
            return false;
        }
        return id != null && id.equals(((Armor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Armor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
