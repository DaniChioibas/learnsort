package com.example.learnsort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LessonActivity extends AppCompatActivity {
    Intent intent;
    Button animatieBtn,informatiiBtn,codBtn;
    String lessonID;
    Fragment fragmentCod = new CodeFragment();
    Fragment fragmentInfo = new InformationFragment();
    Fragment fragmentAnimation = new AnimationFragment();
    TextView nume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        lessonID=intent.getStringExtra("lessonID");
        nume=findViewById(R.id.textViewAlgoritm);

        nume.setText(lessonID);

        animatieBtn=findViewById(R.id.animatieBtn);
        informatiiBtn=findViewById(R.id.informatiiBtn);
        codBtn=findViewById(R.id.codBtn);

        animatieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(fragmentAnimation);
            }
        });
        informatiiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(fragmentInfo);
            }
        });
        codBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(fragmentCod);
            }
        });





    }

    private void addFragment(Fragment fragment) {
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Start a FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the contents of the FrameLayout with the new fragment
        transaction.replace(R.id.fragment_container, fragment);

        // Commit the transaction
        transaction.commit();
    }
}