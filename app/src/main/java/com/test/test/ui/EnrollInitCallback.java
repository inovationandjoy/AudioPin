package com.test.test.ui;

import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;

/**
 * Created by sparvez on 2016-09-01.
 */
public interface EnrollInitCallback {
    public void onSuccess(EnrollInitResponse response);
    public void onError(String error);
}
