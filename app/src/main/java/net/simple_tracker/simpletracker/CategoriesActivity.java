package net.simple_tracker.simpletracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener{

    DBHandler dbHandler;
    List<Category> categories;
    GridLayout imgLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dbHandler = new DBHandler(this);
        imgLayout = (GridLayout)findViewById(R.id.img_categories);
        showCategories();

    }



    private List<Category> getCategories(){
        dbHandler.getReadableDatabase();
        return dbHandler.getAllCategories();
    }

    private void showCategories(){
        categories = getCategories();
        for (Category o:categories) {
            Button button = new Button(this);
            button.setId(o.getId());
            button.setText(o.getCategoryName());
            button.setBackgroundResource(o.getIcon());
            button.setOnClickListener(this);
            imgLayout.addView(button);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CategoryInfo.class);
        if (view.getId()==R.id.addCategory){
            startActivity(intent);
        }else {
            intent.putExtra("id", view.getId());
            startActivity(intent);
        }
    }
}
