package ru.temets.credit_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "credit_applications")
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private CreditContract contract;

    private BigDecimal requestedAmount;

    @Column(name = "status", columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    private StatusType status;


    public enum StatusType {
        APPROVED, REJECTED, INPROGRESS
    }
}
