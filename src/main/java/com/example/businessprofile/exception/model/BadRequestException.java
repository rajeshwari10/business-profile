package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

public class BadRequestException extends StandardException{

    public BadRequestException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        super(failureErrorCode, success, failureDetails);
    }

    public BadRequestException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        super(failureErrorCode, failureDetails);
    }
}
