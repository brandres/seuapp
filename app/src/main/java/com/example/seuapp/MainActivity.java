package com.example.seuapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    EditText fld_topic;
    TextView fld_message;
    EditText fld_url;
    Button btn_sub;
    Button btn_unsub;
    MqttController broker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fld_topic = findViewById(R.id.fld_topic);
        fld_url = findViewById(R.id.fld_url);
        fld_message = findViewById(R.id.fld_message);
        btn_sub = findViewById(R.id.btn_sub);
        btn_unsub = findViewById(R.id.btn_unsub);
        btn_sub.setEnabled(false);
        btn_unsub.setEnabled(false);
    }

    public void subscribe(View v) {
        String topic = fld_topic.getText().toString();
        Toast.makeText(MainActivity.this, topic, Toast.LENGTH_SHORT).show();
        broker.subscribeTo(topic);
        btn_unsub.setEnabled(true);
    }

    public void connectMqttBroker(View v) {
        String url = fld_url.getText().toString();
        broker = new MqttController(getApplicationContext(), url, "clientid",getCallback());
        broker.connectMqtt();
        btn_sub.setEnabled(true);

    }

    public void unsubscribe(View v) {
        String topic = fld_topic.getText().toString();
        broker.unsubscribe(topic);
        btn_unsub.setEnabled(false);
    }

    public MqttCallbackExtended getCallback() {
        return new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
                Log.w("Mqtt", topic);
                fld_message.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
    }

}
