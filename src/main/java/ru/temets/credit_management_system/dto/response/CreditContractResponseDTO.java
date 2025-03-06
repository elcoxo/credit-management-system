package ru.temets.credit_management_system.dto.response;

import lombok.Data;
import ru.temets.credit_management_system.entity.CreditContract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreditContractResponseDTO {
    private Long id;
    private int approvedDays;
    private BigDecimal approvedAmount;
    private LocalDateTime approvedDate;
    private LocalDateTime signatureCreated;
    private CreditContract.SignatureStatus signatureStatus;
}
