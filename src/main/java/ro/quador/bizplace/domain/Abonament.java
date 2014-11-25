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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Abonament.
 */
@Entity
@Table(name = "T_ABONAMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Abonament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "anunturi")
    private Integer anunturi;

    @Column(name = "companii")
    private Integer companii;

    @Column(name = "valabilitate")
    private Integer valabilitate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "creatla", nullable = false)
    private LocalDate creatla;

    @ManyToOne
    private Companie companie;

    @ManyToOne
    private Tipabonament tipabonament;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatla() {
        return creatla;
    }

    public void setCreatla(LocalDate creatla) {
        this.creatla = creatla;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public Tipabonament getTipabonament() {
        return tipabonament;
    }

    public void setTipabonament(Tipabonament tipabonament) {
        this.tipabonament = tipabonament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Abonament abonament = (Abonament) o;

        if (id != null ? !id.equals(abonament.id) : abonament.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Abonament{" +
                "id=" + id +
                ", anunturi='" + anunturi + "'" +
                ", companii='" + companii + "'" +
                ", valabilitate='" + valabilitate + "'" +
                ", creatla='" + creatla + "'" +
                '}';
    }
}
