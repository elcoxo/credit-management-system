package ru.temets.credit_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.entity.CreditApplication;
import ru.temets.credit_management_system.entity.CreditContract;
import ru.temets.credit_management_system.service.ClientService;
import ru.temets.credit_management_system.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class CreditApplicationController {

    private final ClientService clientService;
    private final CreditService creditService;

    public CreditApplicationController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @GetMapping
    public ResponseEntity<List<CreditResponseDTO>> getAllClientApplications() {
        List<CreditResponseDTO> applications = creditService.getAllClientApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<CreditResponseDTO>> getAllClientApplicationsByStatus(@RequestParam(required = false) CreditApplication.StatusType status) {
        List<CreditResponseDTO> applications = creditService.getAllClientApplicationsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }

    @GetMapping(params = "sign")
    public ResponseEntity<List<CreditResponseDTO>> getAllClientApplicationsBySign(@RequestParam(required = false) CreditContract.SignatureStatus sign) {
        List<CreditResponseDTO> applications = creditService.getAllClientApplicationsBySign(sign);
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }
}
