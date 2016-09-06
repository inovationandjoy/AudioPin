package com.test.audiopin.rest.models.enrollment;

/**
 * Created by sparvez on 2016-08-30.
 */
public class AuthRequest {
    public final String user;
    public final String password;

    public AuthRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
