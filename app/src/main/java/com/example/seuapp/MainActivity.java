package com.example.seuapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText fld_topic;
    EditText fld_url;
    Button btn_sub;
    MqttController broker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fld_topic = findViewById(R.id.fld_topic);
        fld_url = findViewById(R.id.fld_url);
        btn_sub = findViewById(R.id.btn_sub);
        btn_sub.setEnabled(false);
    }
    public void subscribe(View v){
        String topic = fld_topic.getText().toString();
        Toast.makeText(MainActivity.this,topic,Toast.LENGTH_SHORT).show();
        broker.subscribeTo(topic);
    }
    public void connectMqttBroker(View v){
        String url = fld_url.getText().toString();
        broker = new MqttController(getApplicationContext(),url,"clientid");
        broker.connectMqtt();
        btn_sub.setEnabled(true);
    }

    public void unsubscribe(View v){
        String topic = fld_topic.getText().toString();
        broker.unsubscribe(topic);
    }

}
