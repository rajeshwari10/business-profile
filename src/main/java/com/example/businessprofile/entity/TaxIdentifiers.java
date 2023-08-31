package com.example.businessprofile.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tax_identifiers", schema = "public")
@Entity
public class TaxIdentifiers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tax_identifiers_id_generator")
    @SequenceGenerator(name = "tax_identifiers_id_generator", sequenceName = "seq_tax_identifiers_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "pan")
    private String pan;

    @Column(name = "ein")
    private String ein;
}
