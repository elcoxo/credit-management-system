package ru.temets.credit_management_system.mapper;

import org.springframework.stereotype.Component;
import ru.temets.credit_management_system.dto.request.ClientRequestDTO;
import ru.temets.credit_management_system.dto.response.ClientResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditContractResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.entity.*;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequestDTO request) {
        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setMiddleName(request.getMiddleName());
        client.setDateOfBirth(request.getDateOfBirth());
        client.setMaritalStatus(request.getMaritalStatus());
        client.setPhoneNumber(request.getPhoneNumber());

        ClientIdentity identity = new ClientIdentity();
        identity.setAddress(request.getAddress());
        identity.setIdentityNumber(request.getIdentityNumber());
        identity.setClient(client);
        client.setIdentity(identity);

        ClientEmployment employment = new ClientEmployment();
        employment.setCompanyName(request.getCompanyName());
        employment.setJobPosition(request.getJobPosition());
        employment.setStartDate(request.getStartDate());
        employment.setEndDate(request.getEndDate());
        employment.setClient(client);
        client.setEmployment(employment);

        CreditApplication application = new CreditApplication();
        application.setClient(client);
        application.setRequestedAmount(request.getRequestedAmount());
        application.setStatus(CreditApplication.StatusType.INPROGRESS);
        client.setCreditApplication(application);

        return client;
    }

    public ClientResponseDTO toDTO(Client client) {
        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(client.getId());
        response.setFirstName(client.getFirstName());
        response.setLastName(client.getLastName());
        response.setMiddleName(client.getMiddleName());
        response.setDateOfBirth(client.getDateOfBirth());
        response.setMaritalStatus(client.getMaritalStatus());
        response.setPhoneNumber(client.getPhoneNumber());

        if (client.getIdentity() != null) {
            response.setAddress(client.getIdentity().getAddress());
            response.setIdentityNumber(client.getIdentity().getIdentityNumber());
        }

        if (client.getEmployment() != null) {
            response.setCompanyName(client.getEmployment().getCompanyName());
            response.setJobPosition(client.getEmployment().getJobPosition());
            response.setStartDate(client.getEmployment().getStartDate());
            response.setEndDate(client.getEmployment().getEndDate());
        }

        if (client.getCreditApplication() != null) {
            CreditResponseDTO appResponse = new CreditResponseDTO();
            appResponse.setId(client.getCreditApplication().getId());
            appResponse.setRequestedAmount(client.getCreditApplication().getRequestedAmount());
            appResponse.setStatus(client.getCreditApplication().getStatus());
            response.setCreditApplication(appResponse);

            CreditContract contract = client.getCreditApplication().getContract();
            if (contract != null) {
                CreditContractResponseDTO contractResponse = new CreditContractResponseDTO();
                contractResponse.setId(contract.getId());
                contractResponse.setApprovedDays(contract.getApprovedDays());
                contractResponse.setApprovedAmount(contract.getApprovedAmount());
                contractResponse.setApprovedDate(contract.getApprovedDate());
                contractResponse.setSignatureCreated(contract.getSignatureCreated());
                contractResponse.setSignatureStatus(contract.getSignatureStatus());
                appResponse.setCreditContract(contractResponse);
            }
        }

        return response;
    }
}
