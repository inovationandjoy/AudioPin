package com.test.test.rest.models.verification;

/**
 * Created by shahed on 03/09/2016.
 */
public class StartVerificationRequest {
    public final String mode;
    public final Boolean word_doubling;
    public final int animation_speed;
    public final String user_agent;
    public final String name;
    public final String telephone_locator;
    public final Boolean use_telephone;

    public StartVerificationRequest(String mode, Boolean word_doubling, int animation_speed,
            String user_agent, String name, String telephone_locator, Boolean use_telephone){

        this.mode = mode;
        this.word_doubling = word_doubling;
        this.animation_speed = animation_speed;
        this.user_agent = user_agent;
        this.name = name;
        this.telephone_locator = telephone_locator;
        this.use_telephone = use_telephone;

    }
}
