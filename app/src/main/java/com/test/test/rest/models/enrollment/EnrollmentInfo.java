package com.test.test.rest.models.enrollment;

/**
 * Created by sparvez on 2016-08-30.
 */
public class EnrollmentInfo {
    public final String gender;
    public  final String name;
    public final String pin;
    public final String telephone_locator;
    public final Boolean use_telephone;

    public EnrollmentInfo (String gender, String name, String pin,
                          String telephone_locator, Boolean use_telephone)
    {
        this.gender = gender;
        this.name = name;
        this.pin = pin;
        this.telephone_locator = telephone_locator;
        this.use_telephone = use_telephone;
    }
}
