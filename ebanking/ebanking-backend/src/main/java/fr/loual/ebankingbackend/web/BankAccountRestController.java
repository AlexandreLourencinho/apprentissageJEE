package fr.loual.ebankingbackend.web;

import fr.loual.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-account")
@AllArgsConstructor
public class BankAccountRestController {

    private BankAccountService bankAccountService;

}
