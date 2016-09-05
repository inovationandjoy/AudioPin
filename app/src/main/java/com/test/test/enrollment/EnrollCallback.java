package com.test.test.enrollment;

import com.test.test.rest.models.EnrollInitResponse;

/**
 * Created by sparvez on 2016-09-02.
 */
public interface EnrollCallback {
    public void onSuccess(String response);
    public void onError(String error);
}
