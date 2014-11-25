package ro.quador.bizplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tipabonament.
 */
@Entity
@Table(name = "T_TIPABONAMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tipabonament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "anunturi")
    private Integer anunturi;

    @Column(name = "companii")
    private Integer companii;

    @Column(name = "valabilitate")
    private Integer valabilitate;

    @Column(name = "epublic")
    private Boolean epublic;

    @OneToMany(mappedBy = "tipabonament")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Abonament> abonaments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getAnunturi() {
        return anunturi;
    }

    public void setAnunturi(Integer anunturi) {
        this.anunturi = anunturi;
    }

    public Integer getCompanii() {
        return companii;
    }

    public void setCompanii(Integer companii) {
        this.companii = companii;
    }

    public Integer getValabilitate() {
        return valabilitate;
    }

    public void setValabilitate(Integer valabilitate) {
        this.valabilitate = valabilitate;
    }

    public Boolean getEpublic() {
        return epublic;
    }

    public void setEpublic(Boolean epublic) {
        this.epublic = epublic;
    }

    public Set<Abonament> getAbonaments() {
        return abonaments;
    }

    public void setAbonaments(Set<Abonament> abonaments) {
        this.abonaments = abonaments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tipabonament tipabonament = (Tipabonament) o;

        if (id != null ? !id.equals(tipabonament.id) : tipabonament.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Tipabonament{" +
                "id=" + id +
                ", nume='" + nume + "'" +
                ", anunturi='" + anunturi + "'" +
                ", companii='" + companii + "'" +
                ", valabilitate='" + valabilitate + "'" +
                ", epublic='" + epublic + "'" +
                '}';
    }
}
