package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

public class ConflictException extends StandardException {
    public ConflictException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        super(failureErrorCode, success, failureDetails);
    }

    public ConflictException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        super(failureErrorCode, failureDetails);
    }
}
