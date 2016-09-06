package com.test.audiopin.enrollment.interfaces;

import com.test.audiopin.rest.models.enrollment.AuthResponse;

/**
 * Created by sparvez on 2016-09-01.
 */
public interface AuthCallback {
    public void onSuccess(AuthResponse response);
    public void onError(String error);
}
