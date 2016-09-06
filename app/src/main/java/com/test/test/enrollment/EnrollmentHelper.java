package com.test.test.enrollment;

import android.content.Context;

import com.test.test.enrollment.interfaces.AuthCallback;
import com.test.test.enrollment.interfaces.UploadAudioCallback;
import com.test.test.enrollment.interfaces.EnrollInitCallback;
import com.test.test.rest.AudioPinApi;
import com.test.test.rest.models.enrollment.AuthRequest;
import com.test.test.rest.models.enrollment.AuthResponse;
import com.test.test.rest.models.enrollment.EnrollInitResponse;
import com.test.test.rest.models.enrollment.EnrollmentInfo;
import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.utils.StringFormatter;
import com.test.test.verification.ClientInfoCallback;

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

    public static final int CLIENT_OK = 200;
    public static final int CLIENT_UNAUTHORIZED = 401;
    public static final int CLIENT_FORBIDDEN = 403;
    public static final int CLIENT_NOT_FOUND = 404;

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

    public void initEnrollment(String token, EnrollmentInfo enrollmentInfo,
                                         final EnrollInitCallback callback){
        AudioPinApi.getInstance()
                .initEnrollment(token, enrollmentInfo)
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
                                      final File theFile, final UploadAudioCallback callback){
        AudioPinApi.getInstance()
                .uploadEnrollmentAudio(token, clientId,
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
                                callback.onSuccess("Successfully upload audio!");
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

    public void fetchClientInfo(String token, String clientId, final ClientInfoCallback callback){
        AudioPinApi.getInstance()
                .getClientInfo(token, clientId)
                .enqueue(new Callback<ClientInfoResponse>() {
                    @Override
                    public void onResponse(Call<ClientInfoResponse> call,
                                           retrofit2.Response<ClientInfoResponse> response) {
                        int statusCode = response.code();
                        switch(statusCode){
                            case CLIENT_OK:
                                callback.onSuccess(response.body());
                                break;
                            case CLIENT_UNAUTHORIZED:
                                callback.onError("Client Unauthorized");
                                break;
                            case CLIENT_FORBIDDEN:
                                callback.onError("Client Forbidden");
                                break;
                            case CLIENT_NOT_FOUND:
                                callback.onError("Client not found");
                                break;
                            default:
                                callback.onError("Authorization unknown");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<ClientInfoResponse> call, Throwable t) {
                        callback.onError("Client info retrieval exception.");
                    }
                });
    }

}
