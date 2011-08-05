package com.globalsight.tools.gscli;

public class Error {
    private String status, error;
    Error(String status, String error) {
        this.status = status;
        this.error = error;
    }
    public String getStatus() {
        return status;
    }
    public String getError() {
        return error;
    }
}
