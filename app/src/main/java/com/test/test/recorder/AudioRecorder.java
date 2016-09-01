package com.test.test.recorder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by sparvez on 2016-09-01.
 */
public class AudioRecorder {


    private MediaRecorder mRecorder;
    private Context mContext;

    private AudioManager.OnAudioFocusChangeListener audioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {

        }
    };

    public AudioRecorder(Context context){
        this.mContext = context;
    }

    public void startRecording() {
        requestAudioFocus();
        mRecorder = new MediaRecorder();
        File file = getTempFile(mContext);
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(file.getAbsolutePath());

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //startTime = System.currentTimeMillis();
        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public static File getTempFile(@NonNull Context context) {
        //return new File(Environment.getExternalStorageDirectory(), "kkk.aac");
        return new File(context.getCacheDir(), "enrollment.aac");
    }

    private boolean requestAudioFocus() {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int duration = (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) ?
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT :
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.requestAudioFocus(audioFocusListener, AudioManager.STREAM_MUSIC | AudioManager.STREAM_ALARM,
                        duration);
    }

}
