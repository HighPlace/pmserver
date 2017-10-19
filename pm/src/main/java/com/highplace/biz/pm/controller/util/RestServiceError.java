package com.highplace.biz.pm.controller.util;

public class RestServiceError {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static RestServiceError build (Type errorType, String message) {
        RestServiceError error = new RestServiceError();
        error.code = errorType.getCode();
        error.message = message;
        return error;
    }

    public enum Type {
        BAD_REQUEST_ERROR("10000", "Bad request error"),
        INTERNAL_SERVER_ERROR("10001", "Unexpected server error"),
        VALIDATION_ERROR("10002", "Found validation issues");

        private String code;
        private String message;

        Type(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
