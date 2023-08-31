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
@Table(name = "product", schema = "public")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "seq_product_id", allocationSize = 1)
    @Column(name = "product_id", nullable = false)
    private BigInteger productId;

    @NotEmpty
    @Column(name = "product_name", nullable = false)
    private String productName;
}
