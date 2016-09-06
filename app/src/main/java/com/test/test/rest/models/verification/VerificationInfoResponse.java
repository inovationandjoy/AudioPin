package com.test.test.rest.models.verification;

/**
 * Created by shahed on 05/09/2016.
 */
public class VerificationInfoResponse {
    public final Object audio;
    public final String client_message;
    public final String client_score;
    public final String created_date;
    public final String id;
    public final String name;
    public final String process_complete_date;
    public final String process_start_date;
    public final String rejection_reason;
    public final String requester;
    public final String resource;
    public final Object score_dict;
    public final Integer sleep_duration;
    public final String status;
    public final String telephone_locator;
    public final Object truth_phrases;
    public final String upload_date;
    public final Boolean use_telephone;
    public final Object used_exemption;
    public final String user_agent;
    public final Boolean verified;


    public VerificationInfoResponse(Object audio, String client_message,
            String client_score,
            String created_date,
            String id,
            String name,
            String process_complete_date,
            String process_start_date,
            String rejection_reason,
            String requester,
            String resource,
            Object score_dict,
            Integer sleep_duration,
            String status,
            String telephone_locator,
            Object truth_phrases,
            String upload_date,
            Boolean use_telephone,
            Object used_exemption,
            String user_agent, Boolean verified){

        this.audio = audio;
        this.client_message = client_message;
        this.client_score = client_score;
        this.created_date = created_date;
        this.id = id;
        this.name= name;
        this.process_complete_date = process_complete_date;
        this.process_start_date = process_start_date;
        this.rejection_reason = rejection_reason;
        this.requester = requester;
        this.resource = resource;
        this.score_dict = score_dict;
        this.sleep_duration = sleep_duration;
        this.status = status;
        this.telephone_locator = telephone_locator;
        this.truth_phrases = truth_phrases;
        this.upload_date = upload_date;
        this.use_telephone = use_telephone;
        this.used_exemption = used_exemption;
        this.user_agent = user_agent;
        this.verified = verified;

    }




}
