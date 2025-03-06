package ru.temets.credit_management_system.service;

import org.springframework.stereotype.Service;
import ru.temets.credit_management_system.dto.response.CreditContractResponseDTO;
import ru.temets.credit_management_system.dto.response.CreditResponseDTO;
import ru.temets.credit_management_system.mapper.CreditMapper;
import ru.temets.credit_management_system.repository.ClientDAO;
import ru.temets.credit_management_system.repository.CreditApplicationDAO;
import ru.temets.credit_management_system.repository.CreditContractDAO;
import ru.temets.credit_management_system.entity.CreditApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.temets.credit_management_system.entity.CreditContract;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CreditService {
    private final CreditApplicationDAO creditApplicationDao;
    private final CreditContractDAO creditContractDao;
    private final ClientDAO clientDao;
    private final CreditMapper creditMapper;


    public CreditService(CreditApplicationDAO creditApplicationDao, ClientDAO clientDao, CreditContractDAO creditContractDao, CreditMapper creditMapper) {
        this.creditApplicationDao = creditApplicationDao;
        this.creditContractDao = creditContractDao;
        this.clientDao = clientDao;
        this.creditMapper = creditMapper;
    }

    @Transactional
    public void approveApplication(CreditApplication application) {
        Random random = new Random();
        boolean isApproved = random.nextBoolean();
        application.setStatus(isApproved ? CreditApplication.StatusType.APPROVED : CreditApplication.StatusType.REJECTED);
        if (isApproved) {
            CreditContract contract = new CreditContract();
            contract.setApprovedDays(random.nextInt(365 - 30 + 1) + 30);
            contract.setApprovedAmount(getRandomAmount(application.getRequestedAmount(), 0.8F));
            contract.setApprovedDate(LocalDateTime.now());
            contract.setSignatureStatus(CreditContract.SignatureStatus.NOT_SIGNED);
            contract.setApplication(application);
            creditContractDao.save(contract);
        }
        creditApplicationDao.update(application);
    }

    public BigDecimal getRandomAmount(BigDecimal requestedAmount, float percentage) {
        Random random = new Random();
        BigDecimal min = requestedAmount.multiply(BigDecimal.valueOf(percentage));
        BigDecimal randomPercentage = BigDecimal.valueOf(percentage + (random.nextDouble() * (1 - percentage)));
        BigDecimal randomAmount = requestedAmount.multiply(randomPercentage).setScale(2, RoundingMode.HALF_UP);

        return randomAmount;
    }

    @Transactional
    public CreditContractResponseDTO signApplication(Long clientId) {

        CreditApplication application = creditApplicationDao.findByClientId(clientId);
        CreditContract contract = application.getContract();
        contract.setSignatureStatus(CreditContract.SignatureStatus.SIGNED);
        contract.setSignatureCreated(LocalDateTime.now());
        creditApplicationDao.update(application);
        return creditMapper.toContractDTO(contract);
    }

    @Transactional
    public List<CreditResponseDTO> getAllClientApplications() {
        return creditApplicationDao.findAll().stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreditResponseDTO> getAllClientApplicationsByStatus(CreditApplication.StatusType status) {
        return creditApplicationDao.findByStatus(status).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreditResponseDTO> getAllClientApplicationsBySign(CreditContract.SignatureStatus sign) {
        return creditApplicationDao.findByApplicationSign(sign).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }
}
