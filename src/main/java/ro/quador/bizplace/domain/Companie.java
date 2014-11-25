package ro.quador.bizplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

import ro.quador.bizplace.domain.util.CustomLocalDateSerializer;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Companie.
 */
@Entity
@Table(name = "T_COMPANIE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Companie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "cui")
    private String cui;

    @Column(name = "nume")
    private String nume;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "creatala", nullable = false)
    private LocalDate creatala;

    @ManyToOne
    private Judet judet;

    @ManyToOne
    private Activitate activitate;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="login") 
    @NotNull
    private User user;

    @OneToMany(mappedBy = "companie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Abonament> abonaments = new HashSet<>();
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDate getCreatala() {
        return creatala;
    }

    public void setCreatala(LocalDate creatala) {
        this.creatala = creatala;
    }

    public Judet getJudet() {
        return judet;
    }

    public void setJudet(Judet judet) {
        this.judet = judet;
    }

    public Activitate getActivitate() {
        return activitate;
    }

    public void setActivitate(Activitate activitate) {
        this.activitate = activitate;
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

        Companie companie = (Companie) o;

        if (id != null ? !id.equals(companie.id) : companie.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Companie{" +
                "id=" + id +
                ", cui='" + cui + "'" +
                ", nume='" + nume + "'" +
                ", adresa='" + adresa + "'" +
                ", telefon='" + telefon + "'" +
                ", email='" + email + "'" +
                ", website='" + website + "'" +
                ", creatala='" + creatala + "'" +
                '}';
    }
}
