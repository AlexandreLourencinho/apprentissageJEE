package fr.loual.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SA") // pour singleTable
@Data @AllArgsConstructor @NoArgsConstructor
public class SavingBankAccount extends BankAccount {

    private double interestRate;

}
