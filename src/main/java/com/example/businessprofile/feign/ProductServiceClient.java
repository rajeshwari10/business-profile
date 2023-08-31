package com.example.businessprofile.feign;

import com.example.businessprofile.feign.model.ValidateProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service",
        url = "${productService.url}",
        fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductServiceClient {
    @PostMapping(value = "${productService.api.validateProfile}")
    Boolean validateProfile(@RequestBody ValidateProfileRequest validateProfileRequest);
}
