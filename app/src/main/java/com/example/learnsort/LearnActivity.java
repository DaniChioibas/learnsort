package com.example.learnsort;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LearnActivity extends AppCompatActivity {
    ArrayList<QuizModel> quizModels=new ArrayList<>();
    ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageViewLogo=findViewById(R.id.imageView_logo);
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        imageViewLogo.startAnimation(bounceAnimation);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setUp();
        LearnModel_RecyclerViewAdapter adapter = new LearnModel_RecyclerViewAdapter(this,quizModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setUp(){
        quizModels.add(new QuizModel("1","Bubble Sort","Invata despre bubble sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("2","Insertion Sort","Invata despre insertion sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("3","Selection Sort","Invata despre selection sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("4","Merge Sort","Invata despre merge sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("5","Quick Sort","Invata despre quick sort","10", Collections.emptyList()));

    }

}