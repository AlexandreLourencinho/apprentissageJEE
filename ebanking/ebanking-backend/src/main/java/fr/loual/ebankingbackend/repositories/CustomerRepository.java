package fr.loual.ebankingbackend.repositories;

import fr.loual.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByName(String name);
    List<Customer> findByNameContains(String keyword);

}
