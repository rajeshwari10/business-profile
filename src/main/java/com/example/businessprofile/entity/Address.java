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
@Table(name = "address", schema = "public")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "address_id_generator")
    @SequenceGenerator(name = "address_id_generator", sequenceName = "seq_address_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @NotEmpty
    @Column(name = "line_1", nullable = false)
    private String lineOne;

    @Column(name = "line_2")
    private String lineTwo;

    @NotEmpty
    @Column(name = "city", nullable = false)
    private String city;

    @NotEmpty
    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip")
    private String zip;

    @NotEmpty
    @Column(name = "country", nullable = false)
    private String country;
}
