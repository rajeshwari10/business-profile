package com.example.businessprofile.feign;

import com.example.businessprofile.feign.model.ValidateProfileRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductServiceFeignClientFallback implements ProductServiceClient{
    @Override
    public Boolean validateProfile(ValidateProfileRequest validateProfileRequest) {
        log.error("In fallback method :: Unable to reach product service");
        return Boolean.FALSE;
    }
}
