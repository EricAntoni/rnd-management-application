package fr.eai.application.rnd.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sujet> sujets = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Projet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sujet> getSujets() {
        return sujets;
    }

    public Projet sujets(Set<Sujet> sujets) {
        this.sujets = sujets;
        return this;
    }

    public Projet addSujet(Sujet sujet) {
        this.sujets.add(sujet);
        sujet.setProjet(this);
        return this;
    }

    public Projet removeSujet(Sujet sujet) {
        this.sujets.remove(sujet);
        sujet.setProjet(null);
        return this;
    }

    public void setSujets(Set<Sujet> sujets) {
        this.sujets = sujets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projet projet = (Projet) o;
        if (projet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
