package com.test.test.rest.models.verification;

/**
 * Created by shahed on 03/09/2016.
 */
public class ClientInfoResponse {
    public final String agent_notification;
    public final Boolean has_verified;
    public final Boolean use_pin;
    public final Integer exemption_logon_limit;
    public final String last_telephone;
    public final Integer enroll_window_remaining;
    public final String word_doubling;
    public final Boolean is_login_locked;
    public final String id;
    public final String help_tip;
    public final Integer animation_speed;
    public final String telephone_locator;
    public final String role;
    public final Boolean use_exemption_code;
    public final String status;
    public final Integer exemption_logon_freq_minutes;
    public final Boolean is_disabled;
    public final Boolean use_telephone;
    public final Boolean exemption_logon;
    public final Integer animation_speed_floor;
    public final Boolean pin_exists;
    public final String  name;
    public final Boolean is_locked;
    public final String gender;
    public final Boolean all_clients_disabled;
    public final String exemption_logon_expiration;
    public final Boolean email_verified;

    public ClientInfoResponse(String agent_notification,
                              Boolean has_verified,
                              Boolean use_pin,
                              Integer exemption_logon_limit,
                              String last_telephone,
                              Integer enroll_window_remaining,
                              String word_doubling,
                              Boolean is_login_locked,
                              String id,
                              String help_tip,
                              Integer animation_speed,
                              String telephone_locator,
                              String role,
                              Boolean use_exemption_code,
                              String status,
                              Integer exemption_logon_freq_minutes,
                              Boolean is_disabled,
                              Boolean use_telephone,
                              Boolean exemption_logon,
                              Integer animation_speed_floor,
                              Boolean pin_exists,
                              String  name,
                              Boolean is_locked,
                              String gender,
                              Boolean all_clients_disabled,
                              String exemption_logon_expiration,
                              Boolean email_verified){

         this.agent_notification = agent_notification;
         this.has_verified = has_verified;
         this.use_pin = use_pin;
         this.exemption_logon_limit = exemption_logon_limit;
         this.last_telephone = last_telephone;
         this.enroll_window_remaining = enroll_window_remaining;
         this.word_doubling = word_doubling;
         this.is_login_locked = is_login_locked;
         this.id = id;
         this.help_tip = help_tip;
         this.animation_speed = animation_speed;
         this.telephone_locator = telephone_locator;
         this.role = role;
         this.use_exemption_code = use_exemption_code;
         this.status = status;
         this.exemption_logon_freq_minutes = exemption_logon_freq_minutes;
         this.is_disabled = is_disabled;
         this.use_telephone = use_telephone;
         this.exemption_logon = exemption_logon;
         this.animation_speed_floor = animation_speed_floor;
         this.pin_exists = pin_exists;
         this.name = name;
         this.is_locked = is_locked;
         this.gender = gender;
         this.all_clients_disabled = all_clients_disabled;
         this.exemption_logon_expiration = exemption_logon_expiration;
         this.email_verified = email_verified;
    }


}
