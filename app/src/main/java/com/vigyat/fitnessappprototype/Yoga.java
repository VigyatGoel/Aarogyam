package com.vigyat.fitnessappprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vigyat.fitnessappprototype.databinding.ActivityYogaBinding;

public class Yoga extends AppCompatActivity {

    TextView urlTextVIew;

    YogaListModalClass yoga;
    ActivityYogaBinding yogaBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        yogaBinding = DataBindingUtil.setContentView(this, R.layout.activity_yoga);

        Intent intent = getIntent();
        String yogaName = intent.getStringExtra("yoga_name");
        int yogaImageResource = intent.getIntExtra("yoga_image", 0);
        String yogaUrl = intent.getStringExtra("yoga_url");
        String yogaBenefits = intent.getStringExtra("yoga_benefits");


        // Find views in your layout
        ImageView imageView = yogaBinding.yogaImg;
        TextView nameTextView =yogaBinding.textView6;

        urlTextVIew = yogaBinding.textView7;

        yoga = new YogaListModalClass();

        urlTextVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri webpage = Uri.parse(yogaUrl);

                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(intent);
            }
        });
        TextView benefitsTextView = yogaBinding.benefits2;


        // Update views with the received data

        // display image using glide
        Glide.with(this)
                .load(yogaImageResource)
                .into(imageView);

         // Load the image resource
        nameTextView.setText(yogaName);

        benefitsTextView.setText(yogaBenefits);
    }// Set the name

}