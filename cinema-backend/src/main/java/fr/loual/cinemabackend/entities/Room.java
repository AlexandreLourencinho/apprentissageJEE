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
public class Room {

    @Id
    private String id;
    private String name;
    private int roomSize;
    @ManyToOne
    private Cinema cinema;
    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private Collection<Seance> seance;
    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private Collection<Place> places;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
