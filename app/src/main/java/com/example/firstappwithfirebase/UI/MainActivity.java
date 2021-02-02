package com.example.firstappwithfirebase.UI;



import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.firstappwithfirebase.R;

import java.util.Date;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    TravelViewModel viewModel = new TravelViewModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TravelDataSource ts = TravelDataSource.getInstance();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2(){
        Intent intent = new Intent(this, AddTravelActivity.class);
        startActivity(intent);
    }




}