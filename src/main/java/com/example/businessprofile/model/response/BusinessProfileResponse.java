package com.example.businessprofile.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessProfileResponse {
    private String companyName;
    private String legalName;
    private AddressResponse businessAddress;
    private AddressResponse legalAddress;
    private TaxIdentifiersResponse taxIdentifiers;
    private String email;
    private String website;
}
