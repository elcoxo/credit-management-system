package ru.temets.credit_management_system.controller.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.temets.credit_management_system.dto.request.ClientRequestDTO;
import ru.temets.credit_management_system.dto.response.ClientResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditContractResponseDTO;
import ru.temets.credit_management_system.service.ClientService;
import ru.temets.credit_management_system.service.CreditService;

import java.util.List;

@Controller
public class ClientControllerMVC {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClientControllerMVC(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/clients/{id}")
    public String getClientById(@PathVariable Long id, Model model) {
        try {
            ClientResponseDTO client = clientService.getClientById(id);
            model.addAttribute("client", client);
            return "single-client";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/create-form")
    public String createClientForm(Model model) {
        model.addAttribute("request", new ClientRequestDTO());
        return "create-client";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createClientWithApplication(@ModelAttribute("request") ClientRequestDTO  request, Model model) {
        ClientResponseDTO client = clientService.createClient(request);
        model.addAttribute("request", client);
        return "redirect:/clients";
    }

    @GetMapping("/search")
    public String createClientWithApplication(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String passportNumber,
            Model model) {
        List<ClientResponseDTO> clients = clientService.searchClients(firstName, lastName, middleName, phoneNumber, passportNumber);
        model.addAttribute("clients", clients);
        return "search-clients";
    }

    @PostMapping("/clients/{id}/sign")
    public String signClientApplication(@PathVariable Long id, Model model) {
        CreditContractResponseDTO contract = creditService.signApplication(id);
        return "redirect:/clients/" + id;
    }

}
