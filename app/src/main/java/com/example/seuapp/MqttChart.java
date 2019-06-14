package com.example.seuapp;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MqttChart {
    private LineChart chart;
    public int indiceGlobal = 0;
    public List<Entry> entries = new ArrayList<Entry>();

    public MqttChart(LineChart chart) {
        this.chart = chart;
    }
}
