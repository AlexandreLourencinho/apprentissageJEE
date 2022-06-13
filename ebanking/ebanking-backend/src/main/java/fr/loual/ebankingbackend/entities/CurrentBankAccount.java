package fr.loual.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("CA") // pour singleTable
@Data @NoArgsConstructor @AllArgsConstructor
public class CurrentBankAccount extends BankAccount {

    private double overDraft;

}
