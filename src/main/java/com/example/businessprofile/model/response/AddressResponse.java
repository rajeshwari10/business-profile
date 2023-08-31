package com.example.businessprofile.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String zip;
    private String country;
}
