package fr.loual.ebankingbackend;

import fr.loual.ebankingbackend.entities.AccountOperation;
import fr.loual.ebankingbackend.entities.CurrentBankAccount;
import fr.loual.ebankingbackend.entities.Customer;
import fr.loual.ebankingbackend.entities.SavingBankAccount;
import fr.loual.ebankingbackend.enums.AccountStatus;
import fr.loual.ebankingbackend.enums.OperationType;
import fr.loual.ebankingbackend.repositories.BankAccountOperationRepository;
import fr.loual.ebankingbackend.repositories.BankAccountRepository;
import fr.loual.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountOperationRepository accountOperationRepository,
                            BankAccountRepository bankAccountRepository) {
        return args -> {
            Stream.of("Alexandre", "Anne-Sophie", "Morgan").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentBankAccount currentBankAccount = new CurrentBankAccount();
                currentBankAccount.setId(UUID.randomUUID().toString());
                currentBankAccount.setBalance(Math.random()*9000);
                currentBankAccount.setCreatedAt(new Date());
                currentBankAccount.setStatus(AccountStatus.CREATED);
                currentBankAccount.setCustomer(customer);
                currentBankAccount.setOverDraft(600);
                currentBankAccount.setCurrency("EUR");
                bankAccountRepository.save(currentBankAccount);

                SavingBankAccount savingBankAccount = new SavingBankAccount();
                savingBankAccount.setId(UUID.randomUUID().toString());
                savingBankAccount.setBalance(Math.random()*9000);
                savingBankAccount.setCreatedAt(new Date());
                savingBankAccount.setStatus(AccountStatus.CREATED);
                savingBankAccount.setCustomer(customer);
                savingBankAccount.setInterestRate(1.5);
                savingBankAccount.setCurrency("EUR");
                bankAccountRepository.save(savingBankAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i =0; i<10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setBankAccount(acc);
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

} // @TODO reprendre à partir de la partie passage en SQL
