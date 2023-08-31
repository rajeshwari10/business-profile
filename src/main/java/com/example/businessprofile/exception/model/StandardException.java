package com.example.businessprofile.exception.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;

import java.util.Objects;

public class StandardException extends RuntimeException {
    private FailureErrorCodes failureErrorCode;
    private boolean success;
    private FailureDetails failureDetails;

    public StandardException(FailureErrorCodes failureErrorCode, FailureDetails failureDetails) {
        this.success = Boolean.FALSE;
        this.failureErrorCode = failureErrorCode;
        this.failureDetails = failureDetails;
    }

    public StandardException(FailureErrorCodes failureErrorCode, boolean success, FailureDetails failureDetails) {
        this.success = Boolean.FALSE;
        this.failureErrorCode = failureErrorCode;
        this.success = success;
        this.failureDetails = failureDetails;
    }

    public FailureErrorCodes getFailureErrorCode() {
        return failureErrorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public FailureDetails getFailureDetails() {
        return failureDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StandardException)) return false;
        StandardException that = (StandardException) o;
        return isSuccess() == that.isSuccess() && getFailureErrorCode() == that.getFailureErrorCode() && getFailureDetails().equals(that.getFailureDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFailureErrorCode(), isSuccess(), getFailureDetails());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StandardException;
    }
}
