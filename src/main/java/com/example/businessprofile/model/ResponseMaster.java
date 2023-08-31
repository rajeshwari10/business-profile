package com.example.businessprofile.model;

import com.example.businessprofile.exception.model.failure.FailureDetails;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseMaster<T> {
    private String statusCode;
    private Boolean success;
    private FailureDetails failureDetails;
    private T result;

    public static <T> ResponseMaster<T> mapResponseMaster(String statusCode, boolean success, T result) {
        ResponseMaster.ResponseMasterBuilder<T> response = builder();
        return response.statusCode(statusCode).success(success).result(result).build();
    }

    public static <T> ResponseMaster<T> mapResponseMaster(String statusCode, boolean success, FailureDetails failureDetails) {
        ResponseMaster.ResponseMasterBuilder<T> response = builder();
        return response.statusCode(statusCode).success(success).failureDetails(failureDetails).build();
    }

    public static <T> ResponseMaster<T> mapResponseMaster(String statusCode, boolean success, FailureDetails failureDetails, T result) {
        ResponseMaster.ResponseMasterBuilder<T> response = builder();
        return response.statusCode(statusCode).success(success).failureDetails(failureDetails).result(result).build();
    }

    public static <T> ResponseMaster.ResponseMasterBuilder<T> builder() {
        return new ResponseMaster.ResponseMasterBuilder();
    }

}
