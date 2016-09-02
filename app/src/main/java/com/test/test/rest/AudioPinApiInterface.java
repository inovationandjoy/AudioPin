package com.test.test.rest;

import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.EnrollmentInfo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by shahed on 14/07/2016.
 */

public interface AudioPinApiInterface {
    @POST("/api/v1/auth")
    Call<Object> authToken(@Body AuthRequest authRequest);

    @POST("/api/v1/auth")
    Call<AuthResponse> getAuthToken(@Body AuthRequest authRequest);

    @POST("/api/v1/enrollments")
    Call<EnrollInitResponse> sendEnrollmentInfo(@Header("Authorization") String authorization,
                                                @Body EnrollmentInfo enrollmentInfo);
    @Multipart
    @PUT("/api/v1/enrollments/{id}")
    Call<ResponseBody> uploadEnrollmentAudio2(@Header("Authorization") String authorization,
                                             @Path("id") String id,
                                             @Part("animation_start") String start,
                                             @Part("intervals") RequestBody intervals,
                                             @Part("enrollment.wav\"; filename=\"blob") RequestBody body,
                                             @Part("file") String file);

}
