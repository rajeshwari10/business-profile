package com.example.businessprofile.exception.model.failure;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FailureDetails {
    private String description;
    private Map<String, Object> details;
}
