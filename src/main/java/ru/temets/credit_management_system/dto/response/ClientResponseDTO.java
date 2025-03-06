package ru.temets.credit_management_system.dto.response;

import lombok.Data;
import ru.temets.credit_management_system.entity.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dateOfBirth;
    private Client.MaritalStatusType maritalStatus;
    private String phoneNumber;
    private String address;
    private String identityNumber;
    private String companyName;
    private String jobPosition;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal requestedAmount;
    private CreditResponseDTO creditApplication;
}
