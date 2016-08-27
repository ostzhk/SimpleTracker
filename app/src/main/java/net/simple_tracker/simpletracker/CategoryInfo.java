package net.simple_tracker.simpletracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryInfo extends AppCompatActivity implements View.OnClickListener{

    DBHandler dbHandler;
    List<Integer> icons;
    GridLayout iconsLayout;
    EditText editText;
    boolean change;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info);

        dbHandler = new DBHandler(this);
        iconsLayout = (GridLayout)findViewById(R.id.iconsChange);
        editText = (EditText)findViewById(R.id.editText2);
        initializeIcons();
        Intent intent = getIntent();


        if (intent.getExtras()!=null){
            change = true;
            categoryId = intent.getExtras().getInt("id");
            editText.setText(dbHandler.getCategoryName(categoryId));
        }else {
            change = false;
            editText.setHint("Введите имя категории");
        }
        showIcons();
    }

    private void showIcons(){
        for (Integer i:icons){
            ImageButton imageButton = new ImageButton(this);
            imageButton.setOnClickListener(this);
            imageButton.setId(i);
            imageButton.setBackgroundResource(i);
            iconsLayout.addView(imageButton);
        }
    }

    private void initializeIcons(){
        icons = new ArrayList<>();
        icons.add(R.drawable.ic_category_car);
        icons.add(R.drawable.ic_category_eat);
        icons.add(R.drawable.ic_category_health);
        icons.add(R.drawable.ic_category_shop);
        icons.add(R.drawable.ic_category_transport);
    }

    @Override
    public void onClick(View view) {
        dbHandler.getWritableDatabase();
        String categoryName = editText.getText().toString();
        if (!categoryName.equals("")){
            if (change){
                dbHandler.changeCategory(categoryId, categoryName, view.getId());
                Toast.makeText(CategoryInfo.this, "Категория изменена.", Toast.LENGTH_SHORT).show();
            }else {
                dbHandler.addCategory(new Category(categoryName, view.getId()));
                Toast.makeText(CategoryInfo.this, "Категория " + categoryName + " добавлена.", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(CategoryInfo.this, "Введите имя категории", Toast.LENGTH_SHORT).show();
        }
    }
}
