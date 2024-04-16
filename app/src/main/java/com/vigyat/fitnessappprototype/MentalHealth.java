package com.vigyat.fitnessappprototype;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MentalHealth extends AppCompatActivity {

    RecyclerView recyclerView;
    List<GovtHelpLineModal> helpLineList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health);

        recyclerView = findViewById(R.id.recyclerView);
        helpLineList = new ArrayList<>();

        GovtHelpLineModal helpLine1 = new GovtHelpLineModal(R.drawable.ic_launcher_background, "Suicide", "https://www.google.com/search?q=how+to+suicide&oq=how+to+sui&gs_lcrp=EgZjaHJvbWUqBggBEEUYOzIGCAAQRRg5MgYIARBFGDsyBwgCEAAYgAQyBwgDEAAYgAQyBwgEEAAYgATSAQgzMTE5ajBqOagCALACAA&client=ms-android-xiaomi-rvo2b&sourceid=chrome-mobile&ie=UTF-8");
        GovtHelpLineModal helpLine2 = new GovtHelpLineModal(R.drawable.ic_launcher_background, "Suicide", "https://www.x.com");

        helpLineList.add(helpLine1);
        helpLineList.add(helpLine2);

        adapter = new MyAdapter(helpLineList, this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false); // For horizontal scrolling

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}