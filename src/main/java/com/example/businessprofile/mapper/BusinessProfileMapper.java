package com.example.businessprofile.mapper;

import com.example.businessprofile.entity.BusinessProfile;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.model.response.BusinessProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessProfileMapper {

    BusinessProfile mapBusinessProfileRequestToBusinessProfile(BusinessProfileRequest businessProfileRequest);

    BusinessProfileResponse mapBusinessProfileToBusinessProfileResponse(BusinessProfile businessProfile);
}
