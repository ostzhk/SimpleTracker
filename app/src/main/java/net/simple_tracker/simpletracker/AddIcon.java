package net.simple_tracker.simpletracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddIcon extends AppCompatActivity implements View.OnClickListener{

    DBHandler dbHandler;
    List<Integer> icons;
    TableLayout iconsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_icon);
        setTheme(R.style.AddIconTheme);
        dbHandler = new DBHandler(this);
        initializeIcons();
        iconsLayout = (TableLayout)findViewById(R.id.iconsLayout);
        showIcons();
    }

    private void showIcons(){
        for (Integer i:icons){
            ImageButton imageButton = new ImageButton(this);
            imageButton.setOnClickListener(this);
            imageButton.setId(i);
            imageButton.setScaleType(ImageView.ScaleType.CENTER);
            imageButton.setBackgroundResource(i);
            iconsLayout.addView(imageButton);
        }
    }

    @Override
    public void onClick(View view) {
        dbHandler.getWritableDatabase();
        String categoryName = getIntent().getExtras().getString("CategoryName");
            dbHandler.addCategory(new Category(categoryName, view.getId()));
        Toast.makeText(AddIcon.this, "Категория " + categoryName + " добавлена", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initializeIcons(){
        icons = new ArrayList<>();
        icons.add(R.drawable.ic_add_icon);
        icons.add(R.drawable.ic_category_eat);
    }
}
