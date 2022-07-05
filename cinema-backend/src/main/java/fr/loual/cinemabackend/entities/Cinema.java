package fr.loual.cinemabackend.entities;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Cinema {

    @Id
    private String id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    private int numberOfRooms;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "cinema")
    @ToString.Exclude
    private Collection<Room> rooms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cinema cinema = (Cinema) o;
        return id != null && Objects.equals(id, cinema.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
