package fr.eai.application.rnd.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fr.eai.application.rnd.management.domain.enumeration.ContextOrigineEnum;

/**
 * A Sujet.
 */
@Entity
@Table(name = "sujet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sujet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "detail")
    private String detail;

    @Column(name = "suivi")
    private Boolean suivi;

    @Column(name = "id_evolution")
    private Integer idEvolution;

    @Enumerated(EnumType.STRING)
    @Column(name = "origine")
    private ContextOrigineEnum origine;

    @ManyToOne
    @JsonIgnoreProperties("sujets")
    private Projet projet;

    @ManyToOne
    @JsonIgnoreProperties("sujets")
    private Theme theme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Sujet libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDetail() {
        return detail;
    }

    public Sujet detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean isSuivi() {
        return suivi;
    }

    public Sujet suivi(Boolean suivi) {
        this.suivi = suivi;
        return this;
    }

    public void setSuivi(Boolean suivi) {
        this.suivi = suivi;
    }

    public Integer getIdEvolution() {
        return idEvolution;
    }

    public Sujet idEvolution(Integer idEvolution) {
        this.idEvolution = idEvolution;
        return this;
    }

    public void setIdEvolution(Integer idEvolution) {
        this.idEvolution = idEvolution;
    }

    public ContextOrigineEnum getOrigine() {
        return origine;
    }

    public Sujet origine(ContextOrigineEnum origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(ContextOrigineEnum origine) {
        this.origine = origine;
    }

    public Projet getProjet() {
        return projet;
    }

    public Sujet projet(Projet projet) {
        this.projet = projet;
        return this;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Theme getTheme() {
        return theme;
    }

    public Sujet theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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
        Sujet sujet = (Sujet) o;
        if (sujet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sujet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sujet{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", detail='" + getDetail() + "'" +
            ", suivi='" + isSuivi() + "'" +
            ", idEvolution=" + getIdEvolution() +
            ", origine='" + getOrigine() + "'" +
            "}";
    }
}
