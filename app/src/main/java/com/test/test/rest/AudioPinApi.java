package com.test.test.rest;

import android.content.res.Resources;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.EnrollmentInfo;
import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.rest.models.verification.StartVerificationRequest;
import com.test.test.rest.models.verification.StartVerificationResponse;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by shahed on 27/08/2016.
 */
public class AudioPinApi {

    private AudioPinApiInterface mApiService;
    private Resources mResources;

    /**
     * Gets the instance of the api
     * @return returns api instance
     */
    public static AudioPinApi getInstance(){
        return AudioPinApiHolder.instance;
    }

    /**
     * Constructor
     */
    public AudioPinApi(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = SelfSigningClientBuilder.configureClient(new OkHttpClient.Builder()
            .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder rb = original.newBuilder()
                                .header("User-Agent", "android " + "0.15")
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .method(original.method(), original.body());
                        Response res = chain.proceed(rb.build());

                        return res;//chain.proceed(rb.build());
                    }
                })
                .addInterceptor(new CurlInterceptor(new Loggable() {
                    @Override
                    public void log(String message) {

                    }

                })).addInterceptor(interceptor)
                .retryOnConnectionFailure(true))
            .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AudioPinApiHelper.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        mApiService = retrofit.create(AudioPinApiInterface.class);
    }

    public Call<AuthResponse> getAuthToken(AuthRequest authRequest){
        return mApiService.getAuthToken(authRequest);
    }

    public Call<EnrollInitResponse> sendEnrollmentInfo(String token, EnrollmentInfo enrollmentInfo){
        return mApiService.sendEnrollmentInfo(token, enrollmentInfo);
    }

    public Call<ResponseBody> uploadEnrollmentAudio2(String token, String clientId,
                                                    String animationStart, RequestBody intervals,
                                                    RequestBody body, String filename){
        return mApiService.uploadEnrollmentAudio2(token, clientId, animationStart, intervals,
                body, filename);
    }


    //Verification
    public Call<ClientInfoResponse> getClientInfo(String authorization, String clientId){
        return mApiService.getClientInfo(authorization, clientId);
    }

    public Call<StartVerificationResponse> startVerification(String authorization,
                                                             StartVerificationRequest request){
        return mApiService.startVerification (authorization, request);
    }

    private static class AudioPinApiHolder {
        public static final AudioPinApi instance = new AudioPinApi();
    }

}
