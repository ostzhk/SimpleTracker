package net.simple_tracker.simpletracker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ReportActivity extends Activity implements View.OnClickListener {
    Map<String, Outlay> outlayList;
    PieChart pieChart;
    DBHandler dbHandler;
    String date;
    String date2;
    int finalSum;
    Button button;
    Calendar calendar;
    private int year, month, day;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        textView = (TextView)findViewById(R.id.textView);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        dbHandler = new DBHandler(this);
        showReport();


        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        pieChart.setEntryLabelColor(Color.DKGRAY);
        pieChart.setEntryLabelTextSize(18f);
        pieChart.animateXY(2000, 2000);


    }

    private void setData() {


        ArrayList<PieEntry> entries = new ArrayList<>();


        for (Map.Entry<String, Outlay> entry : outlayList.entrySet()) {
            entries.add(new PieEntry((float) entry.getValue().getCount(),
                    entry.getKey() + " - " + entry.getValue().getCount() + " руб."));
            finalSum += entry.getValue().getCount();
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setDrawValues(false);
        pieChart.setDrawSliceText(false);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setData(data);
        pieChart.setCenterText("Итого: - " + finalSum + " руб.");
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void showReport() {
        finalSum = 0;

        switch (getIntent().getExtras().getInt("date")){
            case R.id.nav_day:
                date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
                date2 = String.valueOf(year) + "-" + (month + 1) + "-" + day;
                break;
            case R.id.nav_week:
                date = String.valueOf(year) + "-" + (month + 1) + "-" + (day-7);
                date2 = String.valueOf(year) + "-" + (month + 1) + "-" + day;
                break;
            case R.id.nav_month:
                date = String.valueOf(year) + "-" + (month + 1) + "-" + 1;
                date2 = String.valueOf(year) + "-" + (month + 1) + "-" + day;
                break;
            case R.id.nav_year:
                date = String.valueOf(year - 1) + "-" + (month + 1) + "-" + 1;
                date2 = String.valueOf(year) + "-" + (month + 1) + "-" + 1;
                break;
        }

        textView.setText(date + " - " + date2);
        outlayList = dbHandler.getSumOfCategory(date, date2);
        setData();
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
    }

    @Override
    public void onClick(View v) {
        showReport();
    }
}
