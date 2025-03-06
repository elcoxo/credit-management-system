package ru.temets.credit_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "credit_contracts")
public class CreditContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "application_id")
    @JsonBackReference
    private CreditApplication application;

    private int approvedDays;
    private BigDecimal approvedAmount;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime approvedDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime signatureCreated;

    @Column(name = "signature_status")
    @Enumerated(EnumType.STRING)
    private SignatureStatus signatureStatus;

    public enum SignatureStatus {
        SIGNED, NOT_SIGNED;
    }
}
