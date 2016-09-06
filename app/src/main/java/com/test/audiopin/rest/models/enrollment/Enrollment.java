package com.test.audiopin.rest.models.enrollment;

/**
 * Created by sparvez on 2016-09-01.
 */
public class Enrollment {
    public final String duration;
    public final String instruction;
    public final Boolean is_bookmark;
    public final Boolean is_word_boundary;
    public final Boolean update_progress;
    public final Object pin_mapping;


    public Enrollment(String duration, String instruction,
                      Boolean is_bookmark, Boolean is_word_boundary,
                      Boolean update_progress, Object pin_mapping){
        this.duration = duration;
        this.instruction = instruction;
        this.is_bookmark = is_bookmark;
        this.is_word_boundary = is_word_boundary;
        this.update_progress = update_progress;
        this.pin_mapping = pin_mapping;
    }

}



