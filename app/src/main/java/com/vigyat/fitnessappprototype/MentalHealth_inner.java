package com.vigyat.fitnessappprototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MentalHealth_inner extends AppCompatActivity {

    private LinearLayout meditation, helpline, yoga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_inner);

        meditation = findViewById(R.id.idLLMeditation);
        helpline = findViewById(R.id.idLLGovtPortals);
        yoga = findViewById(R.id.yogaLL);


        meditation.setOnClickListener(v -> {

            Intent i = new Intent(getApplicationContext(), Meditation.class);
            startActivity(i);

        });

        helpline.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MentalHealth.class);
            startActivity(i);

        });

        yoga.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), YogaList.class);
            startActivity(intent);
        });

    }
}