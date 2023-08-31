package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

public class NotFoundException extends StandardException {

    public NotFoundException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        super(failureErrorCode, success, failureDetails);
    }

    public NotFoundException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        super(failureErrorCode, failureDetails);
    }
}
