package ru.temets.credit_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.temets.credit_management_system.dto.request.ClientRequestDTO;
import ru.temets.credit_management_system.dto.response.ClientResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditContractResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.entity.Client;
import ru.temets.credit_management_system.entity.CreditApplication;
import ru.temets.credit_management_system.service.ClientService;
import ru.temets.credit_management_system.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClientController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostMapping("/add")
    public ResponseEntity<ClientResponseDTO> createClientWithApplication(@RequestBody ClientRequestDTO request) {
        ClientResponseDTO response = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity <List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> response = clientService.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        ClientResponseDTO response = clientService.getClientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientResponseDTO>> searchClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String identityNumber) {
        List<ClientResponseDTO> clients = clientService.searchClients(firstName, lastName, middleName, phoneNumber, identityNumber);
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<CreditContractResponseDTO> signClientApplication(@PathVariable Long id) {
        CreditContractResponseDTO contract = creditService.signApplication(id);
        return ResponseEntity.ok(contract);
    }
}
