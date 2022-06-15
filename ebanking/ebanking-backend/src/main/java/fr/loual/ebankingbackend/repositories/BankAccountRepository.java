package fr.loual.ebankingbackend.repositories;

import fr.loual.ebankingbackend.entities.BankAccount;
import fr.loual.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    BankAccount findByCustomer(Customer customer);

}
