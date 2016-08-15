package net.simple_tracker.simpletracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {

    EditText editText;
    DBHandler dbHandler;
    ImageButton selectIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dbHandler = new DBHandler(this);
        editText = (EditText)findViewById(R.id.editText2);
        editText.setHint("Введите имя категории");
        selectIcon = (ImageButton) findViewById(R.id.addIcon);

    }

    public void showIcons(View view){
        if (!editText.getText().toString().equals("")){
            Intent intent = new Intent(this, AddIcon.class);
            intent.putExtra("CategoryName", editText.getText().toString());
            startActivity(intent);
        }else {
            Toast.makeText(CategoryActivity.this, "Добавьте имя категории", Toast.LENGTH_SHORT).show();
        }
    }
}
