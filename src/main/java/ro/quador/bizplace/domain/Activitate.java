package ro.quador.bizplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activitate.
 */
@Entity
@Table(name = "T_ACTIVITATE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activitate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "nume")
    private String nume;

    @OneToMany(mappedBy = "activitate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Companie> companies = new HashSet<>();

    
    public Activitate() {
		super();
	}

	public Activitate(Long id) {
		super();
		this.id = id;
	}

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

    public Set<Companie> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Companie> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Activitate activitate = (Activitate) o;

        if (id != null ? !id.equals(activitate.id) : activitate.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Activitate{" +
                "id=" + id +
                ", nume='" + nume + "'" +
                '}';
    }
}
