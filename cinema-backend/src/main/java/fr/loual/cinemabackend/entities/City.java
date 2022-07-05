package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@ToString
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    @OneToMany(mappedBy = "city")
    @ToString.Exclude
    private Collection<Cinema> cinemas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        City city = (City) o;
        return id != null && Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
