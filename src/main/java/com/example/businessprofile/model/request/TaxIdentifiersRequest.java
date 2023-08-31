package com.example.businessprofile.model.request;

import com.example.businessprofile.annotations.IdValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxIdentifiersRequest {
    @IdValidation(message = "Tax Identifiers Id should not be provided.")
    private BigInteger id;
    private String pan;
    private String ein;
}
