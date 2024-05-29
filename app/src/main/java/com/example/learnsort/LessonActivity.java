package com.example.learnsort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LessonActivity extends AppCompatActivity {
    Intent intent;
    Button informatiiBtn,codBtn;
    String lessonID,informatii,codsursa;
    Fragment fragmentCod;
    Fragment fragmentInfo;

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
        informatiiBtn=findViewById(R.id.informatiiBtn);
        codBtn=findViewById(R.id.codBtn);


        retrieveQuizData(lessonID);


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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void retrieveQuizData(String lessonId) {
        DatabaseReference lessonRef = FirebaseDatabase.getInstance().getReference().child("lessons").child(lessonId);
        lessonRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String id = snapshot.child("id").getValue(String.class);
                    informatii = snapshot.child("informatii").getValue(String.class);
                    codsursa = snapshot.child("codSursa").getValue(String.class);
                    String numeAlg = snapshot.child("nume").getValue(String.class);
                    nume.setText(numeAlg);

                    fragmentInfo=new InformationFragment(informatii);
                    fragmentCod=new CodeFragment(codsursa);




                } else {
                }
            } else {
            }
        });
    }

}