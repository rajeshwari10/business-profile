package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

public class TechnicalException extends StandardException {

    public TechnicalException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        super(failureErrorCode, success, failureDetails);
    }

    public TechnicalException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        super(failureErrorCode, failureDetails);
    }
}
