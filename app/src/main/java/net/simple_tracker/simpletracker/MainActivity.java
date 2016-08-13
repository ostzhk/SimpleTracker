package net.simple_tracker.simpletracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText editText;
    Button button12;
    Button button14;
    Button button15;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);

        //Get all views
        editText = (EditText)findViewById(R.id.editText);
        TextView dateText = (TextView) findViewById(R.id.dateText);
        Button buttonPoint = (Button)findViewById(R.id.buttonPoint);
        ImageButton buttonDelete = (ImageButton)findViewById(R.id.deleteButton);
        Button historyButton = (Button)findViewById(R.id.historyBtn);
        Button button0 = (Button)findViewById(R.id.button0);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);


        //Test View
        button12 = (Button)findViewById(R.id.button12);
        button14 = (Button)findViewById(R.id.button14);
        button15 = (Button)findViewById(R.id.button15);


        buttonPoint.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        historyButton.setOnClickListener(this);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        button12.setOnClickListener(this);
        button14.setOnClickListener(this);
        button15.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_day: {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("date", R.id.nav_day);
                startActivity(intent);
                break;
            }
            case R.id.nav_week: {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("date", R.id.nav_week);
                startActivity(intent);
                break;
            }
            case R.id.nav_month: {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("date", R.id.nav_month);
                startActivity(intent);
                break;
            }
            case R.id.nav_year: {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("date", R.id.nav_year);
                startActivity(intent);
                break;
            }
            case R.id.nav_categories: {
                Intent intent = new Intent(this, ReportActivity.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Outlay outlay;
        dbHandler.getWritableDatabase();




        switch (view.getId()){
            case R.id.button0:
                editText.append("0");
                break;
            case R.id.button1:
                editText.append("1");
                break;
            case R.id.button2:
                editText.append("2");
                break;
            case R.id.button3:
                editText.append("3");
                break;
            case R.id.button4:
                editText.append("4");
                break;
            case R.id.button5:
                editText.append("5");
                break;
            case R.id.button6:
                editText.append("6");
                break;
            case R.id.button7:
                editText.append("7");
                break;
            case R.id.button8:
                editText.append("8");
                break;
            case R.id.button9:
                editText.append("9");
                break;
            case R.id.buttonPoint:
                editText.append(".");
                break;
            case R.id.deleteButton:
                editText.setText("");
                break;
            case R.id.button12:
                outlay = new Outlay(button12.getText().toString(), "2016-8-13", Integer.parseInt(editText.getText().toString()));
                dbHandler.addOutlay(outlay);
                Toast.makeText(this, button12.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button14:
                outlay = new Outlay(button12.getText().toString(), "2016-8-13", Integer.parseInt(editText.getText().toString()));
                dbHandler.addOutlay(outlay);
                Toast.makeText(this, button14.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button15:
                outlay = new Outlay(button12.getText().toString(), "2016-8-13", Integer.parseInt(editText.getText().toString()));
                dbHandler.addOutlay(outlay);
                Toast.makeText(this, button15.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
