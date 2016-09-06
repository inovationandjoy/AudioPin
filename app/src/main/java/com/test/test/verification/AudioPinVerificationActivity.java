package com.test.test.verification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.test.enrollment.interfaces.AuthCallback;
import com.test.test.recorder.WavAudioRecorder;
import com.test.test.rest.models.enrollment.AuthResponse;
import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.rest.models.verification.StartVerificationRequest;
import com.test.test.rest.models.verification.StartVerificationResponse;
import com.test.test.ui.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shahed on 03/09/2016.
 */
public class AudioPinVerificationActivity extends AppCompatActivity {

    private String mPinString;
    private TextView mDisplay;

    private String mClientId;
    private String mToken;

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
    private Button mButtonfinish;

    private VerificationHelper verificationHelper;

    private WavAudioRecorder audioRecorder;
    private String mVerificationResourceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        inflateComponents();
        mPinString = "";

        Bundle bundle = getIntent().getExtras();
        //mToken = bundle.getString("token");
        //mClientId = bundle.getString("clientId");

        mClientId = "b3df334c5588a375ae5327e4d0a81f1b";
        //verify();
        mDisplay.setText("Verification");
        checkVerification();
    }

    private void inflateComponents(){

        mButton0 = (Button)findViewById(R.id.verification_button0);
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="0";
            }
        });
        mButton1 = (Button)findViewById(R.id.verification_button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="1";
            }
        });
        mButton2 = (Button)findViewById(R.id.verification_button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="2";
            }
        });
        mButton3 = (Button)findViewById(R.id.verification_button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="3";
            }
        });
        mButton4 = (Button)findViewById(R.id.verification_button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="4";
            }
        });
        mButton5 = (Button)findViewById(R.id.verification_button5);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="5";
            }
        });
        mButton6 = (Button)findViewById(R.id.verification_button6);
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="6";
            }
        });
        mButton7 = (Button)findViewById(R.id.verification_button7);
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="7";
            }
        });
        mButton8 = (Button)findViewById(R.id.verification_button8);
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="8";
            }
        });
        mButton9 = (Button)findViewById(R.id.verification_button9);
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="9";
            }
        });
        mButtonStar = (Button)findViewById(R.id.verification_button_star);
        mButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="*";
            }
        });
        mButtonHash = (Button)findViewById(R.id.verification_button_hash);
        mButtonHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="#";
            }
        });
        mDisplay = (TextView) findViewById(R.id.verification_display);
        mButtonfinish = (Button) findViewById(R.id.verification_button_finish);
        mButtonfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mButtonfinish.getText().toString().equalsIgnoreCase("Try again")){
                    mButtonfinish.setText("Finish");
                    checkVerification();
                }else
                    finish();
            }
        });
    }

    private void verify(){
        delay(1000);

        verificationHelper = new VerificationHelper(getBaseContext());

        verificationHelper.fetchClientInfo(mToken, mClientId, new ClientInfoCallback() {
            @Override
            public void onSuccess(ClientInfoResponse response) {
                String status = response.status;
                String client_id = response.id;
                String client_name = response.name;

                Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();

                StartVerificationRequest verificationRequest =
                        new StartVerificationRequest("audiopin", false, 75,
                        "webapp", mClientId, "1234567890", false);

                verificationHelper.startVerification(mToken, verificationRequest,
                        new StartVerificationCallback() {
                    @Override
                    public void onSuccess(StartVerificationResponse response) {
                        Toast.makeText(getBaseContext(), "Verification started",
                                Toast.LENGTH_SHORT).show();
                         animate(response);
                    }
                    @Override
                    public void onError(String error) {
                        Toast.makeText(getBaseContext(), "Error to start verification ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void checkVerification() {
        verificationHelper = new VerificationHelper(getBaseContext());

        verificationHelper.getAT(new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                String token = "Bearer " + response.jwt;
                mToken = token;
                verify();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void animate(final StartVerificationResponse response){
        mVerificationResourceId = response.resource;
        audioRecorder = new WavAudioRecorder(getBaseContext(), "verification");
        audioRecorder.startRecording();

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            long startTime = System.currentTimeMillis();
            int count = 0;
            int duration = response.animation[count].duration;
            int elaspeTime = 0;
            List<Integer> boundaries = new ArrayList<>();

            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime > duration) {
                    if(response.animation[count].is_word_boundary.equalsIgnoreCase("true")){
                        boundaries.add(elaspeTime);
                    }
                    elaspeTime += duration;
                    count++;
                    if(count >= response.animation.length){
                        timer.cancel();
                        delay(1000);
                        audioRecorder.stopRecording();
                        delay(1000);
                        String boundariesStr = new Gson().toJson(boundaries);
//                        verificationHelper.uploadVerificationAudio(mToken, mVerificationResourceId,
//                                boundariesStr,
//                                new File(audioRecorder.getFilename()));
                        uploadVerificationAudio(boundariesStr);
                        return;
                    }
                    duration = response.animation[count].duration;
                    final Object pin_mapping = response.animation[count].pin_mapping;
                    startTime = System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showPinMapping(pin_mapping);
                        }
                    });
                }
            }
        }, 0, 10);
    }

    private void uploadVerificationAudio(String boundariesStr){
        verificationHelper.uploadVerificationAudio(mToken, mVerificationResourceId,
                boundariesStr,
                new File(audioRecorder.getFilename()), new AudioUploadCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();

                        delay(5000);

                        isAuthenticate();
                    }
                    @Override
                    public void onError(String error) {
                        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void isAuthenticate(){
        verificationHelper.getVerificationInfo(mToken, mVerificationResourceId, new VerificationInfoCallback() {
            @Override
            public void onSuccess(String response) {
                mDisplay.setText(response);

                if(response.equalsIgnoreCase("--Unauthorized--")){
                    mButtonfinish.setText("Try again");
                }else{
                    mButtonfinish.setText("Finish");
                }
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    private void delay(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showPinMapping(Object pinMap){
        Map<Integer, String> map = (Map)pinMap;
        mButton0.setText("0" + "\n" + map.get("0"));
        mButton1.setText("1" + "\n" + map.get("1"));
        mButton2.setText("2" + "\n" + map.get("2"));
        mButton3.setText("3" + "\n" + map.get("3"));
        mButton4.setText("4" + "\n" + map.get("4"));
        mButton5.setText("5" + "\n" + map.get("5"));
        mButton6.setText("6" + "\n" + map.get("6"));
        mButton7.setText("7" + "\n" + map.get("7"));
        mButton8.setText("8" + "\n" + map.get("8"));
        mButton9.setText("9" + "\n" + map.get("9"));
        mButtonStar.setText("*" + "\n" + map.get("*"));
        mButtonHash.setText("#" + "\n" + map.get("#"));
    }

}
