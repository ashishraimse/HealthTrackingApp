package com.example.navigationdrawersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        TextView result;
        EditText htin , htft , wt;
        Button calculate;
//        LinearLayout linerarmain;

//      Connecting with UI
        result = findViewById(R.id.Result);
        htin = findViewById(R.id.inheight);
        htft = findViewById(R.id.ftheight);
        wt = findViewById(R.id.weight);
//        linerarmain=findViewById(R.id.linerarmain);
        calculate = findViewById(R.id.BMIbtn);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wht = Integer.parseInt(wt.getText().toString());
                int ft = Integer.parseInt(htft.getText().toString());
                int in = Integer.parseInt(htin.getText().toString());

                int inInch = ft*12 + in;
                double inCm = inInch*2.53;
                double totalhtinM = inCm/100;
                double bmidata = (wht)/(totalhtinM*totalhtinM);
                if(bmidata > 25)
                {
                    result.setText("You are OverWeighted Person!");
                    result.setBackgroundColor(getResources().getColor(R.color.Owt));
                }
                else if(bmidata < 18)
                {
                    result.setText("You are UnderWeighted Person!");
                    result.setBackgroundColor(getResources().getColor(R.color.Uwt));

                }
                else
                {
                    result.setText("You are Healthy Person!");
                    result.setBackgroundColor(getResources().getColor(R.color.Hel));

                }
            }
        });

    }
}