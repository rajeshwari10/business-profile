package com.example.businessprofile.model.request;

import com.example.businessprofile.annotations.IdValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessProfileRequest {

    @IdValidation(message = "Business Profile Id should not be provided.")
    private BigInteger id;

    @NotNull(message = "companyName field is mandatory")
    @NotEmpty(message = "companyName field cannot be empty")
    private String companyName;

    @NotNull(message = "legalName field is mandatory")
    @NotEmpty(message = "legalName field cannot be empty")
    private String legalName;

    @Valid
    @NotNull(message = "businessAddress field is mandatory")
    private AddressRequest businessAddress;

    @Valid
    @NotNull(message = "legalAddress field is mandatory")
    private AddressRequest legalAddress;

    @Valid
    private TaxIdentifiersRequest taxIdentifiers;

    @Email(message = "Please provide valid Email ID")
    private String email;

    private String website;
}
