package com.test.test.rest.models;

/**
 * Created by sparvez on 2016-09-01.
 */
public class EnrollInitResponse {
    public final String name;
    public final String samplerate;
    public final String id;
    public final String [] prompts;
    public final Animation animation;

    public EnrollInitResponse(String name, String samplerate, String id,
                              String [] prompts, Animation animation){
        this.name = name;
        this.samplerate = samplerate;
        this.id = id;
        this. prompts = prompts;
        this.animation = animation;
    }

}
