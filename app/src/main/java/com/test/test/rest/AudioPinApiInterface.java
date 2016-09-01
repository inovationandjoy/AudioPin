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
    Call<ResponseBody> uploadEnrollmentAudio(@Header("Authorization") String authorization,
                                             @Path("id") String id,
                                             @Part("animation_start") String start,
                                             @Part("intervals") RequestBody intervals,
                                             @Part("enrollment.aac\"; filename=\"blob") RequestBody body,
                                             @Part("file") String file
    );

//    @Multipart
//    @PUT("/api/v1/enrollments/{client_id}")
//    Call<Object> uploadEnrollmentAudio(@Header("Authorization") String authorization,
//                                       @Path("client_id") String clientId,
//                                       @Part("animation_start") Long animationStart,
//                                       @Part("intervals") Interval interval[],
//                                       @Part("file") String file,
//                                       @Part ("filename") Object fileName);


    //@Multipart

//    @PUT("/api/v1/enrollments/{client_id}")
//    Call<Object> uploadEnrollmentAudio(@Header("Authorization") String authorization,
//                                       @Path("client_id") String clientId,
//                                       @Body EnrollmentAudioRequest request);

//    @PUT("/api/v1/enrollments/{client_id}")
//    Call<Object> uploadEnrollmentAudio(@Header("Authorization") String authorization,
//                                       @Path("client_id") String clientId,
//                                       @Body Int request);



}
