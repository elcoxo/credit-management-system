package ru.temets.credit_management_system.controller.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.entity.CreditApplication;
import ru.temets.credit_management_system.entity.CreditContract;
import ru.temets.credit_management_system.service.ClientService;
import ru.temets.credit_management_system.service.CreditService;

import java.util.List;

@Controller
public class CreditApplicationControllerMVC {

    private final ClientService clientService;
    private final CreditService creditService;

    public CreditApplicationControllerMVC(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @GetMapping("/applications")
    public String getAllClientApplications(
            @RequestParam(required = false) CreditApplication.StatusType applicationStatus,
            @RequestParam(required = false) CreditContract.SignatureStatus signatureStatus,
            Model model) {
        List<CreditResponseDTO> applications;
        if (applicationStatus != null) {
            applications = creditService.getAllClientApplicationsByStatus(applicationStatus);
        } else if (signatureStatus != null) {
            applications = creditService.getAllClientApplicationsBySign(signatureStatus);
        }
        else {
            applications = creditService.getAllClientApplications();
        }
        model.addAttribute("applications", applications);
        return "applications";
    }

}
