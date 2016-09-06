package com.test.audiopin.rest.models.verification;

/**
 * Created by shahed on 03/09/2016.
 */
public class VerificationAnimation {
    public final Integer duration;
    public final String is_word_boundary;
    public final Object pin_mapping;
    public final Boolean update_progress;

    public VerificationAnimation(Integer duration, String is_word_boundary,
                                 Object pin_mapping, Boolean update_progress){
        this.duration = duration;
        this.is_word_boundary = is_word_boundary;
        this.pin_mapping = pin_mapping;
        this.update_progress = update_progress;
    }
}
