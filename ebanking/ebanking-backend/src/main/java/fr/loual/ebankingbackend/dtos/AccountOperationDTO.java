package fr.loual.ebankingbackend.dtos;

import fr.loual.ebankingbackend.enums.OperationType;
import lombok.Data;

import java.util.Date;


@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;


}
