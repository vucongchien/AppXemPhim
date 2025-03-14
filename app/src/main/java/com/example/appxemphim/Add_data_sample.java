package com.example.appxemphim;

import static com.example.appxemphim.FireStore_DataBase.Add_Data.Add_Data_movie;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appxemphim.object_data.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Add_data_sample extends MainActivity {
    ArrayList<EditText> editTexts;
    ArrayList<String> text;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data_sample);
        editTexts.add(findViewById(R.id.tenphim));
        editTexts.add(findViewById(R.id.mota));
        editTexts.add(findViewById(R.id.poster));
        editTexts.add(findViewById(R.id.trailer));
        editTexts.add(findViewById(R.id.nation));
        editTexts.add(findViewById(R.id.created_at));

    }

    public void addmovie(View view) {
        for( EditText a : editTexts){
            text.add(a.getText().toString());
            if(a.getText().toString().isEmpty()){
                Toast.makeText(this, "vui long nhap du thong tin", Toast.LENGTH_SHORT).show();
                text.clear();
                return;
            }
        }
        try {
             date = dateFormat.parse(text.get(5));
        }catch (ParseException e){
            e.printStackTrace();
        }

        Add_Data_movie(db, new Movie(text.get(0),text.get(1),text.get(2),text.get(3),text.get(4),date));
    }
}