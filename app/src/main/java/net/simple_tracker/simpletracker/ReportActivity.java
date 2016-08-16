package net.simple_tracker.simpletracker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
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

public class ReportActivity extends AppCompatActivity implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener {
    Map<String, Outlay> outlayList;
    PieChart pieChart;
    DBHandler dbHandler;
    String date;
    String date2;
    int finalSum;
    Calendar calendar;
    private int day, month, year;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    CalendarDatePickerDialogFragment cdp;
    TextView textView, textView2;
    boolean isFirstDate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = (calendar.get(Calendar.MONTH)+1);
        year = calendar.get(Calendar.YEAR);
        cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView.setText(year + "-" + month + "-" + day);
        textView.setOnClickListener(this);
        textView2.setText(year + "-" + (month+1) + "-" + day);
        textView2.setOnClickListener(this);

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
//        date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
//        date2 = String.valueOf(year) + "-" + (month + 1) + "-" + day;
        outlayList = dbHandler.getSumOfCategory(textView.getText().toString(), textView2.getText().toString());
        setData();
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.textView){
            isFirstDate = true;
        }else if (v.getId()==R.id.textView2){
            isFirstDate = false;
        }
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        if (isFirstDate){
            textView.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            showReport();
        }else {
            textView2.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            showReport();
        }
    }
}
