package com.example.businessprofile.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessProfileConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExceptionConstants {
        public static final String BAD_REQUEST_1 = "User ID %s doesn't exist";
        public static final String BAD_REQUEST_2 = "Product ID %s doesn't exist";
        public static final String BAD_REQUEST_3 = "Already subscribed to product %s";
        public static final String BAD_REQUEST_4 = "Required fields are missing, empty or invalid";
        public static final String BAD_REQUEST_5 = "User ID %s not found to create the profile";
        public static final String BAD_REQUEST_6 = "User %s already has business profile. Please perform update operation if profile has to be updated.";
        public static final String BAD_REQUEST_7 = "User ID %s not found to update the profile";
        public static final String BAD_REQUEST_8 = "User %s doesn't have business profile. Please create one before updating.";
        public static final String BAD_REQUEST_9 = "User %s doesn't have business profile";
        public static final String BAD_REQUEST_10 = "User ID %s not found";
        public static final String BAD_REQUEST_11 = "Not allowed to update the Business Profile";

    }
}
