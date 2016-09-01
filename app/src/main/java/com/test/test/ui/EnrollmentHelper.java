package com.test.test.ui;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.test.test.rest.OurAudioPinApi;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.EnrollmentAudioRequest;
import com.test.test.rest.models.EnrollmentInfo;
import com.test.test.rest.models.Interval;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sparvez on 2016-09-01.
 */
public class EnrollmentHelper {
    public final static String TAG = "EnrollmentHelper";
    public final static int AUTH_SUCCESS = 200;
    public final static int AUTH_UNAUTHORIZED = 401;
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private Context mContext;

    public EnrollmentHelper(Context context){
        this.mContext = context;
    }

    public void fetchAuthToken(AuthRequest authRequest, final AuthCallback callback){
        OurAudioPinApi.getInstance()
                .getAuthToken(authRequest)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                        int statusCode = response.code();
                        if(statusCode == AUTH_SUCCESS){
                            AuthResponse authResponse = response.body();
                            Log.d(TAG, "Auth info retrieved");
                            callback.onSuccess(authResponse);
                        }else if(statusCode == AUTH_UNAUTHORIZED){
                            Log.d(TAG, "Client is unauthorized");
                            callback.onError("Unauthorized");
                        }else{
                            callback.onError("Unknown");
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        int ii = 0;
                    }
                });
    }

    public void initializeEnrollmentInfo(String token, EnrollmentInfo enrollmentInfo,
                                         final EnrollInitCallback callback){
        OurAudioPinApi.getInstance()
                .sendEnrollmentInfo(token, enrollmentInfo)
                .enqueue(new Callback<EnrollInitResponse>() {
                    @Override
                    public void onResponse(Call<EnrollInitResponse> call, retrofit2.Response<EnrollInitResponse> response) {
                        int statusCode = response.code();
                        callback.onSuccess(response.body());
                    }
                    @Override
                    public void onFailure(Call<EnrollInitResponse> call, Throwable t) {
                        int ii = 0;
                    }
                });
    }



    public void uploadEnrollmentAudio(String token){
        //AudioPinApiHelper.init(getBaseContext());

        String clientId = "b3df334c5588a375ae5327e4d05674d3";
        //String clientId = "abcd";
        Interval interval1 = new Interval("Chicago", 11370L, 13374L, 0);
        Interval interval2 = new Interval("Boston", 13379L, 17387L, 0);

        Interval intervals[] = new Interval[2];
        intervals[0] = interval1;
        intervals[1] = interval2;

        EnrollmentAudioRequest request = new EnrollmentAudioRequest(intervals);

        //String intervalsStr = "[{\"phrase\":\"Chicago\",\"end\":13350,\"start\":11350},{\"phrase\":\"Boston\",\"end\":15351,\"start\":13351},{\"phrase\":\"Dallas\",\"end\":17352,\"start\":15352}]";//intervals.toString();

        String intervalsStr = "[{\"phrase\":\"Chicago\",\"end\":13350,\"start\":11350},{\"phrase\":\"Boston\",\"end\":15351,\"start\":13351},{\"phrase\":\"Dallas\",\"end\":17352,\"start\":15352},{\"phrase\":\"Atlanta\",\"end\":19353,\"start\":17353},{\"phrase\":\"Denver\",\"end\":21354,\"start\":19354},{\"phrase\":\"Seattle\",\"end\":23355,\"start\":21355},{\"phrase\":\"Nashville\",\"end\":25356,\"start\":23356},{\"phrase\":\"Baltimore\",\"end\":27357,\"start\":25357},{\"phrase\":\"Orlando\",\"end\":29358,\"start\":27358},{\"phrase\":\"Memphis\",\"end\":31359,\"start\":29359},{\"phrase\":\"Cleveland\",\"end\":33360,\"start\":31360},{\"phrase\":\"Phoenix\",\"end\":35361,\"start\":33361},{\"phrase\":\"Chicago\",\"end\":42162,\"start\":40162},{\"phrase\":\"Boston\",\"end\":44163,\"start\":42163},{\"phrase\":\"Dallas\",\"end\":46164,\"start\":44164},{\"phrase\":\"Atlanta\",\"end\":48165,\"start\":46165},{\"phrase\":\"Denver\",\"end\":50166,\"start\":48166},{\"phrase\":\"Seattle\",\"end\":52167,\"start\":50167},{\"phrase\":\"Nashville\",\"end\":54168,\"start\":52168},{\"phrase\":\"Baltimore\",\"end\":56169,\"start\":54169},{\"phrase\":\"Orlando\",\"end\":58170,\"start\":56170},{\"phrase\":\"Memphis\",\"end\":60171,\"start\":58171},{\"phrase\":\"Cleveland\",\"end\":62172,\"start\":60172},{\"phrase\":\"Phoenix\",\"end\":64173,\"start\":62173},{\"phrase\":\"Chicago\",\"end\":70974,\"start\":68974},{\"phrase\":\"Boston\",\"end\":72975,\"start\":70975},{\"phrase\":\"Dallas\",\"end\":74976,\"start\":72976},{\"phrase\":\"Atlanta\",\"end\":76977,\"start\":74977},{\"phrase\":\"Denver\",\"end\":78978,\"start\":76978},{\"phrase\":\"Seattle\",\"end\":80979,\"start\":78979},{\"phrase\":\"Nashville\",\"end\":82980,\"start\":80980},{\"phrase\":\"Baltimore\",\"end\":84981,\"start\":82981},{\"phrase\":\"Orlando\",\"end\":86982,\"start\":84982},{\"phrase\":\"Memphis\",\"end\":88983,\"start\":86983},{\"phrase\":\"Cleveland\",\"end\":90984,\"start\":88984},{\"phrase\":\"Phoenix\",\"end\":92985,\"start\":90985}]";

        OurAudioPinApi.getInstance()
                .uploadEnrollmentAudio(token, clientId, format(new Date(System.currentTimeMillis()), true),
                        RequestBody.create(MediaType.parse("application/json"), intervalsStr),
                        RequestBody.create(MediaType.parse("audio/aac"), getAudioFile()),
                        "enrollment.wav"
                )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        int statusCode = response.code();
                        Object timeCard = response.body();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        int ii = 0;
                    }
                });
    }


    public static String format(Date date, boolean millis) {
        Calendar calendar = new GregorianCalendar(UTC_TIME_ZONE, Locale.US);
        calendar.setTime(date);

        // estimate capacity of buffer as close as we can (yeah, that's pedantic ;)
        int capacity = "yyyy-MM-dd hh:mm:ss".length();
        capacity += millis ? ".sss".length() : 0;
        StringBuilder formatted = new StringBuilder(capacity);

        padInt(formatted, calendar.get(Calendar.YEAR), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(Calendar.MONTH) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(Calendar.DAY_OF_MONTH), "dd".length());
        formatted.append(' ');
        padInt(formatted, calendar.get(Calendar.HOUR_OF_DAY), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(Calendar.MINUTE), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(Calendar.SECOND), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(Calendar.MILLISECOND), "sss".length());
        }

        return formatted.toString();
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }



    private  File getAudioFile() {
        File sdcard = mContext.getCacheDir();
        if (sdcard.exists()){
            int ii = 0;
        }
        File fff = new File(sdcard, "enrollment.aac");
        return fff;



    }






}
