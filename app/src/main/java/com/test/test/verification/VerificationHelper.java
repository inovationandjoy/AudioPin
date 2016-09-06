package com.test.test.verification;

import android.content.Context;

import com.test.test.enrollment.interfaces.AuthCallback;
import com.test.test.rest.AudioPinApi;
import com.test.test.rest.AudioPinApiHelper;
import com.test.test.rest.models.enrollment.AuthRequest;
import com.test.test.rest.models.enrollment.AuthResponse;
import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.rest.models.verification.StartVerificationRequest;
import com.test.test.rest.models.verification.StartVerificationResponse;
import com.test.test.rest.models.verification.VerificationInfoResponse;
import com.test.test.utils.StringFormatter;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by shahed on 03/09/2016.
 */
public class VerificationHelper {

    public static final int CLIENT_OK = 200;
    public static final int CLIENT_UNAUTHORIZED = 401;
    public static final int CLIENT_FORBIDDEN = 403;
    public static final int CLIENT_NOT_FOUND = 404;

    public static final int START_VERIFICATION_OK = 201;
    public static final int START_VERIFICATION_UNAUTHORIZED = 401;
    public static final int START_VERIFICATION_PAYMENT = 402;
    public static final int START_VERIFICATION_FORBIDDEN = 403;
    public static final int START_VERIFICATION_NOTFOUND = 404;
    public static final int START_VERIFICATION_INTERNAL_ERROR = 500;

    public static final int UPLOAD_VERIFICATION_AUDIO_OK = 202;
    public static final int VERIFICATION_INFO_RECEIVED = 200;

    private Context mContext;

    public VerificationHelper(Context context){
        mContext = context;
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


    public void startVerification(String token, StartVerificationRequest requestBody,
                                  final StartVerificationCallback callback) {
        AudioPinApi.getInstance()
                .startVerification(token, requestBody)
                .enqueue(new Callback<StartVerificationResponse>() {
                    @Override
                    public void onResponse(Call<StartVerificationResponse> call,
                                           retrofit2.Response<StartVerificationResponse> response) {
                        int statusCode = response.code();

                        switch (statusCode) {
                            case START_VERIFICATION_OK:
                                callback.onSuccess(response.body());
                                break;
                            case START_VERIFICATION_UNAUTHORIZED:
                                callback.onError("Client Unauthorized");
                                break;
                            case START_VERIFICATION_FORBIDDEN:
                                callback.onError("Client Forbidden");
                                break;
                            case START_VERIFICATION_NOTFOUND:
                                callback.onError("Client not found");
                                break;
                            default:
                                callback.onError("Authorization unknown");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<StartVerificationResponse> call, Throwable t) {
                        callback.onError("Client info retrieval exception.");
                    }
                });
    }


    public void uploadVerificationAudio(String token, String verificationId, String boundaryStr,
                                      final File theFile, final AudioUploadCallBack callBack){
        AudioPinApi.getInstance()
                .uploadVerificationAudio(token, verificationId,
                        StringFormatter.format(new Date(System.currentTimeMillis()), true),
                        RequestBody.create(MediaType.parse("application/json"), boundaryStr),
                        RequestBody.create(MediaType.parse("audio/wav"), theFile),
                        "verification.wav"
                )
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call,
                                           retrofit2.Response<Void> response) {
                        int statusCode = response.code();
                        switch(statusCode){
                            case UPLOAD_VERIFICATION_AUDIO_OK:
                                callBack.onSuccess("Verification audio uploaded.");
                                break;
                            default:
                                callBack.onError("Cannot upload verification audio");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        callBack.onError("Exception updating verification audio");
                    }
                });
    }


    public void getVerificationInfo(String token, String verificationId,
                                    final VerificationInfoCallback callback){
        AudioPinApi.getInstance()
                .getVerificationInfo(token, verificationId)
                .enqueue(new Callback<VerificationInfoResponse>() {
                    @Override
                    public void onResponse(Call<VerificationInfoResponse> call,
                                           retrofit2.Response<VerificationInfoResponse> response) {
                        int statusCode = response.code();
                        switch(statusCode) {
                            case VERIFICATION_INFO_RECEIVED:
                                if(response.body().verified != null){
                                    if(response.body().verified){
                                        callback.onSuccess("**Authorized**");
                                    }else{
                                        callback.onSuccess("--Unauthorized--");
                                    }
                                }else {
                                    callback.onSuccess("--Unauthorized--");
                                }
                                break;
                            default:
                                break;
                        }

                    }
                    @Override
                    public void onFailure(Call<VerificationInfoResponse> call, Throwable t) {

                    }
                });

    }


    public void getAT(final AuthCallback cb){

        AuthRequest ar = new AuthRequest(AudioPinApiHelper.USER, AudioPinApiHelper.PASS);
        AudioPinApi.getInstance()
                .getAuthToken(ar)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call,
                                           retrofit2.Response<AuthResponse> response) {
                        int statusCode = response.code();
                        cb.onSuccess(response.body());
                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                    }
                });

    }



}
