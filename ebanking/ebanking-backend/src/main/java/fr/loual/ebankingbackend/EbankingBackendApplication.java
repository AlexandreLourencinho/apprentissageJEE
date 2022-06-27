package fr.loual.ebankingbackend;

import fr.loual.ebankingbackend.dtos.BankAccountDTO;
import fr.loual.ebankingbackend.dtos.CurrentBankAccountDTO;
import fr.loual.ebankingbackend.dtos.CustomerDTO;
import fr.loual.ebankingbackend.dtos.SavingBankAccountDTO;
import fr.loual.ebankingbackend.entities.*;
import fr.loual.ebankingbackend.enums.AccountStatus;
import fr.loual.ebankingbackend.enums.OperationType;
import fr.loual.ebankingbackend.exceptions.CustomerNotFoundException;
import fr.loual.ebankingbackend.repositories.BankAccountOperationRepository;
import fr.loual.ebankingbackend.repositories.BankAccountRepository;
import fr.loual.ebankingbackend.repositories.CustomerRepository;
import fr.loual.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Alexandre", "Anne-Sophie", "Morgan").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 9000, customer.getId(), 800);
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, customer.getId(), 1.5);
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    } else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    try{
                        bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "credit");
                        bankAccountService.debit(accountId, 1000 + Math.random() * 120000, "débit");
                    }catch(Exception e){
                        System.out.println("e : " + e.toString());
                    }

                }
            }
        };
    }


    //    @Bean
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
                currentBankAccount.setBalance(Math.random() * 9000);
                currentBankAccount.setCreatedAt(new Date());
                currentBankAccount.setStatus(AccountStatus.CREATED);
                currentBankAccount.setCustomer(customer);
                currentBankAccount.setOverDraft(600);
                currentBankAccount.setCurrency("EUR");
                bankAccountRepository.save(currentBankAccount);

                SavingBankAccount savingBankAccount = new SavingBankAccount();
                savingBankAccount.setId(UUID.randomUUID().toString());
                savingBankAccount.setBalance(Math.random() * 9000);
                savingBankAccount.setCreatedAt(new Date());
                savingBankAccount.setStatus(AccountStatus.CREATED);
                savingBankAccount.setCustomer(customer);
                savingBankAccount.setInterestRate(1.5);
                savingBankAccount.setCurrency("EUR");
                bankAccountRepository.save(savingBankAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setBankAccount(acc);
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperationRepository.save(accountOperation);
                }
            });


            BankAccount bankAccount = bankAccountRepository.findById("05fe9f15-d360-4d73-a595-cd4bec57e497").orElse(null);
            System.out.println("bankAccount : " + bankAccount.toString());
            System.out.println(bankAccount.getClass().getSimpleName());
            List<AccountOperation> accountOperations = bankAccount.getAccountOperations();
            accountOperations.forEach(System.out::println);
        };
    }

}
