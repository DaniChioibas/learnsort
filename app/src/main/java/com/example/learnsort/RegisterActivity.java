package com.example.learnsort;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDoB, editTextRegisterMobile, editTextRegisterPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private static final String TAG="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName=findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail=findViewById(R.id.editText_register_email);
        editTextRegisterDoB=findViewById(R.id.editText_register_dob);
        editTextRegisterMobile=findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd=findViewById(R.id.editText_register_password);

        radioGroupRegisterGender=findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        Button buttonRegister = findViewById(R.id.button_quizz);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textFullName))
                {
                    editTextRegisterFullName.setError("Numele este obligatoriu!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (TextUtils.isEmpty(textEmail))
                {
                    editTextRegisterFullName.setError("Email-ul este obligatoriu!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    editTextRegisterFullName.setError("Email-ul trebuie sa fie valid!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (TextUtils.isEmpty(textDoB))
                {
                    editTextRegisterFullName.setError("Data este obligatorie!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (radioGroupRegisterGender.getCheckedRadioButtonId()==-1)
                {
                    editTextRegisterFullName.setError("Sexul este obligatoriu!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (TextUtils.isEmpty(textMobile))
                {
                    editTextRegisterFullName.setError("Telefonul este obligatoriu!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (TextUtils.isEmpty(textPwd))
                {
                    editTextRegisterFullName.setError("Parola este obligatorie!");
                    editTextRegisterFullName.requestFocus();
                }
                else if (!isPasswordValid(textPwd))
                {
                    editTextRegisterFullName.setError("Parola nu este destul de complexa!");
                    editTextRegisterFullName.requestFocus();
                }
                else
                {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textDoB,textGender,textMobile,textPwd);
                }


            }
        });
    }

    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDoB,textGender,textMobile);
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                // Daca vrem confirmare prin e-mail.
                                //firebaseUser.sendEmailVerification();

                            Toast.makeText(RegisterActivity.this, "Cont creat cu succes!",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                                }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Eroare la crearea contului",Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });


                }
                else
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Adresa de email invalida sau deja folosita!");
                        editTextRegisterPwd.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    } catch(FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("Exista deja un cont cu aceasta adresa de email!");
                        editTextRegisterPwd.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
            }
        });


    }
    public boolean isPasswordValid(String password) {
        if (password == null || password.length() < 10) {
            return false;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*+=?-].*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
}