package com.test.audiopin.rest;

import android.annotation.SuppressLint;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public class SelfSigningClientBuilder {

    @SuppressWarnings("null")
    public static OkHttpClient.Builder configureClient(OkHttpClient.Builder client) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, certs, new SecureRandom());
        } catch (GeneralSecurityException e) {
        }
        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            if (sslContext != null) {
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                client.hostnameVerifier(hostnameVerifier)
                      .sslSocketFactory(sslSocketFactory);
            }
        } catch (final Exception e) {
        }
        return client;
    }
}
