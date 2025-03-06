package ru.temets.credit_management_system.mapper;

import org.springframework.stereotype.Component;
import ru.temets.credit_management_system.dto.response.CreditContractResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.entity.*;

@Component
public class CreditMapper {
    public CreditResponseDTO toDTO(CreditApplication application) {
        CreditResponseDTO response = new CreditResponseDTO();
        response.setId(application.getId());
        response.setRequestedAmount(application.getRequestedAmount());
        response.setStatus(application.getStatus());

        if (application.getContract() != null) {
            CreditContractResponseDTO contractResponse = new CreditContractResponseDTO();
            contractResponse.setId(application.getContract().getId());
            contractResponse.setApprovedDays(application.getContract().getApprovedDays());
            contractResponse.setApprovedAmount(application.getContract().getApprovedAmount());
            contractResponse.setApprovedDate(application.getContract().getApprovedDate());
            contractResponse.setSignatureCreated(application.getContract().getSignatureCreated());
            contractResponse.setSignatureStatus(application.getContract().getSignatureStatus());
            response.setCreditContract(contractResponse);
        }

        return response;
    }

    public CreditContractResponseDTO toContractDTO(CreditContract contract) {
        CreditContractResponseDTO response = new CreditContractResponseDTO();
        response.setId(contract.getId());
        response.setApprovedDays(contract.getApprovedDays());
        response.setApprovedAmount(contract.getApprovedAmount());
        response.setApprovedDate(contract.getApprovedDate());
        response.setSignatureCreated(contract.getSignatureCreated());
        response.setSignatureStatus(contract.getSignatureStatus());
        return response;
    }
}
