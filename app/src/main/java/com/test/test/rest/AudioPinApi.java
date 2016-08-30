package com.test.test.rest;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.test.util.RequestHelper;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AudioPinApiHelper.getBaseUrl())
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        mApiService = retrofit.create(AudioPinApiInterface.class);
    }


    private static OkHttpClient getUnsafeOkHttpClient() {
      try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });


            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Call<Object> getEnrollmentInfo(String client_id){
        String header = RequestHelper.authHeader;
        return mApiService.getEnrollmentInfo(header, client_id);
    }

    public Call<Object> authToken(){
         String clientid = AudioPinApiHelper.getClientId();
         String clientS = AudioPinApiHelper.getClientSecret();
         //return mApiService.getAuthToken(clientid, clientS);
        return mApiService.getAuthTokenNew("admin", "71xufPuaMT8z");
         //return mApiService.getAuthTokenNew();
     }

    private static class AudioPinApiHolder {
        public static final AudioPinApi instance = new AudioPinApi();
    }

}
