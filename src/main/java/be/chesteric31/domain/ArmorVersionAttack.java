package be.chesteric31.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ArmorVersionAttack.
 */
@Entity
@Table(name = "armor_version_attack")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArmorVersionAttack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "attacks", allowSetters = true)
    private ArmorVersion version;

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

    public ArmorVersionAttack name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArmorVersion getVersion() {
        return version;
    }

    public ArmorVersionAttack version(ArmorVersion armorVersion) {
        this.version = armorVersion;
        return this;
    }

    public void setVersion(ArmorVersion armorVersion) {
        this.version = armorVersion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmorVersionAttack)) {
            return false;
        }
        return id != null && id.equals(((ArmorVersionAttack) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArmorVersionAttack{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
