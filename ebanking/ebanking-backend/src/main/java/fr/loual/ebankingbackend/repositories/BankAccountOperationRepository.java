package fr.loual.ebankingbackend.repositories;

import fr.loual.ebankingbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
