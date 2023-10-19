package com.vigyat.fitnessappprototype;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Yoga extends AppCompatActivity {

    TextView urlTextVIew;

    YogaListModalClass yoga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        Intent intent = getIntent();
        String yogaName = intent.getStringExtra("yoga_name");
        int yogaImageResource = intent.getIntExtra("yoga_image", 0);
        String yogaUrl = intent.getStringExtra("yoga_url");
        String yogaBenefits = intent.getStringExtra("yoga_benefits");


        // Find views in your layout
        ImageView imageView = findViewById(R.id.yogaImg);
        TextView nameTextView = findViewById(R.id.textView6);

        urlTextVIew = findViewById(R.id.textView7);

        yoga = new YogaListModalClass();

        urlTextVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = yogaUrl;
                Uri webpage = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(intent);
            }
        });
        TextView benefitsTextView = findViewById(R.id.benefits2);


        // Update views with the received data
        imageView.setImageResource(yogaImageResource); // Load the image resource
        nameTextView.setText(yogaName);

        benefitsTextView.setText(yogaBenefits);
    }// Set the name

}