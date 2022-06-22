package fr.loual.ebankingbackend.mappers;

import fr.loual.ebankingbackend.dtos.AccountOperationDTO;
import fr.loual.ebankingbackend.dtos.CurrentBankAccountDTO;
import fr.loual.ebankingbackend.dtos.CustomerDTO;
import fr.loual.ebankingbackend.dtos.SavingBankAccountDTO;
import fr.loual.ebankingbackend.entities.AccountOperation;
import fr.loual.ebankingbackend.entities.CurrentBankAccount;
import fr.loual.ebankingbackend.entities.Customer;
import fr.loual.ebankingbackend.entities.SavingBankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

// @TODO utilisation de MapStruct ou Jmapper plus tard: but de savoir les bases pour pouvoir palier aux eventuelles lacunes des framework et comprendre le fonctionnement

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO); // remplace la liste de setter et getter

        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingBankAccount savingBankAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingBankAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(this.fromCustomer(savingBankAccount.getCustomer()));
        savingBankAccountDTO.setType(savingBankAccount.getClass().getSimpleName());

        return savingBankAccountDTO;
    }

    public SavingBankAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        SavingBankAccount savingBankAccount = new SavingBankAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingBankAccount);
        savingBankAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));

        return savingBankAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentBankAccount currentBankAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentBankAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentBankAccount.getCustomer()));
        currentBankAccountDTO.setType(currentBankAccount.getClass().getSimpleName());

        return currentBankAccountDTO;
    }

    public CurrentBankAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentBankAccount currentBankAccount = new CurrentBankAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentBankAccount);
        currentBankAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));

        return currentBankAccount;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);

        return accountOperationDTO;
    }

    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);

        return accountOperation;
    }


}
