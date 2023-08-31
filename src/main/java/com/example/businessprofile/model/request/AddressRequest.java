package com.example.businessprofile.model.request;

import com.example.businessprofile.annotations.IdValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {

    @IdValidation(message = "Address Id should not be provided.")
    private BigInteger id;

    @NotNull(message = "lineOne field is mandatory")
    @NotEmpty(message = "lineOne field cannot be empty")
    private String lineOne;

    private String lineTwo;

    @NotNull(message = "city field is mandatory")
    @NotEmpty(message = "city field cannot be empty")
    private String city;

    @NotNull(message = "state field is mandatory")
    @NotEmpty(message = "state field cannot be empty")
    private String state;

    @Size(max = 10)
    private String zip;

    @NotNull(message = "country field is mandatory")
    @NotEmpty(message = "country field cannot be empty")
    private String country;
}
