package com.cse19.ue.exception;

public enum ResponseStatusCodes {
    BIOMETRIC_SIGNATURE_CANNOT_BE_NULL(230);
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
