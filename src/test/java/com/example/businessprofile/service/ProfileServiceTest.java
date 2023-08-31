package com.example.businessprofile.service;

import com.example.businessprofile.entity.Address;
import com.example.businessprofile.entity.BusinessProfile;
import com.example.businessprofile.entity.TaxIdentifiers;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.exception.model.BadRequestException;
import com.example.businessprofile.exception.model.ConflictException;
import com.example.businessprofile.exception.model.NotFoundException;
import com.example.businessprofile.exception.model.UpdateNotAllowedException;
import com.example.businessprofile.feign.ProductServiceClient;
import com.example.businessprofile.feign.model.ValidateProfileRequest;
import com.example.businessprofile.mapper.BusinessProfileMapper;
import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.model.request.AddressRequest;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.model.request.TaxIdentifiersRequest;
import com.example.businessprofile.model.response.AddressResponse;
import com.example.businessprofile.model.response.BusinessProfileResponse;
import com.example.businessprofile.model.response.TaxIdentifiersResponse;
import com.example.businessprofile.repository.AddressRepository;
import com.example.businessprofile.repository.BusinessProfileRepository;
import com.example.businessprofile.repository.TaxIdentifiersRepository;
import com.example.businessprofile.repository.UserRepository;
import com.example.businessprofile.service.helper.CacheHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@RunWith(JUnit4.class)
public class ProfileServiceTest {

    @Mock
    BusinessProfileMapper businessProfileMapper;

    @Mock
    BusinessProfileRepository businessProfileRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    TaxIdentifiersRepository taxIdentifiersRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductServiceClient productServiceClient;

    @Mock
    CacheHandler cacheHandler;

    @InjectMocks
    ProfileService profileService;

    private User user;
    private BusinessProfile businessProfile;
    private BusinessProfileRequest businessProfileRequest;
    private User userWithProfile;
    private BusinessProfileResponse businessProfileResponse;
    private BusinessProfile businessProfileWithoutTaxIdentifiers;
    private BusinessProfileRequest businessProfileRequestWithoutTaxIdentifiers;
    private User userWithoutTaxIdentifiers;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().userId(BigInteger.ONE).userName("User One").build();
        businessProfile = BusinessProfile.builder()
                .id(BigInteger.ONE)
                .businessAddress(Address.builder().id(BigInteger.ONE).lineOne("line one").city("city").country("country").state("state").build())
                .legalAddress(Address.builder().id(BigInteger.TWO).lineOne("line one").city("city").country("country").state("state").build())
                .companyName("company name")
                .legalName("legal name")
                .taxIdentifiers(TaxIdentifiers.builder().id(BigInteger.ONE).pan("pan").build())
                .build();
        businessProfileRequest = BusinessProfileRequest.builder()
                .businessAddress(AddressRequest.builder().lineOne("line one").city("city").country("country").state("state").build())
                .legalAddress(AddressRequest.builder().lineOne("line one").city("city").country("country").state("state").build())
                .companyName("company name")
                .legalName("legal name")
                .taxIdentifiers(TaxIdentifiersRequest.builder().pan("pan").build())
                .build();
        userWithProfile = User.builder().userId(BigInteger.ONE).userName("user name").businessProfile(businessProfile).build();
        businessProfileResponse = BusinessProfileResponse.builder().
                businessAddress(AddressResponse.builder().lineOne("line one").city("city").country("country").state("state").build())
                .legalAddress(AddressResponse.builder().lineOne("line one").city("city").country("country").state("state").build())
                .companyName("company name")
                .legalName("legal name")
                .taxIdentifiers(TaxIdentifiersResponse.builder().pan("pan").build())
                .build();
        businessProfileWithoutTaxIdentifiers = BusinessProfile.builder()
                .id(BigInteger.ONE)
                .businessAddress(Address.builder().id(BigInteger.ONE).lineOne("line one").city("city").country("country").state("state").build())
                .legalAddress(Address.builder().id(BigInteger.TWO).lineOne("line one").city("city").country("country").state("state").build())
                .companyName("company name")
                .legalName("legal name")
                .build();
        businessProfileRequestWithoutTaxIdentifiers = BusinessProfileRequest.builder()
                .businessAddress(AddressRequest.builder().lineOne("line one").city("city").country("country").state("state").build())
                .legalAddress(AddressRequest.builder().lineOne("line one").city("city").country("country").state("state").build())
                .companyName("company name")
                .legalName("legal name")
                .build();
        userWithoutTaxIdentifiers = User.builder().userId(BigInteger.ONE).userName("user name").businessProfile(businessProfileWithoutTaxIdentifiers).build();
    }

    @Test
    public void createBusinessProfile() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(user));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.TRUE);
        when(businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(any(BusinessProfileRequest.class))).thenReturn(businessProfile);
        assertSame(profileService.createBusinessProfile(BigInteger.ONE, businessProfileRequest).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.CREATED.name(), TRUE, null).getStatusCode());
    }

    @Test
    public void createBusinessProfileWithoutTaxIdentifiers() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(user));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.TRUE);
        when(businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(any(BusinessProfileRequest.class))).thenReturn(businessProfileWithoutTaxIdentifiers);
        assertSame(profileService.createBusinessProfile(BigInteger.ONE, businessProfileRequestWithoutTaxIdentifiers).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.CREATED.name(), TRUE, null).getStatusCode());
    }

    @Test
    public void createBusinessProfileUpdateNotAllowed() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(user));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.FALSE);
        assertThrows(UpdateNotAllowedException.class, () -> profileService.createBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void createBusinessProfileInvalidUser() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> profileService.createBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void createBusinessProfileConflictException() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(userWithProfile));
        assertThrows(ConflictException.class, () -> profileService.createBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void updateBusinessProfile() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(userWithProfile));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.TRUE);
        when(businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(any(BusinessProfileRequest.class))).thenReturn(businessProfile);
        assertSame(profileService.updateBusinessProfile(BigInteger.ONE, businessProfileRequest).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.ACCEPTED.name(), TRUE, null).getStatusCode());
    }

    @Test
    public void updateBusinessProfileWithoutTaxIdentifiers() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(userWithoutTaxIdentifiers));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.TRUE);
        when(businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(any(BusinessProfileRequest.class))).thenReturn(businessProfileWithoutTaxIdentifiers);
        assertSame(profileService.updateBusinessProfile(BigInteger.ONE, businessProfileRequestWithoutTaxIdentifiers).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.ACCEPTED.name(), TRUE, null).getStatusCode());
    }

    @Test
    public void updateBusinessProfileUpdateNotAllowed() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(userWithProfile));
        when(productServiceClient.validateProfile(any(ValidateProfileRequest.class))).thenReturn(Boolean.FALSE);
        assertThrows(UpdateNotAllowedException.class, () -> profileService.updateBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void updateBusinessProfileInvalidUser() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> profileService.updateBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void updateBusinessProfileNotPresent() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(user));
        assertThrows(BadRequestException.class, () -> profileService.updateBusinessProfile(BigInteger.ONE, businessProfileRequest));
    }

    @Test
    public void getBusinessProfile() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(userWithProfile));
        when(businessProfileMapper.mapBusinessProfileToBusinessProfileResponse(any(BusinessProfile.class))).thenReturn(businessProfileResponse);
        assertSame(profileService.getBusinessProfile(BigInteger.ONE).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.OK.name(), TRUE, businessProfileResponse).getStatusCode());
    }

    @Test
    public void getBusinessProfileInvalidUser() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> profileService.getBusinessProfile(BigInteger.ONE));
    }

    @Test
    public void getBusinessProfileNotFound() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(user));
        assertThrows(NotFoundException.class, () -> profileService.getBusinessProfile(BigInteger.ONE));
    }
}