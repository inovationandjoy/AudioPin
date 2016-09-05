package com.test.test.verification;

import android.content.Context;

import com.test.test.rest.AudioPinApi;
import com.test.test.rest.AudioPinApiHelper;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.rest.models.verification.StartVerificationRequest;
import com.test.test.rest.models.verification.StartVerificationResponse;
import com.test.test.enrollment.AuthCallback;
import com.test.test.ui.interfaces.ClientInfoCallback;
import com.test.test.ui.interfaces.StartVerificationCallback;
import com.test.test.ui.utils.StringFormatter;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
                                      final File theFile){
        AudioPinApi.getInstance()
                .uploadVerificationAudio(token, verificationId,
                        StringFormatter.format(new Date(System.currentTimeMillis()), true),
                        RequestBody.create(MediaType.parse("application/json"), boundaryStr),
                        RequestBody.create(MediaType.parse("audio/wav"), theFile),
                        "verification.wav"
                )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           retrofit2.Response<ResponseBody> response) {

                        int statusCode = response.code();
                        int abc = 0;
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                        int abc = 0;

                        cb.onSuccess(response.body());

                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                    }
                });

    }



}
