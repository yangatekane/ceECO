/*
 * Copyright 2012 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.htm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.androidplot.Plot;
import com.androidplot.util.PlotStatistics;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.util.Arrays;


public class GraphsActivity extends Activity implements SensorEventListener
{

    private static final int HISTORY_SIZE = 300;
    private SensorManager sensorMgr = null;
    private Sensor orSensor = null;

    private XYPlot aprLevelsPlot = null;
    private XYPlot aprHistoryPlot = null;

    private CheckBox hwAcceleratedCb;
    private CheckBox showFpsCb;
    private SimpleXYSeries aLvlSeries;
    private SimpleXYSeries pLvlSeries;
    private SimpleXYSeries rLvlSeries;

    private SimpleXYSeries azimuthHistorySeries = null;
    private SimpleXYSeries pitchHistorySeries = null;
    private SimpleXYSeries rollHistorySeries = null;

    private SimpleXYSeries xAccSeries = null;
    private SimpleXYSeries yAccSeries = null;
    private SimpleXYSeries zAccSeries = null;

    private SimpleXYSeries xFusedSeries = null;
    private SimpleXYSeries yFusedSeries = null;
    private SimpleXYSeries zFusedSeries = null;

    private Redrawer redrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_activity);

        // setup the APR Levels plot:
        aprLevelsPlot = (XYPlot) findViewById(R.id.aprLevelsPlot);
        aprLevelsPlot.setDomainBoundaries(-1, 1, BoundaryMode.FIXED);
        aprLevelsPlot.getGraphWidget().getDomainLabelPaint().setColor(Color.TRANSPARENT);

        aLvlSeries = new SimpleXYSeries("A");
        pLvlSeries = new SimpleXYSeries("P");
        rLvlSeries = new SimpleXYSeries("R");

        aprLevelsPlot.addSeries(aLvlSeries,
                new BarFormatter(Color.rgb(0, 200, 0), Color.rgb(0, 80, 0)));
        aprLevelsPlot.addSeries(pLvlSeries,
                new BarFormatter(Color.rgb(200, 0, 0), Color.rgb(0, 80, 0)));
        aprLevelsPlot.addSeries(rLvlSeries,
                new BarFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 80, 0)));

        aprLevelsPlot.setDomainStepValue(3);
        aprLevelsPlot.setTicksPerRangeLabel(3);

        aprLevelsPlot.setRangeBoundaries(-180, 359, BoundaryMode.FIXED);

        aprLevelsPlot.setDomainLabel("");
        aprLevelsPlot.getDomainLabelWidget().pack();
        aprLevelsPlot.setRangeLabel("Angle (Degs)");
        aprLevelsPlot.getRangeLabelWidget().pack();
        aprLevelsPlot.setGridPadding(15, 0, 15, 0);
        aprLevelsPlot.setRangeValueFormat(new DecimalFormat("#"));

        aprHistoryPlot = (XYPlot) findViewById(R.id.aprHistoryPlot);

        azimuthHistorySeries = new SimpleXYSeries("Az.");
        azimuthHistorySeries.useImplicitXVals();
        pitchHistorySeries = new SimpleXYSeries("Pitch");
        pitchHistorySeries.useImplicitXVals();
        rollHistorySeries = new SimpleXYSeries("Roll");
        rollHistorySeries.useImplicitXVals();

        aprHistoryPlot.setRangeBoundaries(-180, 359, BoundaryMode.FIXED);
        aprHistoryPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
        aprHistoryPlot.addSeries(azimuthHistorySeries,
                new LineAndPointFormatter(
                        Color.rgb(100, 100, 200), null, null, null));
        aprHistoryPlot.addSeries(pitchHistorySeries,
                new LineAndPointFormatter(
                        Color.rgb(100, 200, 100), null, null, null));
        aprHistoryPlot.addSeries(rollHistorySeries,
                new LineAndPointFormatter(
                        Color.rgb(200, 100, 100), null, null, null));
        aprHistoryPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
        aprHistoryPlot.setDomainStepValue(HISTORY_SIZE/10);
        aprHistoryPlot.setTicksPerRangeLabel(3);
        aprHistoryPlot.setDomainLabel("Sample Index");
        aprHistoryPlot.getDomainLabelWidget().pack();
        aprHistoryPlot.setRangeLabel("Angle (Degs)");
        aprHistoryPlot.getRangeLabelWidget().pack();

        aprHistoryPlot.setRangeValueFormat(new DecimalFormat("#"));
        aprHistoryPlot.setDomainValueFormat(new DecimalFormat("#"));

        hwAcceleratedCb = (CheckBox) findViewById(R.id.hwAccelerationCb);
        final PlotStatistics levelStats = new PlotStatistics(1000, false);
        final PlotStatistics histStats = new PlotStatistics(1000, false);

        aprLevelsPlot.addListener(levelStats);
        aprHistoryPlot.addListener(histStats);
        hwAcceleratedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    aprLevelsPlot.setLayerType(View.LAYER_TYPE_NONE, null);
                    aprHistoryPlot.setLayerType(View.LAYER_TYPE_NONE, null);
                } else {
                    aprLevelsPlot.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    aprHistoryPlot.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
            }
        });

        showFpsCb = (CheckBox) findViewById(R.id.showFpsCb);
        showFpsCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                levelStats.setAnnotatePlotEnabled(b);
                histStats.setAnnotatePlotEnabled(b);
            }
        });

        BarRenderer barRenderer = (BarRenderer) aprLevelsPlot.getRenderer(BarRenderer.class);
        if(barRenderer != null) {

            barRenderer.setBarWidth(25);
        }

        sensorMgr = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        for (Sensor sensor : sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION)) {
            if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
                orSensor = sensor;
            }
        }

        if (orSensor == null) {
            System.out.println("Failed to attach to orSensor.");
            cleanup();
        }

        sensorMgr.registerListener(this, orSensor, SensorManager.SENSOR_DELAY_UI);

        redrawer = new Redrawer(
                Arrays.asList(new Plot[]{aprHistoryPlot, aprLevelsPlot}),
                100, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        redrawer.start();
    }

    @Override
    public void onPause() {
        redrawer.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        redrawer.finish();
        super.onDestroy();
    }

    private void cleanup() {
        sensorMgr.unregisterListener(this);
        finish();
    }


    @Override
    public synchronized void onSensorChanged(SensorEvent sensorEvent) {

        aLvlSeries.setModel(Arrays.asList(
                new Number[]{sensorEvent.values[0]}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

        pLvlSeries.setModel(Arrays.asList(
                new Number[]{sensorEvent.values[1]}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

        rLvlSeries.setModel(Arrays.asList(
                new Number[]{sensorEvent.values[2]}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

        if (rollHistorySeries.size() > HISTORY_SIZE) {
            rollHistorySeries.removeFirst();
            pitchHistorySeries.removeFirst();
            azimuthHistorySeries.removeFirst();
        }

        azimuthHistorySeries.addLast(null, sensorEvent.values[0]);
        pitchHistorySeries.addLast(null, sensorEvent.values[1]);
        rollHistorySeries.addLast(null, sensorEvent.values[2]);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}