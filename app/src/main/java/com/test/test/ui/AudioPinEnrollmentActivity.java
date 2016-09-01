package com.test.test.ui;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.test.test.recorder.AudioRecorder;
import com.test.test.rest.AudioPinApiHelper;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.Enrollment;
import com.test.test.rest.models.EnrollmentInfo;

import java.io.File;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;




public class AudioPinEnrollmentActivity extends AppCompatActivity {
    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButtonStar;
    private Button mButtonHash;


    private String mPinString;
    private TextView mPinView;
    private Switch mMFSwitch;
    private TextView mEditText;
    private static String root = null;
    String[] vocabulary = null;
    private int mCounter = 0;

    String mGender;

    EnrollmentHelper enrollmentHelper;

    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        inflateComponents();
        mPinString = "";
        createDirectoriesIfNeeded();
        vocabulary = getResources().getStringArray(R.array.vocabulary);
        mGender = "M";

    }

    private void inflateComponents(){
        mButton0 = (Button)findViewById(R.id.button0);
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="0";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton1 = (Button)findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="1";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton2 = (Button)findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="2";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton3 = (Button)findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="3";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton4 = (Button)findViewById(R.id.button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="4";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton5 = (Button)findViewById(R.id.button5);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="5";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton6 = (Button)findViewById(R.id.button6);
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="6";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton7 = (Button)findViewById(R.id.button7);
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="7";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton8 = (Button)findViewById(R.id.button8);
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="8";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });
        mButton9 = (Button)findViewById(R.id.button9);
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="9";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });


        mButtonStar = (Button)findViewById(R.id.button9);
        mButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="*";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });

        mButtonHash = (Button)findViewById(R.id.button9);
        mButtonHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="#";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });

        mPinView = (TextView)findViewById(R.id.pinView);
        mEditText = (TextView) findViewById(R.id.editText);

        mMFSwitch = (Switch) findViewById(R.id.switchMaleFemale);
        mMFSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mGender = "F";
                }else{
                    mGender = "M";
                }
            }
        });
    }



    private void enroll(String key){
        if(key == null || key.isEmpty() || key.length() <= 3 ){
            return;
        }
        mPinView.setText("");

        enrollmentHelper = new EnrollmentHelper(getBaseContext());
        AuthRequest authRequest = new AuthRequest(AudioPinApiHelper.USER, AudioPinApiHelper.PASS);
        String name = UUID.randomUUID().toString();

        final EnrollmentInfo enrollmentInfo = new EnrollmentInfo(mGender, name, "2815", "", false);
        enrollmentHelper.fetchAuthToken(authRequest, new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                Toast.makeText(getBaseContext(), response.jwt, Toast.LENGTH_SHORT).show();

                final String token = "Bearer " + response.jwt;

                enrollmentHelper.initializeEnrollmentInfo("Bearer " + response.jwt, enrollmentInfo, new EnrollInitCallback() {
                    @Override
                    public void onSuccess(EnrollInitResponse response) {
                        Toast.makeText(getBaseContext(), "Enrollment initialized", Toast.LENGTH_SHORT).show();
                        animate(response, token);
                    }
                    @Override
                    public void onError(String error) {
                    }
                });
            }
            @Override
            public void onError(String error) {
            }
        });
    }

    private void createDirectoriesIfNeeded() {
        root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File folder = new File(root, "AudioRecord");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File audioFolder = new File(folder.getAbsolutePath(), "Audio");
        if (!audioFolder.exists()) {
            audioFolder.mkdir();
        }
        root = audioFolder.getAbsolutePath();
    }

    private void animate(EnrollInitResponse response, final String token){
        final Enrollment [] enrollments = response.animation.enrollment;
        final Timer timer = new Timer();
        final AudioRecorder audioRecorder = new AudioRecorder(getBaseContext());
        audioRecorder.startRecording();
        timer.scheduleAtFixedRate(new TimerTask() {
            long startTime = System.currentTimeMillis();
            int count = 0;
            int duration = Integer.parseInt(enrollments[count].duration);
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime > duration) {
                    count++;
                    if(count >= enrollments.length){
                        timer.cancel();
                        audioRecorder.stopRecording();

                        enrollmentHelper.uploadEnrollmentAudio(token);

                        return;
                    }
                    duration = Integer.parseInt(enrollments[count].duration);
                    startTime = System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEditText.setText(enrollments[count].instruction);
                        }
                    });
                } else {
                }
            }
        }, 0, 100);
    }






//    public void fetchAuthToken(){
//        //AudioPinApiHelper.init(getBaseContext());
//        AuthRequest authRequest = new AuthRequest("admin", "71xufPuaMT8z");
//        OurAudioPinApi.getInstance()
//                .authToken(authRequest)
//                .enqueue(new Callback<Object>() {
//                    @Override
//                    public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
//                        int statusCode = response.code();
//                        Object timeCard = response.body();
//                        //String token = response.body();
//
//                        String jsonString = response.body().toString();
//                        Gson gson = new Gson();
//
//                        JwtResponse jwt = gson.fromJson(jsonString, JwtResponse.class);
//
//                        //sendEnrollmentInfo("Bearer " + jwt.jwt);
//                        //uploadEnrollmentAudio("Bearer " + jwt.jwt);
//                    }
//                    @Override
//                    public void onFailure(Call<Object> call, Throwable t) {
//                       int ii = 0;
//                    }
//                });
//    }

//    public void sendEnrollmentInfo(String token){
//        //AudioPinApiHelper.init(getBaseContext());
//        AuthRequest authRequest = new AuthRequest("admin", "71xufPuaMT8z");
//        EnrollmentInfo enrollmentInfo = new EnrollmentInfo("M", "abcd3", "2815", "", false);
//        OurAudioPinApi.getInstance()
//                .sendEnrollmentInfo(token, enrollmentInfo)
//                .enqueue(new Callback<Object>() {
//                    @Override
//                    public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
//                        int statusCode = response.code();
//                        Object timeCard = response.body();
//                    }
//                    @Override
//                    public void onFailure(Call<Object> call, Throwable t) {
//                        int ii = 0;
//                    }
//                });
//    }


    /*

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
                        RequestBody.create(MediaType.parse("audio/wav"), getAudioFile()),
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



    private static File getAudioFile() {
       File sdcard = Environment.getExternalStorageDirectory();
       if (sdcard.exists()){
            int ii = 0;
        }


        //File fff = new File(sdcard, "/enrollment.wav");
        File fff = new File(sdcard, "enrollment.wav");


        return fff;

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
*/
}
