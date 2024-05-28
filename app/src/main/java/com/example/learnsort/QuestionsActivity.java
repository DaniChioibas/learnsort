package com.example.learnsort;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    Button ansA,ansB,ansC,ansD,next;
    TextView question;

    private TextView timeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        startTimer();

        question=findViewById(R.id.question_textview);
        ansA=findViewById(R.id.ansA);
        ansB=findViewById(R.id.ansB);
        ansC=findViewById(R.id.ansC);
        ansD=findViewById(R.id.ansD);
        next=findViewById(R.id.next_btn);

        ansA.setText("ASdasodj askdjasokldj asokdj asokdj asodkj asodkjasokdj asokdj asokdj oaskljdi");
    }

    private void startTimer() {
        timeTextView=findViewById(R.id.timer_indicator_textview);
        long totalTimeInMillis = Integer.parseInt("1") * 60 * 1000L;
        new CountDownTimer(totalTimeInMillis, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                String timeText = String.format("%02d:%02d", minutes, remainingSeconds);
                timeTextView.setText(timeText);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(QuestionsActivity.this,QuizActivity.class));
                finish();
            }
        }.start();
    }

}
