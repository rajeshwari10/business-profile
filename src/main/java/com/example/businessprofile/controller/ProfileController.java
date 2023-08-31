package com.example.businessprofile.controller;

import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.model.response.BusinessProfileResponse;
import com.example.businessprofile.service.ProfileService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.math.BigInteger;

import static org.springframework.http.HttpStatus.*;


@OpenAPIDefinition(
        info = @Info(
                title = "Business Profile Service")
)
@Slf4j
@Tag(name = "Profile Controller")
@RestController
@AllArgsConstructor
@RequestMapping("/profile")
@Validated
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ResponseMaster<Void>> createBusinessProfile(@PathVariable("userId") BigInteger userId,
                                                                      @Valid @RequestBody BusinessProfileRequest businessProfile) {
        return ResponseEntity.status(CREATED).body(profileService.createBusinessProfile(userId, businessProfile));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseMaster<Void>> updateBusinessProfile(@PathVariable("userId") BigInteger userId,
                                                                      @Valid @RequestBody BusinessProfileRequest businessProfileRequest) {
        return ResponseEntity.status(ACCEPTED).body(profileService.updateBusinessProfile(userId, businessProfileRequest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseMaster<BusinessProfileResponse>> getBusinessProfile(@PathVariable("userId") BigInteger userId) {
        return ResponseEntity.status(OK).body(profileService.getBusinessProfile(userId));
    }
}
