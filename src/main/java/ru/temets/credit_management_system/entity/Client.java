package ru.temets.credit_management_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Column(name = "marital_status", columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    private MaritalStatusType maritalStatus;
    private String phoneNumber;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private CreditApplication creditApplication;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ClientEmployment employment;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ClientIdentity identity;


    public enum MaritalStatusType {
        SINGLE, MARRIED;
    }
}
