package ru.temets.credit_management_system.dto.response;

import lombok.Data;
import ru.temets.credit_management_system.entity.CreditApplication;

import java.math.BigDecimal;

@Data
public class CreditResponseDTO {
    private Long id;
    private BigDecimal requestedAmount;
    private CreditApplication.StatusType status;
    private CreditContractResponseDTO creditContract;
}
