package com.example.businessprofile.controller;

import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.model.request.AddressRequest;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.model.request.TaxIdentifiersRequest;
import com.example.businessprofile.model.response.AddressResponse;
import com.example.businessprofile.model.response.BusinessProfileResponse;
import com.example.businessprofile.model.response.TaxIdentifiersResponse;
import com.example.businessprofile.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileControllerTest {

    @Mock
    ProfileService profileService;

    @InjectMocks
    ProfileController profileController;

    private MockMvc mockMvc;
    private String jsonRequest;
    private BusinessProfileRequest businessProfileRequest;
    private BusinessProfileResponse businessProfileResponse;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
        jsonRequest = """
                {
                    "companyName" : "string",
                    "legalName" : "string",
                    "businessAddress" : {
                        "lineOne" : "string",
                        "city": "string",
                        "state" : "string",
                        "country" : "string"
                    },
                    "legalAddress" : {
                        "lineOne" : "string",
                        "city": "string",
                        "state" : "string",
                        "country" : "string"
                    },
                    "taxIdentifiers" : {
                        "pan" : "string"
                    }
                }
                """;
        businessProfileRequest = BusinessProfileRequest.builder()
                .businessAddress(AddressRequest.builder().lineOne("string").city("string").country("string").state("string").build())
                .legalAddress(AddressRequest.builder().lineOne("string").city("string").country("string").state("string").build())
                .companyName("string")
                .legalName("string")
                .taxIdentifiers(TaxIdentifiersRequest.builder().pan("string").build())
                .build();
        businessProfileResponse = BusinessProfileResponse.builder()
                .businessAddress(AddressResponse.builder().lineOne("string").city("string").country("string").state("string").build())
                .legalAddress(AddressResponse.builder().lineOne("string").city("string").country("string").state("string").build())
                .companyName("string")
                .legalName("string")
                .taxIdentifiers(TaxIdentifiersResponse.builder().pan("string").build())
                .build();
    }

    @Test
    public void createBusinessProfile() throws Exception {
        Mockito.when(profileService.createBusinessProfile(any(BigInteger.class), any(BusinessProfileRequest.class))).thenReturn(ResponseMaster.mapResponseMaster(HttpStatus.CREATED.toString(), Boolean.TRUE, null));
        mockMvc.perform(post("/profile/user/1").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(profileService).createBusinessProfile(BigInteger.ONE, businessProfileRequest);
        verify(profileService, times(1)).createBusinessProfile(BigInteger.ONE, businessProfileRequest);
    }

    @Test
    public void updateBusinessProfile() throws Exception {
        Mockito.when(profileService.updateBusinessProfile(any(BigInteger.class), any(BusinessProfileRequest.class))).thenReturn(ResponseMaster.mapResponseMaster(HttpStatus.ACCEPTED.toString(), Boolean.TRUE, null));
        mockMvc.perform(put("/profile/user/1").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andDo(print())
                .andExpect(status().isAccepted());
        verify(profileService).updateBusinessProfile(BigInteger.ONE, businessProfileRequest);
        verify(profileService, times(1)).updateBusinessProfile(BigInteger.ONE, businessProfileRequest);
    }

    @Test
    public void getBusinessProfile() throws Exception {
        Mockito.when(profileService.getBusinessProfile(any(BigInteger.class))).thenReturn(ResponseMaster.mapResponseMaster(HttpStatus.OK.toString(), Boolean.TRUE, businessProfileResponse));
        mockMvc.perform(get("/profile/user/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(profileService).getBusinessProfile(BigInteger.ONE);
        verify(profileService, times(1)).getBusinessProfile(BigInteger.ONE);
    }
}