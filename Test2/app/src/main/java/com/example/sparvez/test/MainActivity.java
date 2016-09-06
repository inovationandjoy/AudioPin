package com.example.sparvez.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.audiopin.enrollment.AudioPinEnrollmentActivity;
import com.test.audiopin.verification.AudioPinVerificationActivity;

public class MainActivity extends AppCompatActivity {
    public static final int ENROLLMENT = 1;
    public static final int VERIFICATION = 2;

    private String mClientId;
    private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enrollment = (Button) findViewById(R.id.button_enrollment);
        if(enrollment != null)
        enrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AudioPinEnrollmentActivity.class);
                        startActivityForResult(intent, ENROLLMENT);
            }
        });

        Button verification = (Button) findViewById(R.id.button_verification);
        if(verification != null)
            verification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mClientId = "b3df334c5588a375ae5327e4d0a81f1b";
                    mStatus = "enrolled";


                    if(mClientId == null || mClientId.isEmpty() || !mStatus.equalsIgnoreCase("enrolled")){
                        Toast.makeText(getBaseContext(), "Enroll first", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, AudioPinVerificationActivity.class);
                    intent.putExtra("clientId", mClientId);
                    startActivityForResult(intent, VERIFICATION);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENROLLMENT) {
            if (resultCode == RESULT_OK) {
                Bundle result = data.getExtras();
                mClientId = result.getString("clientId");
                mStatus = result.getString("status");
            }
        }else if (requestCode == VERIFICATION) {
            if (resultCode == RESULT_OK) {
                Bundle result = data.getExtras();
                String status = result.getString("status");
                if(status.equalsIgnoreCase("**Authorized**")){
                    Toast.makeText(getBaseContext(), "**Authorized**", Toast.LENGTH_LONG).show();
                }

            }
        }



    }
}
