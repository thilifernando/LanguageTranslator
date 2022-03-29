package com.usjdcs.languagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class Text_to_speech extends AppCompatActivity {

    private TextToSpeech text_to_speech;
    private EditText Edit_Text;
    private SeekBar SeekBar_Pitch;
    private SeekBar SeekBar_Speed;
    private Button Button_Speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        Button_Speak = findViewById(R.id.Button_speak);

        text_to_speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    int result = text_to_speech.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e ("TTS", "Language not supported");
                    }
                    else {
                        Button_Speak.setEnabled(true);
                    }
                    }                   else {
                        Log.e("TTS", "Initialization failed");
                    }
                }
        });

        Edit_Text = findViewById(R.id.edit_text);
        SeekBar_Pitch  = findViewById(R.id.seek_bar_pitch);
        SeekBar_Speed = findViewById(R.id.seek_bar_speed);

        Button_Speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    private void speak(){
        String text = Edit_Text.getText().toString();
        float pitch = SeekBar_Pitch.getProgress()/50;
        if (pitch<0.1)pitch = 0.1f;
        float speed = SeekBar_Speed.getProgress()/50;
        if (speed<0.1)speed = 0.1f;
        
        text_to_speech.setPitch(pitch);
        text_to_speech.setSpeechRate(speed);
        
        text_to_speech.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onStop() {
        if (text_to_speech != null){
            text_to_speech.stop();
            text_to_speech.shutdown();
        }
        super.onStop();
    }

}