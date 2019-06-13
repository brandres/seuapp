package com.example.seuapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.eclipse.paho.android.service.MqttAndroidClient;

public class MainActivity extends AppCompatActivity {
    EditText fld_topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fld_topic = findViewById(R.id.fld_topic);
    }
    public void subscribe(View v) throws Exception{
        String topic = fld_topic.getText().toString();
        Toast.makeText(MainActivity.this,topic,Toast.LENGTH_SHORT).show();
    }

}
