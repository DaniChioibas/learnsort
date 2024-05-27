package com.example.learnsort;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    ArrayList<QuizModel> quizModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setUp();
        QuizModel_RecyclerViewAdapter adapter = new QuizModel_RecyclerViewAdapter(this,quizModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private void setUp(){
        quizModels.add(new QuizModel("1","Bubble Sort","About bubble sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("2","Insertion Sort","About insertion sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("3","Selection Sort","About selection sort","10", Collections.emptyList()));
        quizModels.add(new QuizModel("4","Merge Sort","About merge sort","10", Collections.emptyList()));

    }
}