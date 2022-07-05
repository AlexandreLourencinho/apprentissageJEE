package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Movie {
    @Id
    private String id;
    private String title;
    private int duration;
    private String maker;
    private String description;
    private String picture;
    private Date releaseDate;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "movie")
    @ToString.Exclude
    private Collection<Seance> seances;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movie movie = (Movie) o;
        return id != null && Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
