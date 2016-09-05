package com.test.test.enrollment;

import com.test.test.rest.models.enrollment.EnrollInitResponse;

/**
 * Created by sparvez on 2016-09-01.
 */
public interface EnrollInitCallback {
    public void onSuccess(EnrollInitResponse response);
    public void onError(String error);
}
