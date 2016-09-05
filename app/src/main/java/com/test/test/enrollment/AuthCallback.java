package com.test.test.enrollment;

import com.test.test.rest.models.enrollment.AuthResponse;

/**
 * Created by sparvez on 2016-09-01.
 */
public interface AuthCallback {
    public void onSuccess(AuthResponse response);
    public void onError(String error);
}
