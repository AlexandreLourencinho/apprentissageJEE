package fr.loual.ebankingbackend.web;

import fr.loual.ebankingbackend.dtos.CustomerDTO;
import fr.loual.ebankingbackend.exceptions.CustomerNotFoundException;
import fr.loual.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alexandre
 */
@CrossOrigin("http://localhost:4200") // * autorise les requetes venant de toutes les origines
@RestController
@RequestMapping("/customers")
@Slf4j
@AllArgsConstructor
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomer();
    }

    /**
     *
     * @param keyword chaine de caractères contenant la recherche voulue
     * @return liste de DTO de clients
     */
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return this.bankAccountService.getCustomer(customerId);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customer) {
        return bankAccountService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        customer.setId(id);
        return bankAccountService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }

}
