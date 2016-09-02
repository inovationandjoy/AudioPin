package com.test.test.ui.interfaces;

import com.test.test.rest.models.AuthResponse;

/**
 * Created by sparvez on 2016-09-01.
 */
public interface AuthCallback {
    public void onSuccess(AuthResponse response);
    public void onError(String error);
}
