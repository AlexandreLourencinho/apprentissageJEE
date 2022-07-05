package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@ToString
public class Place {

    @Id
    private String id;
    private int number;
    private double longitude;
    private double latitude;
    private double altitude;
    @ManyToOne
    private Room room;
    @OneToOne(mappedBy = "place")
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Place place = (Place) o;
        return id != null && Objects.equals(id, place.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
