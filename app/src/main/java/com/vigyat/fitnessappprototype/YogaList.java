package com.vigyat.fitnessappprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.vigyat.fitnessappprototype.databinding.ActivityYogaListBinding;

import java.util.ArrayList;
import java.util.List;

public class YogaList extends AppCompatActivity {


    RecyclerView recyclerView;
    List<YogaListModalClass> yogaList;
    YogaListAdapter adapter;
    ActivityYogaListBinding yogaListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_list);
        yogaListBinding = DataBindingUtil.setContentView(this, R.layout.activity_yoga_list);

        recyclerView = yogaListBinding.yogaRecyclerView;

         YogaListModalClass yoga = new YogaListModalClass("Anulom-Vilom",R.drawable.anulomvilom,"https://theyogainstitute.org/anulom-vilom-pranayama/","1.Improves breathing\n2.Reduces mental stress");
         YogaListModalClass yoga7 = new YogaListModalClass("Kapal Bhati",R.drawable.kapalbhati,"https://www.artofliving.org/in-en/yoga/breathing-techniques/skull-shining-breath-kapal-bhati","1.Improves breathing\n2.Reduces mental stress");
         YogaListModalClass yoga1 = new YogaListModalClass("Mandukasana",R.drawable.mandukasna,"https://www.youtube.com/watch?v=bxO4MK8YDWE&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC","1.Opens chest and shoulders\n2.Improves digestion");
         YogaListModalClass yoga2 = new YogaListModalClass("Mayurasana",R.drawable.mayurasana,"https://www.youtube.com/watch?v=ASNK54226ts&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=3","1.Strengthens your wrists and arms\n2.Tones your abdominal muscles");
         YogaListModalClass yoga3 = new YogaListModalClass("Savasana",R.drawable.shavasan,"https://www.youtube.com/watch?v=1VYlOKUdylM&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=4","1.Reduces stress and anxiety\n2.Calms your mind");
         YogaListModalClass yoga4 = new YogaListModalClass("Marichyasana",R.drawable.marichyasana,"https://www.youtube.com/watch?v=tUdN0g2wugM&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=6","1.Strengthens your spine\n2.Stretches your back and shoulders");
         YogaListModalClass yoga5 = new YogaListModalClass("Ardha Bhekasana",R.drawable.ardhabhekasana,"https://www.youtube.com/watch?v=giGl3EpvP68&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=5","1.Opens up your chest\n2.Improves posture");
         YogaListModalClass yoga6 = new YogaListModalClass("Hanumanasana",R.drawable.hanumanasan,"https://www.youtube.com/watch?v=3ir0DFG5oMQ&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=2","1.Strengthens your spine\n2.Stretches your back and shoulders");
         YogaListModalClass yoga8 = new YogaListModalClass("Dhanurasana",R.drawable.dhanushasan,"https://www.youtube.com/watch?v=DPiW5pN1jQM&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=7","1.Activates all seven chakras\n2.Strengthens your upper and lower body");
         YogaListModalClass yoga9 = new YogaListModalClass("Svarga Dvijasana",R.drawable.svargadvijasana,"https://www.youtube.com/watch?v=wIJzVgTTVew&list=PLtM0YoctczKa2-G6HoX-vSFViYiHFdWhC&index=8","1.Strengthens your lower body\n2.Improves concentration");
         //YogaListModalClass yoga10 = new YogaListModalClass("Maksikanagasana",R.drawable.svargadvijasana,"https://www.google.com","1.Strengthens your arms, back, and shoulders\n2.Improves flexibility and mobility");



        yogaList = new ArrayList<>();

         yogaList.add(yoga);
         yogaList.add(yoga7);
         yogaList.add(yoga1);
         yogaList.add(yoga2);
         yogaList.add(yoga3);
         yogaList.add(yoga4);
         yogaList.add(yoga5);
         yogaList.add(yoga6);
         yogaList.add(yoga8);
         yogaList.add(yoga9);
         //yogaList.add(yoga10);




         adapter = new YogaListAdapter(yogaList,this);
         LinearLayoutManager layoutManager = new LinearLayoutManager(this);

         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(adapter);


    }
}