package com.spartansoftwareinc.globalsight.gscli;

public class InvalidTokenError extends Error {

    InvalidTokenError(String status, String error) {
        super(status, error);
    }
}
