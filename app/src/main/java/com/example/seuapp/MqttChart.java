package com.example.seuapp;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MqttChart {
    private LineChart chart;
    private int indiceGlobal = 0;
    private List<Entry> entries = new ArrayList<Entry>();

    MqttChart(LineChart chart) {
        this.chart = chart;
    }
    public int getIndiceGlobal() {
        return indiceGlobal;
    }

    public void setIndiceGlobal(int indiceGlobal) {
        this.indiceGlobal = indiceGlobal;
    }

    public void addEntry(float y){
        entries.add(new Entry(indiceGlobal++,y));
    }

    public void setChartData(){
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }


}
