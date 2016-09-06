package com.test.audiopin.rest.models.enrollment;

/**
 * Created by sparvez on 2016-08-31.
 */
public class Interval {
    public final String phrase;
    public final Long start;
    public final Long end;

    public Interval(String phrase, Long start, Long end){
        this.phrase = phrase;
        this.start = start;
        this.end = end;
    }
}
