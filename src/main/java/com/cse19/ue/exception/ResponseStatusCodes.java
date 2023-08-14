package com.cse19.ue.exception;

public enum ResponseStatusCodes {
    BIOMETRIC_SIGNATURE_CANNOT_BE_NULL(230),
    BIOMETRIC_SIGNATURE_IS_NOT_MATCHING(231),
    USER_NOT_EXIST_FOR_APPROVER_ID(232),
    ERRR_OCCURED_WHILE_SAVE_GUEST(233);


    private int responseCode;

    ResponseStatusCodes(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public static int valueOf(ResponseStatusCodes responseStatusCodes) {
        return responseStatusCodes.responseCode;
    }
}
