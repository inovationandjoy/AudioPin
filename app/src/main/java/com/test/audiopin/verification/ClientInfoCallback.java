package com.test.audiopin.verification;

import com.test.audiopin.rest.models.verification.ClientInfoResponse;

/**
 * Created by shahed on 03/09/2016.
 */
public interface ClientInfoCallback {
    public void onSuccess(ClientInfoResponse response);
    public void onError(String error);
}
