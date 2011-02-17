package com.globalsight.tools;

public class InvalidTokenError extends Error {

    InvalidTokenError(String status, String error) {
        super(status, error);
    }
}
