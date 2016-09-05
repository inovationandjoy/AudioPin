package com.test.test.verification;

import com.test.test.rest.models.verification.ClientInfoResponse;

/**
 * Created by shahed on 03/09/2016.
 */
public interface ClientInfoCallback {
    public void onSuccess(ClientInfoResponse response);
    public void onError(String error);
}
