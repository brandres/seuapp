package com.example.seuapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ManualFragment extends Fragment implements OnClickListener{
    private EditText fld_topic;
    private TextView fld_message;
    private EditText fld_url;
    private Button btn_sub;
    private Button btn_unsub;
    private MqttController broker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View w = inflater.inflate(R.layout.fragment_manual, container, false);
        fld_topic = w.findViewById(R.id.fld_topic);
        fld_url = w.findViewById(R.id.fld_url);
        fld_message = w.findViewById(R.id.fld_message);
        btn_sub = w.findViewById(R.id.btn_sub);
        btn_unsub = w.findViewById(R.id.btn_unsub);
        Button btn_conectar = w.findViewById(R.id.btn_conectar);
        btn_sub.setEnabled(false);
        btn_unsub.setEnabled(false);
        btn_sub.setOnClickListener(this);
        btn_conectar.setOnClickListener(this);
        btn_unsub.setOnClickListener(this);
        return w;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conectar:
                connectMqttBroker();
                break;
            case R.id.btn_sub:
                subscribe(v);
                break;
            case R.id.btn_unsub:
                unsubscribe();
                break;
        }
    }

    private void subscribe(View v) {
        String topic = fld_topic.getText().toString();
        Toast.makeText(v.getContext(), topic, Toast.LENGTH_SHORT).show();
        broker.subscribeTo(topic);
        btn_unsub.setEnabled(true);
    }

    private void connectMqttBroker() {
        String url = fld_url.getText().toString();
        broker = new MqttController(getContext(), url, "clientid",getCallback());
        broker.connectMqtt();
        btn_sub.setEnabled(true);

    }

    private void unsubscribe() {
        String topic = fld_topic.getText().toString();
        broker.unsubscribe(topic);
        btn_unsub.setEnabled(false);
    }

    private MqttCallbackExtended getCallback() {
        return new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage){
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
