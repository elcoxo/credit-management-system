package ru.temets.credit_management_system.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.temets.credit_management_system.dto.request.ClientRequestDTO;
import ru.temets.credit_management_system.mapper.ClientMapper;
import ru.temets.credit_management_system.repository.ClientDAO;
import ru.temets.credit_management_system.repository.CreditApplicationDAO;
import ru.temets.credit_management_system.dto.response.ClientResponseDTO;
import ru.temets.credit_management_system.entity.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientService {
    private final ClientDAO clientDao;
    private final CreditApplicationDAO creditApplicationDao;
    private final CreditService creditService;
    private final ClientMapper clientMapper;

    public ClientService(ClientDAO clientDao, CreditApplicationDAO creditApplicationDao, CreditService creditService) {
        this.clientDao = clientDao;
        this.creditApplicationDao = creditApplicationDao;
        this.creditService = creditService;
        this.clientMapper = new ClientMapper();
    }

    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO request)
    {
        Client client = clientMapper.toEntity(request);
        clientDao.save(client);
        creditService.approveApplication(client.getCreditApplication());
        return clientMapper.toDTO(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        clientDao.deleteById(id);
    }

    @Transactional
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientDao.findById(id);
        if (client == null) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        return clientMapper.toDTO(client);
    }

    @Transactional
    public List<ClientResponseDTO> getAllClients() {
        return clientDao.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ClientResponseDTO> searchClients(String firstName, String lastName, String middleName,
                                      String phoneNumber, String identityNumber) {
        return clientDao.search(firstName, lastName, middleName, phoneNumber, identityNumber).stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }


}
