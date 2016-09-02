package com.test.test.ui;

import android.content.Context;

import com.test.test.rest.AudioPinApi;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.EnrollmentInfo;
import com.test.test.ui.interfaces.AuthCallback;
import com.test.test.ui.interfaces.EnrollCallback;
import com.test.test.ui.interfaces.EnrollInitCallback;
import com.test.test.ui.utils.StringFormatter;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sparvez on 2016-09-01.
 */
public class EnrollmentHelper {
    public final static String TAG = "EnrollmentHelper";
    public final static int AUTH_SUCCESS = 200;
    public final static int AUTH_UNAUTHORIZED = 401;
    public final static int INIT_ENROLL_SUCCESS = 201;
    public final static int INIT_ENROLL_BADREQ = 400;
    public final static int INIT_ENROLL_UNAUTHORIZED = 401;
    public final static int INIT_ENROLL_PAYMENT_REQUIRED = 402;
    public final static int INIT_ENROLL_FORBIDDEN = 403;
    public final static int INIT_ENROLL_REQTIMEOUT = 408;
    public final static int INIT_ENROLL_SERVICE_UNAVAILABLE = 503;

    public final static int ENROLL_BAD_REQUEST = 400;
    public final static int ENROLL_UNAUTHORIZED = 401;
    public final static int ENROLL_FORBIDDEN = 403;
    public final static int ENROLL_NOT_FOUND = 404;
    public final static int ENROLL_UNSUPPORTED_TYPE = 415;
    public final static int ENROLL_INTERNAL_ERROR = 500;
    public final static int ENROLL_SUCCESS = 201;

    private Context mContext;

    public EnrollmentHelper(Context context){
        this.mContext = context;
    }

    public void fetchAuthToken(AuthRequest authRequest, final AuthCallback callback){
        AudioPinApi.getInstance()
                .getAuthToken(authRequest)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call,
                                           retrofit2.Response<AuthResponse> response) {
                        int statusCode = response.code();
                        switch(statusCode){
                            case AUTH_SUCCESS:
                                callback.onSuccess(response.body());
                                break;
                            case AUTH_UNAUTHORIZED:
                                callback.onError("Unauthorized");
                                break;
                            default:
                                callback.onError("Authorization unknown");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        callback.onError("Auth token retrieval exception.");
                    }
                });
    }

    public void initializeEnrollmentInfo(String token, EnrollmentInfo enrollmentInfo,
                                         final EnrollInitCallback callback){
        AudioPinApi.getInstance()
                .sendEnrollmentInfo(token, enrollmentInfo)
                .enqueue(new Callback<EnrollInitResponse>() {
                    @Override
                    public void onResponse(Call<EnrollInitResponse> call,
                                           retrofit2.Response<EnrollInitResponse> response) {
                        int statusCode = response.code();
                        switch(statusCode){
                            case INIT_ENROLL_SUCCESS:
                                callback.onSuccess(response.body());
                                break;
                            case INIT_ENROLL_BADREQ:
                                callback.onError("Bad request");
                                break;
                            case INIT_ENROLL_UNAUTHORIZED:
                                callback.onError("Unauthorized");
                                break;
                            case INIT_ENROLL_PAYMENT_REQUIRED:
                                callback.onError("Payment required");
                                break;
                            case INIT_ENROLL_FORBIDDEN:
                                callback.onError("Forbidden");
                                break;
                            case INIT_ENROLL_REQTIMEOUT:
                                callback.onError("Request time out");
                                break;
                            case INIT_ENROLL_SERVICE_UNAVAILABLE:
                                callback.onError("Service unavailable");
                                break;
                            default:
                                callback.onError("Enrollment init Unknown issue");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<EnrollInitResponse> call, Throwable t) {
                        callback.onError("Enrollment init exception.");
                    }
                });
    }

    public void uploadEnrollmentAudio(String token, String clientId, String intervalsStr,
                                      final File theFile, final EnrollCallback callback){
        AudioPinApi.getInstance()
                .uploadEnrollmentAudio2(token, clientId,
                        StringFormatter.format(new Date(System.currentTimeMillis()), true),
                        RequestBody.create(MediaType.parse("application/json"), intervalsStr),
                        RequestBody.create(MediaType.parse("audio/wav"), theFile),
                        "enrollment.wav"
                )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           retrofit2.Response<ResponseBody> response) {
                        int statusCode = response.code();
                        switch(statusCode){
                            case ENROLL_BAD_REQUEST:
                                callback.onError("Bad enrollment request");
                                break;
                            case ENROLL_UNAUTHORIZED:
                                callback.onError("Enrollment unauthorized");
                                break;
                            case ENROLL_FORBIDDEN:
                                callback.onError("Enrollment forbidden");
                                break;
                            case ENROLL_NOT_FOUND:
                                callback.onError("Enrollment not found");
                                break;
                            case ENROLL_UNSUPPORTED_TYPE:
                                callback.onError("Enrollment unsupported type");
                                break;
                            case ENROLL_INTERNAL_ERROR:
                                callback.onError("Enrollment internal error");
                                break;
                            case ENROLL_SUCCESS:
                                callback.onSuccess("Successfully enrolled!");
                                break;
                            default:
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
