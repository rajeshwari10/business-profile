package com.example.businessprofile.service;

import com.example.businessprofile.entity.BusinessProfile;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.exception.model.BadRequestException;
import com.example.businessprofile.exception.model.ConflictException;
import com.example.businessprofile.exception.model.NotFoundException;
import com.example.businessprofile.exception.model.UpdateNotAllowedException;
import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;
import com.example.businessprofile.feign.ProductServiceClient;
import com.example.businessprofile.feign.model.ValidateProfileRequest;
import com.example.businessprofile.mapper.BusinessProfileMapper;
import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.model.response.BusinessProfileResponse;
import com.example.businessprofile.repository.AddressRepository;
import com.example.businessprofile.repository.BusinessProfileRepository;
import com.example.businessprofile.repository.TaxIdentifiersRepository;
import com.example.businessprofile.repository.UserRepository;
import com.example.businessprofile.service.helper.CacheHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

import static com.example.businessprofile.utils.BusinessProfileConstants.ExceptionConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final BusinessProfileMapper businessProfileMapper;
    private final BusinessProfileRepository businessProfileRepository;
    private final AddressRepository addressRepository;
    private final TaxIdentifiersRepository taxIdentifiersRepository;
    private final UserRepository userRepository;
    private final ProductServiceClient productServiceClient;
    private final CacheHandler cacheHandler;

    public ResponseMaster<Void> createBusinessProfile(BigInteger userId, BusinessProfileRequest businessProfile) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            if (user.getBusinessProfile() == null) {
                boolean updateAllowed = productServiceClient.validateProfile(ValidateProfileRequest.builder()
                        .businessProfile(businessProfile.toString())
                        .productIds(cacheHandler.getProductIdsByUserId(userId, FALSE))
                        .build());
                if (!updateAllowed) {
                    throw new UpdateNotAllowedException(FailureErrorCodes.ERR406, FailureDetails.builder().description(BAD_REQUEST_11).build());
                } else {
                    BusinessProfile businessProfileToSave = businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(businessProfile);
                    addressRepository.saveAndFlush(businessProfileToSave.getBusinessAddress());
                    addressRepository.saveAndFlush(businessProfileToSave.getLegalAddress());
                    if (businessProfile.getTaxIdentifiers() != null)
                        taxIdentifiersRepository.saveAndFlush(businessProfileToSave.getTaxIdentifiers());
                    BusinessProfile businessProfileSaved = businessProfileRepository.saveAndFlush(businessProfileToSave);
                    user.setBusinessProfile(businessProfileSaved);
                    userRepository.save(user);
                }
            } else {
                throw new ConflictException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_6, userId)).build());
            }
        }, () -> {
            throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_5, userId)).build());
        });
        return ResponseMaster.mapResponseMaster(HttpStatus.CREATED.name(), TRUE, null);
    }

    public ResponseMaster<Void> updateBusinessProfile(BigInteger userId, BusinessProfileRequest businessProfile) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            if (user.getBusinessProfile() == null) {
                throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_8, userId)).build());
            } else {
                boolean updateAllowed = productServiceClient.validateProfile(ValidateProfileRequest.builder()
                        .businessProfile(businessProfile.toString())
                        .productIds(cacheHandler.getProductIdsByUserId(userId, FALSE))
                        .build());
                if (!updateAllowed) {
                    throw new UpdateNotAllowedException(FailureErrorCodes.ERR406, FailureDetails.builder().description(BAD_REQUEST_11).build());
                } else {
                    BusinessProfile existingBusinessProfile = user.getBusinessProfile();
                    businessProfile.setId(existingBusinessProfile.getId());
                    businessProfile.getBusinessAddress().setId(existingBusinessProfile.getBusinessAddress().getId());
                    businessProfile.getLegalAddress().setId(existingBusinessProfile.getLegalAddress().getId());
                    if (existingBusinessProfile.getTaxIdentifiers() != null && businessProfile.getTaxIdentifiers() != null)
                        businessProfile.getTaxIdentifiers().setId(existingBusinessProfile.getTaxIdentifiers().getId());
                    BusinessProfile businessProfileToSave = businessProfileMapper.mapBusinessProfileRequestToBusinessProfile(businessProfile);
                    addressRepository.saveAndFlush(businessProfileToSave.getBusinessAddress());
                    addressRepository.saveAndFlush(businessProfileToSave.getLegalAddress());
                    if (businessProfile.getTaxIdentifiers() != null)
                        taxIdentifiersRepository.saveAndFlush(businessProfileToSave.getTaxIdentifiers());
                    BusinessProfile businessProfileSaved = businessProfileRepository.saveAndFlush(businessProfileToSave);
                    user.setBusinessProfile(businessProfileSaved);
                    userRepository.save(user);
                }
            }
        }, () -> {
            throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_7, userId)).build());
        });
        return ResponseMaster.mapResponseMaster(HttpStatus.ACCEPTED.name(), TRUE, null);
    }

    public ResponseMaster<BusinessProfileResponse> getBusinessProfile(BigInteger userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getBusinessProfile() == null) {
                throw new NotFoundException(FailureErrorCodes.ERR404, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_9, userId)).build());
            } else {
                return ResponseMaster.mapResponseMaster(HttpStatus.OK.name(), TRUE, businessProfileMapper.mapBusinessProfileToBusinessProfileResponse(user.getBusinessProfile()));
            }
        } else {
            throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_10, userId)).build());
        }
    }
}
