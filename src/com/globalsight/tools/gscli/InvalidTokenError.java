package com.globalsight.tools.gscli;

public class InvalidTokenError extends Error {

    InvalidTokenError(String status, String error) {
        super(status, error);
    }
}
