package com.example.businessprofile.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "business_profile", schema = "public")
@Entity
public class BusinessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "business_profile_id_generator")
    @SequenceGenerator(name = "business_profile_id_generator", sequenceName = "seq_business_profile_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @NotEmpty
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotEmpty
    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_address_id", referencedColumnName = "id", nullable = false)
    private Address businessAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_address_id", referencedColumnName = "id", nullable = false)
    private Address legalAddress;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "tax_identifiers_id", referencedColumnName = "id")
    private TaxIdentifiers taxIdentifiers;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;
}
