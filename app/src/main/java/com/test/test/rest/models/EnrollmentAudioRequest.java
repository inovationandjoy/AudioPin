package com.test.test.rest.models;

import com.test.test.rest.models.Interval;

/**
 * Created by sparvez on 2016-08-31.
 */
public class EnrollmentAudioRequest {
//    public final String file;
//    public final Object filename;
    public final Interval[] intervals;
//    public final Long animation_start;

    public  EnrollmentAudioRequest(//String file, Object filename,
                                   Interval[] intervals){//, Long animation_start){
//        this.file = file;
//        this.filename = filename;
        this.intervals = intervals;
//        this.animation_start = animation_start;
    }

}
