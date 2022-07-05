package fr.loual.cinemabackend.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {

    @Id
    private String id;
    private String clientName;
    private double price;
    private int paymentCode;
    private boolean logical;
    @ManyToOne
    private Seance seance;
    @OneToOne
    private Place place;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ticket ticket = (Ticket) o;
        return id != null && Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
