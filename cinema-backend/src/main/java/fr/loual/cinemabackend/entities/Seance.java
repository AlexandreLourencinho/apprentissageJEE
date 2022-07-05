package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@ToString
public class Seance extends MovieProjection {

    private Date beginDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Seance seance = (Seance) o;
        return getId() != null && Objects.equals(getId(), seance.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
