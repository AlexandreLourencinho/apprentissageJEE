package fr.loual.ebankingbackend.services;

import fr.loual.ebankingbackend.dtos.*;
import fr.loual.ebankingbackend.entities.*;
import fr.loual.ebankingbackend.enums.OperationType;
import fr.loual.ebankingbackend.exceptions.BalanceNotSufficientException;
import fr.loual.ebankingbackend.exceptions.BankAccountNotFoundException;
import fr.loual.ebankingbackend.exceptions.CustomerNotFoundException;
import fr.loual.ebankingbackend.mappers.BankAccountMapperImpl;
import fr.loual.ebankingbackend.repositories.BankAccountOperationRepository;
import fr.loual.ebankingbackend.repositories.BankAccountRepository;
import fr.loual.ebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountOperationRepository operationRepository;
    private BankAccountMapperImpl mapper;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        log.info("Saving new Customer");
        return mapper.fromCustomer(customerRepository.save(mapper.fromCustomerDTO(customer)));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer) {
        log.info("Saving new Customer");
        return mapper.fromCustomer(customerRepository.save(mapper.fromCustomerDTO(customer)));
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }


    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overdraft) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("customer not found");
        }
        CurrentBankAccount currentBankAccount = new CurrentBankAccount();
        currentBankAccount.setId(UUID.randomUUID().toString()); // évidemment, si l'id est par exemple le RIB, d'autres règles s'appliquent qu'un simple UUID
        currentBankAccount.setCreatedAt(new Date());
        currentBankAccount.setBalance(initialBalance);
        currentBankAccount.setCustomer(customer);
        currentBankAccount.setOverDraft(overdraft);
        log.info("saving new Current bank account");
        return mapper.fromCurrentBankAccount(bankAccountRepository.save(currentBankAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("customer not found");
        }
        SavingBankAccount savingBankAccount = new SavingBankAccount();
        savingBankAccount.setId(UUID.randomUUID().toString()); // évidemment, si l'id est par exemple le RIB, d'autres règles s'appliquent qu'un simple UUID
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setBalance(initialBalance);
        savingBankAccount.setCustomer(customer);
        savingBankAccount.setInterestRate(interestRate);
        log.info("saving new Saving bank account");
        return mapper.fromSavingBankAccount(bankAccountRepository.save(savingBankAccount));
    }

    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> mapper.fromCustomer(customer)).collect(Collectors.toList()); // ne pas oublier le collect !!
    }



    @Override
    public BankAccountDTO getBankAccountDTO(String accountId) throws BankAccountNotFoundException {
        log.info("retrieving account");
        BankAccount retrievedBankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("account not found"));
        if (retrievedBankAccount instanceof SavingBankAccount) {
            SavingBankAccount savingBankAccount = (SavingBankAccount) retrievedBankAccount;
            return mapper.fromSavingBankAccount(savingBankAccount);
        } else {
            CurrentBankAccount currentBankAccount = (CurrentBankAccount) retrievedBankAccount;
            return mapper.fromCurrentBankAccount(currentBankAccount);
        }
    }

    @Override
    public AccountOperation operate(BankAccount bankAccount, OperationType operationType, double amount, String description) {
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(operationType);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        log.info("Registering account operation for " + bankAccount.getId());
        return accountOperation;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = this.getBankAccount(accountId);
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        if (bankAccount instanceof CurrentBankAccount && bankAccount.getBalance() + ((CurrentBankAccount) bankAccount).getOverDraft() < amount) {
            throw new BalanceNotSufficientException("Balance and overdraft not sufficient");
        }
        AccountOperation accountOperation = this.operate(bankAccount, OperationType.DEBIT, amount, description);
        log.info("saving debit operation for " + bankAccount.getId());
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        log.info("debiting account " + bankAccount.getId());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = this.getBankAccount(accountId);
        AccountOperation accountOperation = this.operate(bankAccount,OperationType.CREDIT, amount, description);
        log.info("saving credit operation for " + bankAccount.getId());
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        log.info("crediting account " + bankAccount.getId());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String sourceAccountId, String destinationAccountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("debiting account " + sourceAccountId);
        this.debit(sourceAccountId, amount, "transfert vers" + destinationAccountId);
        log.info("crediting account " + destinationAccountId);
        this.credit(destinationAccountId, amount, "transfert depuis" + sourceAccountId);

    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        return mapper.fromCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"))
        );
    }


    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();

        return bankAccountList.stream().map(account -> {
           if (account instanceof SavingBankAccount) {
               SavingBankAccount savingBankAccount = (SavingBankAccount) account;
               return mapper.fromSavingBankAccount(savingBankAccount);
           } else {
               CurrentBankAccount currentBankAccount = (CurrentBankAccount) account;
               return mapper.fromCurrentBankAccount(currentBankAccount);
           }
        }).collect(Collectors.toList());
    }


    private BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {

        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("account not found"));
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) {
        List<AccountOperation> accountOperations = operationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op -> mapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("account not found"));
        Page<AccountOperation> accountOperations = operationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> mapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }
}
