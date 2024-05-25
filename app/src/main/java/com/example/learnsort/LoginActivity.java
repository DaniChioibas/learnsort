package com.example.learnsort;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail,editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();

                if (TextUtils.isEmpty(textEmail))
                {
                    editTextLoginEmail.setError("Email is required!");
                    editTextLoginEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(textPwd))
                {
                    editTextLoginPwd.setError("Password is required!");
                    editTextLoginPwd.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPwd);
                }
            }
        });
    }


    private void loginUser(String email, String pwd) {
         authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful())
                 {
                     Toast.makeText(LoginActivity.this,"You are now logged in!",Toast.LENGTH_LONG).show();
                 }
                 else
                 {
                     try {
                         throw task.getException();
                     }catch (FirebaseAuthInvalidUserException e)
                     {
                         editTextLoginEmail.setError("User does not exist");
                         editTextLoginEmail.requestFocus();
                     }
                     catch (FirebaseAuthInvalidCredentialsException e)
                     {
                         editTextLoginEmail.setError("Invalid credentials");
                         editTextLoginEmail.requestFocus();
                     }
                     catch (Exception e)
                     {
                         Log.e(TAG,e.getMessage());
                         Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                     }
                 }
                 progressBar.setVisibility(View.GONE);
             }
         });
    }
}