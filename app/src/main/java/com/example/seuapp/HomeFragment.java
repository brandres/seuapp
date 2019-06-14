package com.example.seuapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private MqttChart tempChart;
    private MqttChart potenChart;
    private MqttChart luzChart;
    private static final String URL = "tcp://fmle.ddns.net:1883";
    private MqttController broker;
    private static final String TEMP_TOPIC = "wemos/temperatura";
    private static final String POTEN_TOPIC = "wemos/potenciometro";
    private static final String LUZ_TOPIC = "wemos/luz";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View w = inflater.inflate(R.layout.fragment_home, container, false);
        tempChart = new MqttChart((LineChart)w.findViewById(R.id.temp_chart));
        potenChart = new MqttChart((LineChart)w.findViewById(R.id.poten_chart));
        luzChart = new MqttChart((LineChart)w.findViewById(R.id.luz_chart));
        connectMqttBroker();
        subscribeAll();
        entries.add(new Entry(indiceGlobal,));
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        tempChart.setData(lineData);
        tempChart.invalidate();
        return w;
    }

    public void connectMqttBroker() {
        broker = new MqttController(getContext(), URL, "clientid",getCallback());
        broker.connectMqtt();
    }
    public void subscribeAll(){
        broker.subscribeTo(TEMP_TOPIC);
        broker.subscribeTo(POTEN_TOPIC);
        broker.subscribeTo(LUZ_TOPIC);
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
                switch (topic){
                    case TEMP_TOPIC:
                        tempChart.entries.add(new Entry(tempChart.indiceGlobal++, Float.parseFloat(mqttMessage.toString())));
                        break;
                    case POTEN_TOPIC:
                        potenChart.entries.add(new Entry(potenChart.indiceGlobal++, Float.parseFloat(mqttMessage.toString())));
                        break;
                    case LUZ_TOPIC:
                        luzChart.entries.add(new Entry(luzChart.indiceGlobal++, Float.parseFloat(mqttMessage.toString())));
                        break;
                    default: break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
    }
}
