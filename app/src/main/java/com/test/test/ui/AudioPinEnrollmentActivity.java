package com.test.test.ui;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.test.recorder.WavAudioRecorder;
import com.test.test.rest.AudioPinApiHelper;
import com.test.test.rest.models.AuthRequest;
import com.test.test.rest.models.AuthResponse;
import com.test.test.rest.models.EnrollInitResponse;
import com.test.test.rest.models.Enrollment;
import com.test.test.rest.models.EnrollmentInfo;
import com.test.test.rest.models.Interval;
import com.test.test.ui.interfaces.AuthCallback;
import com.test.test.ui.interfaces.EnrollCallback;
import com.test.test.ui.interfaces.EnrollInitCallback;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
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
    String mGender;

    EnrollmentHelper enrollmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        inflateComponents();
        mPinString = "";
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

        mButtonStar = (Button)findViewById(R.id.buttonStar);
        mButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="*";
                mPinView.setText(mPinString);
                enroll(mPinString);
            }
        });

        mButtonHash = (Button)findViewById(R.id.buttonHash);
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
                    mMFSwitch.setText("F");
                    mGender = "F";
                }else{
                    mGender = "M";
                    mMFSwitch.setText("M");
                }
            }
        });
    }

    private boolean isConsecutive(String pin){
        if(pin.equalsIgnoreCase("1234")) return true;
        if(pin.equalsIgnoreCase("2345")) return true;
        if(pin.equalsIgnoreCase("3456")) return true;
        return false;
    }

    private void enroll(String key){
        if(key == null || key.isEmpty() || key.length() <= 3 ){
            return;
        }
        if(isConsecutive(key)){
            mPinView.setText("");
            mPinString = "";
            Toast.makeText(getBaseContext(),
                    "Pin must not be a sequence.", Toast.LENGTH_SHORT).show();
            return;
        }
        enrollmentHelper = new EnrollmentHelper(getBaseContext());
        AuthRequest authRequest = new AuthRequest(AudioPinApiHelper.USER, AudioPinApiHelper.PASS);
        String name = UUID.randomUUID().toString();
        final EnrollmentInfo enrollmentInfo = new EnrollmentInfo(mGender, name, key, "", false);
        enrollmentHelper.fetchAuthToken(authRequest, new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                Toast.makeText(getBaseContext(), "Received authentication token",
                        Toast.LENGTH_SHORT).show();
                final String token = "Bearer " + response.jwt;
                enrollmentHelper.initializeEnrollmentInfo("Bearer " + response.jwt, enrollmentInfo,
                        new EnrollInitCallback() {
                    @Override
                    public void onSuccess(EnrollInitResponse response) {
                        Toast.makeText(getBaseContext(), "Enrollment initialized",
                                Toast.LENGTH_SHORT).show();
                        getIntervals(response.animation.enrollment, response.prompts);
                        animate(response, token, response.id);
                    }
                    @Override
                    public void onError(String error) {
                        mPinView.setText("");
                        mPinString = "";
                    }
                });
            }
            @Override
            public void onError(String error) {
                mPinView.setText("");
                mPinString = "";
            }
        });
    }

    private void animate(final EnrollInitResponse response,
                         final String token, final String clientId){
        final Enrollment [] enrollments = response.animation.enrollment;
        final Timer timer = new Timer();
        final WavAudioRecorder audioRecorder = new WavAudioRecorder(getBaseContext());
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
                        delay(1000);
                        audioRecorder.stopRecording();
                        delay(1000);
                        String intervalsStr =  getIntervals(response.animation.enrollment,
                                response.prompts);
                        enrollmentHelper.uploadEnrollmentAudio(token, clientId, intervalsStr,
                                new File(audioRecorder.getFilename()), new EnrollCallback() {
                                    @Override
                                    public void onSuccess(final String response) {
                                        Toast.makeText(getBaseContext(), response,
                                                Toast.LENGTH_LONG).show();
                                        mPinView.setText("");
                                        mPinString = "";
                                    }
                                    @Override
                                    public void onError(final String error) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getBaseContext(), error,
                                                        Toast.LENGTH_SHORT).show();
                                                mPinView.setText("");
                                                mPinString = "";
                                            }
                                        });
                                    }
                                });
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
                }
            }
        }, 0, 10);
    }

    private void delay(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getIntervals(Enrollment[] enrollments, String[] prompts){
        List<String> promptsList = Arrays.asList(prompts);
        Interval[] intervals = new Interval[36];
        int intervalCount = 0;
        long duration = 0;
        for(Enrollment enrollment: enrollments){
            String instruction = enrollment.instruction;
            Long animDuration = Long.parseLong(enrollment.duration);
            if(promptsList == null || promptsList.isEmpty()){
                return null;
            }
            if(instruction == null || instruction.isEmpty()){
                continue;
            }
            if(promptsList.contains(instruction)){
                Interval interval = new Interval(enrollment.instruction,
                        duration, duration + animDuration);
                intervals[intervalCount] = interval;
                intervalCount++;
            }
            duration += animDuration;
        }
        List<Interval> intervalList = Arrays.asList(intervals);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Interval>>(){}.getType();
        String intervalStr = gson.toJson(intervalList, type);
        return intervalStr;
    }
}
