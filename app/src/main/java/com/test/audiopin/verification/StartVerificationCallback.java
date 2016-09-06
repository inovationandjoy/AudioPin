package com.test.audiopin.verification;

import com.test.audiopin.rest.models.verification.StartVerificationResponse;

/**
 * Created by shahed on 04/09/2016.
 */
public interface StartVerificationCallback {
    public void onSuccess(StartVerificationResponse response);
    public void onError(String error);
}
