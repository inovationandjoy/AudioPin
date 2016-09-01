package com.test.test.rest.models;

/**
 * Created by sparvez on 2016-08-31.
 */
public class Interval {
    public final String phrase;
    public final Long start;
    public final Long end;
    public final Integer time_correction;

    public Interval(String phrase, Long start, Long end,
                    Integer time_correction){
        this.phrase = phrase;
        this.start = start;
        this.end = end;
        this.time_correction = time_correction;
    }
}
