package net.simple_tracker.simpletracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OneActivity extends AppCompatActivity {


    DBHandler dbHandler;
    ArrayList<String> dates = null;
    List<Outlay> outlays = null;
    LinearLayout oneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        oneLayout = (LinearLayout)findViewById(R.id.oneLayout);

        dbHandler = new DBHandler(this);
        showOutlays();
    }

    private void showOutlays(){
        dates = dbHandler.getDates();

        for (String d:dates) {
            outlays = dbHandler.getAllOutlaysByDate(d);
            TextView textView1 = new TextView(this);
            textView1.setText(d);
            oneLayout.addView(textView1);

            for (Outlay o:outlays){
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(o.getCategoryName() + ": " + o.getCount()));
                oneLayout.addView(textView);
            }
        }
    }
}
