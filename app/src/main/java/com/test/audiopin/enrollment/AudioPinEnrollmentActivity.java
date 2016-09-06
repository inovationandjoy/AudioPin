package com.test.audiopin.enrollment;

import android.content.Intent;
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
import com.test.audiopin.enrollment.interfaces.AuthCallback;
import com.test.audiopin.enrollment.interfaces.UploadAudioCallback;
import com.test.audiopin.enrollment.interfaces.EnrollInitCallback;
import com.test.audiopin.recorder.WavAudioRecorder;
import com.test.audiopin.rest.AudioPinApiHelper;
import com.test.audiopin.rest.models.enrollment.AuthRequest;
import com.test.audiopin.rest.models.enrollment.AuthResponse;
import com.test.audiopin.rest.models.enrollment.EnrollInitResponse;
import com.test.audiopin.rest.models.enrollment.Enrollment;
import com.test.audiopin.rest.models.enrollment.EnrollmentInfo;
import com.test.audiopin.rest.models.enrollment.Interval;
import com.test.audiopin.rest.models.verification.ClientInfoResponse;
import com.test.audiopin.ui.R;
import com.test.audiopin.verification.ClientInfoCallback;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class AudioPinEnrollmentActivity extends AppCompatActivity {

    private String mPinString;
    private TextView mPinView;
    private Switch mMFSwitch;
    private TextView mEditText;
    private Button mFinish;
    private String mGender;
    private String mToken;
    private String mClientId;
    private String mKey;
    private String mStatus;

    private EnrollmentHelper mEnrollmentHelper;
    private EnrollInitResponse mResponse;
    private WavAudioRecorder mAudioRecorder;

    public enum Operation {
        TOKEN,
        INIT_ENROLL,
        ANIMATE,
        UPLOAD,
        INFO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        inflateUIComponents();
        mPinString = "";
        mGender = "M";
        mEnrollmentHelper = new EnrollmentHelper(getBaseContext());
        mAudioRecorder = new WavAudioRecorder(getBaseContext(), "enrollment");
        mStatus = "";
    }

    /**
     * Inflate the widgets and listen events.
     */
    private void inflateUIComponents(){
        Button button0 = (Button) findViewById(R.id.button0);
        if(button0 != null) {
            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "0";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button1 = (Button) findViewById(R.id.button1);
        if(button1 != null) {
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "1";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button2 = (Button) findViewById(R.id.button2);
        if(button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "2";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button3 = (Button) findViewById(R.id.button3);
        if(button3 != null) {
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "3";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button4 = (Button) findViewById(R.id.button4);
        if(button4 != null) {
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "4";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button5 = (Button) findViewById(R.id.button5);
        if(button5 != null) {
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "5";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button6 = (Button) findViewById(R.id.button6);
        if(button6 != null) {
            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "6";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button7 = (Button) findViewById(R.id.button7);
        if(button7 != null) {
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "7";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button8 = (Button) findViewById(R.id.button8);
        if(button8 != null) {
            button8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "8";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button button9 = (Button) findViewById(R.id.button9);
        if(button9 != null) {
            button9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "9";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button buttonStar = (Button) findViewById(R.id.buttonStar);
        if(buttonStar != null) {
            buttonStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "*";
                    mPinView.setText(mPinString);
                }
            });
        }
        Button buttonHash = (Button) findViewById(R.id.buttonHash);
        if(buttonHash != null) {
            buttonHash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPinString += "#";
                    mPinView.setText(mPinString);
                }
            });
        }
        mFinish = (Button) findViewById(R.id.buttonFinish);
        if(mFinish != null) {
            mFinish.setText("Enroll");
            mFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFinish.getText().toString().equalsIgnoreCase("Enroll")){
                        if(mPinString.length() == 4) {
                            mFinish.setText("Finish");
                            mFinish.setEnabled(false);
                            enroll(mPinString);
                        }else {
                            Toast.makeText(getBaseContext(), "Key should be 4 chars long",
                                    Toast.LENGTH_LONG).show();
                            mPinView.setText("");
                            mPinString = "";
                        }
                    } else if(mFinish.getText().toString().equalsIgnoreCase("Finish")){
                        Intent data = new Intent();
                        data.putExtra("status", mStatus);
                        data.putExtra("clientId", mClientId);
                        setResult(RESULT_OK,data);
                        finish();
                    }
                }
            });
        }

        mPinView = (TextView)findViewById(R.id.pinView);
        mEditText = (TextView) findViewById(R.id.editText);
        mMFSwitch = (Switch) findViewById(R.id.switchMaleFemale);

        if(mMFSwitch != null) {
            mMFSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mMFSwitch.setText("F");
                        mGender = "F";
                    } else {
                        mGender = "M";
                        mMFSwitch.setText("M");
                    }
                }
            });
        }
    }

    private boolean isConsecutive(String pin){
        if(pin.equalsIgnoreCase("1234")) return true;
        if(pin.equalsIgnoreCase("2345")) return true;
        if(pin.equalsIgnoreCase("3456")) return true;
        if(pin.equalsIgnoreCase("1111")) return true;
        if(pin.equalsIgnoreCase("2222")) return true;
        if(pin.equalsIgnoreCase("3333")) return true;
        if(pin.equalsIgnoreCase("4444")) return true;
        if(pin.equalsIgnoreCase("5555")) return true;
        if(pin.equalsIgnoreCase("6666")) return true;
        if(pin.equalsIgnoreCase("7777")) return true;
        if(pin.equalsIgnoreCase("8888")) return true;
        if(pin.equalsIgnoreCase("9999")) return true;
        if(pin.equalsIgnoreCase("0000")) return true;
        if(pin.equalsIgnoreCase("****")) return true;
        if(pin.equalsIgnoreCase("####")) return true;
        return false;
    }

    /**
     * To handle different async tasks
     * @param operation
     */
    private void operate(Operation operation){
        switch(operation){
            case TOKEN:
                getAuthToken();
                break;
            case INIT_ENROLL:
                initEnrollment(mKey);
                break;
            case ANIMATE:
                animate(mResponse);
                break;
            case UPLOAD:
                String intervalsStr = getIntervals(mResponse.animation.enrollment,
                        mResponse.prompts);
                uploadEnrollmentAudio(intervalsStr);
                break;
            case INFO:
                delay(5000);
                getClientInfo();
                break;
        }
    }

    /**
     * Retrieve the authentication token
     */
    private void getAuthToken(){
        AuthRequest authRequest = new AuthRequest(AudioPinApiHelper.USER, AudioPinApiHelper.PASS);
        mEnrollmentHelper.fetchAuthToken(authRequest, new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                Toast.makeText(getBaseContext(), "Received authentication token",
                        Toast.LENGTH_SHORT).show();
                mToken = "Bearer " + response.jwt;
                operate(Operation.INIT_ENROLL);
            }
            @Override
            public void onError(String error) {
                mPinView.setText("");
                mPinString = "";
            }
        });
    }

    /**
     * Initializes enrollment by creating client and returning related information.
     * @param key pin string
     */
    private void initEnrollment(String key){
        String name = UUID.randomUUID().toString();
        EnrollmentInfo enrollmentInfo = new EnrollmentInfo(mGender, name, key, "", false);
        mEnrollmentHelper.initEnrollment(mToken, enrollmentInfo,
                new EnrollInitCallback() {
                    @Override
                    public void onSuccess(EnrollInitResponse response) {
                        Toast.makeText(getBaseContext(), "Enrollment initialized",
                                Toast.LENGTH_SHORT).show();
                        getIntervals(response.animation.enrollment, response.prompts);
                        mClientId = response.id;
                        mResponse = response;
                        operate(Operation.ANIMATE);
                    }
                    @Override
                    public void onError(String error) {
                        mPinView.setText("");
                        mPinString = "";
                    }
                });
    }

    /**
     * Animate the phrases based on the info from server
     * @param response
     */
    private void animate(final EnrollInitResponse response){
        final Enrollment [] enrollments = response.animation.enrollment;
        final Timer timer = new Timer();
        mAudioRecorder.startRecording();
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
                        mAudioRecorder.stopRecording();
                        delay(1000);
                        operate(Operation.UPLOAD);
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

    /**
     * Uploads enrollment audio
     * @param intervalsStr string containing interval information
     */
    private void uploadEnrollmentAudio(String intervalsStr){
        mEnrollmentHelper.uploadEnrollmentAudio(mToken, mClientId, intervalsStr,
                new File(mAudioRecorder.getFilename()), new UploadAudioCallback() {
                    @Override
                    public void onSuccess(final String response) {
                        Toast.makeText(getBaseContext(), response,
                                Toast.LENGTH_LONG).show();
                        mPinView.setText("");
                        mPinString = "";
                        operate(Operation.INFO);
                    }
                    @Override
                    public void onError(final String error) {
                        Toast.makeText(getBaseContext(), error,
                                Toast.LENGTH_SHORT).show();
                        mPinView.setText("");
                        mPinString = "";
                    }
                });
    }

    /**
     * Retrieve the client info to know enrollment status
     */
    private void getClientInfo(){
        mEnrollmentHelper.fetchClientInfo(mToken, mClientId, new ClientInfoCallback() {
            @Override
            public void onSuccess(ClientInfoResponse response) {
                mStatus = response.status;
                Toast.makeText(getBaseContext(), mStatus, Toast.LENGTH_LONG).show();
                mFinish.setEnabled(true);
            }
            @Override
            public void onError(String error) {

            }
        });
    }


    private void enroll(String key){
        if(isConsecutive(key)){
            mPinView.setText("");
            mPinString = "";
            Toast.makeText(getBaseContext(),
                    "Pin must not be a sequence.", Toast.LENGTH_SHORT).show();
            return;
        }
        mKey = key;
        operate(Operation.TOKEN);
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
            if(promptsList.isEmpty()){
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
        return gson.toJson(intervalList, type);
    }
}
