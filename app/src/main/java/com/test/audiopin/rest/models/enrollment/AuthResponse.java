package com.test.audiopin.rest.models.enrollment;

/**
 * Created by sparvez on 2016-09-01.
 */
public class AuthResponse {
    public final String jwt;
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
