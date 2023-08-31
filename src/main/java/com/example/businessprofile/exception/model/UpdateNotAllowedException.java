package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

public class UpdateNotAllowedException extends StandardException{
    public UpdateNotAllowedException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        super(failureErrorCode, failureDetails);
    }

    public UpdateNotAllowedException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        super(failureErrorCode, success, failureDetails);
    }
}
