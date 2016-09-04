package com.test.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.test.rest.models.verification.ClientInfoResponse;
import com.test.test.rest.models.verification.StartVerificationRequest;
import com.test.test.rest.models.verification.StartVerificationResponse;
import com.test.test.ui.interfaces.ClientInfoCallback;
import com.test.test.ui.interfaces.StartVerificationCallback;

/**
 * Created by shahed on 03/09/2016.
 */
public class AudioPinVerificationActivity extends AppCompatActivity {

    private String mPinString;
    private TextView mDisplay;

    private String mClientId;
    private String mToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        inflateComponents();
        mPinString = "";


        verify();
    }

    private void inflateComponents(){
        Button mButton0;
        Button mButton1;
        Button mButton2;
        Button mButton3;
        Button mButton4;
        Button mButton5;
        Button mButton6;
        Button mButton7;
        Button mButton8;
        Button mButton9;
        Button mButtonStar;
        Button mButtonHash;

        mButton0 = (Button)findViewById(R.id.verification_button0);
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="0";
                verify(mPinString);
            }
        });
        mButton1 = (Button)findViewById(R.id.verification_button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="1";
                verify(mPinString);
            }
        });
        mButton2 = (Button)findViewById(R.id.verification_button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="2";
                verify(mPinString);
            }
        });
        mButton3 = (Button)findViewById(R.id.verification_button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="3";
                verify(mPinString);
            }
        });
        mButton4 = (Button)findViewById(R.id.verification_button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="4";
                verify(mPinString);
            }
        });
        mButton5 = (Button)findViewById(R.id.verification_button5);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="5";
                verify(mPinString);
            }
        });
        mButton6 = (Button)findViewById(R.id.verification_button6);
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="6";
                verify(mPinString);
            }
        });
        mButton7 = (Button)findViewById(R.id.verification_button7);
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="7";
                verify(mPinString);
            }
        });
        mButton8 = (Button)findViewById(R.id.verification_button8);
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="8";
                verify(mPinString);
            }
        });
        mButton9 = (Button)findViewById(R.id.verification_button9);
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="9";
                verify(mPinString);
            }
        });

        mButtonStar = (Button)findViewById(R.id.verification_button_star);
        mButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="*";
                verify(mPinString);
            }
        });

        mButtonHash = (Button)findViewById(R.id.verification_button_hash);
        mButtonHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPinString+="#";
                verify(mPinString);
            }
        });

        mDisplay = (TextView) findViewById(R.id.verification_display);


        Button finish = (Button) findViewById(R.id.verification_button_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = getIntent().getExtras();
//                String mToken = bundle.getString("token");
//                String mClientId = bundle.getString("clientId");
//                new VerificationHelper(getBaseContext()).fetchClientInfo(mToken, mClientId, null );
            }
        });
    }

    private void verify(){
        Bundle bundle = getIntent().getExtras();
        final String mToken = bundle.getString("token");
        final String mClientId = bundle.getString("clientId");

        final VerificationHelper helper = new VerificationHelper(getBaseContext());

        helper.fetchClientInfo(mToken, mClientId, new ClientInfoCallback() {
            @Override
            public void onSuccess(ClientInfoResponse response) {
                String status = response.status;
                String verification_id = response.id;
                String verification_name = response.name;


                Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();


                StartVerificationRequest req = new StartVerificationRequest("audiopin", false, 75,
                        "webapp", "c6ad29bf-9d30-4c5f-b256-8ca7327a3c23", "1234567890", false);

                helper.startVerification(mToken, req, new StartVerificationCallback() {
                    @Override
                    public void onSuccess(StartVerificationResponse response) {
                        StartVerificationResponse rs = response;
                    }

                    @Override
                    public void onError(String error) {
                        int ii = 19;

                    }
                });





            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void verify(String input){
        String pin = input;
    }

}
