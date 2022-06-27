package fr.loual.ebankingbackend.services;

import fr.loual.ebankingbackend.dtos.*;
import fr.loual.ebankingbackend.entities.*;
import fr.loual.ebankingbackend.enums.OperationType;
import fr.loual.ebankingbackend.exceptions.BalanceNotSufficientException;
import fr.loual.ebankingbackend.exceptions.BankAccountNotFoundException;
import fr.loual.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customer);

    void deleteCustomer(Long customerId);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overdraft) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomer();
    AccountOperation operate(BankAccount bankAccount, OperationType operationType, double amount, String description);
    BankAccountDTO getBankAccountDTO(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfert(String sourceAccountId, String destinationAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    List<BankAccountDTO> bankAccountList();
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
    List<CustomerDTO> searchCustomers(String keyword);
}
