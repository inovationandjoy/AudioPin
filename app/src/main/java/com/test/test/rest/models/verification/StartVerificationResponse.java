package com.test.test.rest.models.verification;

/**
 * Created by shahed on 03/09/2016.
 */
public class StartVerificationResponse {
    public final String name;
    public final String initial_pause;
    public final String samplerate;
    public final String id;
    public final String resource;
    public final VerificationAnimation [] animation;


    public StartVerificationResponse(String name, String initial_pause, String samplerate,
                                     String id, String resource, VerificationAnimation [] animation) {
        this.name = name;
        this.initial_pause = initial_pause;
        this.samplerate = samplerate;
        this.id = id;
        this.resource = resource;
        this.animation = animation;

    }
}
