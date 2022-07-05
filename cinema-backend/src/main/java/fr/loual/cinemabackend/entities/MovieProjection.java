package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class MovieProjection {

    @Id
    private String id;
    private Date projectionDate;
    private double price;
    private Date beginDate;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    @OneToMany(mappedBy = "seance")
    @ToString.Exclude
    private Collection<Ticket> ticketsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieProjection that = (MovieProjection) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
