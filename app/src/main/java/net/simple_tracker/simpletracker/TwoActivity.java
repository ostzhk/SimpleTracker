package net.simple_tracker.simpletracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TwoActivity extends AppCompatActivity {


    DBHandler dbHandler;
    List<Category> categories = null;
    List<Outlay> outlays = null;
    LinearLayout twoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        twoLayout = (LinearLayout)findViewById(R.id.twoLayout);

        dbHandler = new DBHandler(this);
        showOutlays();
    }

    private void showOutlays(){
        categories = dbHandler.getAllCategories();

        for (Category c:categories) {
            outlays = dbHandler.getAllOutlaysByCategory(c.getCategoryName());
            if (outlays.size()!=0){
                TextView textView1 = new TextView(this);
                textView1.setText(c.getCategoryName());
                twoLayout.addView(textView1);
            }

            for (Outlay o:outlays){
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(o.getDate() + ": " + o.getCount()));
                twoLayout.addView(textView);
            }
        }
    }
}
