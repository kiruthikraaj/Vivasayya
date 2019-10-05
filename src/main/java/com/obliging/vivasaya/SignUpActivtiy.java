package com.obliging.vivasaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivtiy extends AppCompatActivity {

    EditText email,password,reenterpassword,username;
    Button signup;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        email=findViewById(R.id.emailContainer);
        password=findViewById(R.id.passwordContainer);
        reenterpassword = findViewById(R.id.retypePassword);
        signup=findViewById(R.id.signup_btn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailId = email.getText().toString();
                String passwordId = password.getText().toString();
                String retypePasswordId = reenterpassword.getText().toString();

                if (emailId.isEmpty()){

                    email.setError("Enter the email id");
                }

                else if(passwordId.isEmpty()){
                    password.setError("Password is Empty");
                }
                else{
                    if(passwordId.equals(retypePasswordId)){
                        firebaseAuth.createUserWithEmailAndPassword(emailId,passwordId).addOnCompleteListener(SignUpActivtiy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    firebaseDatabase.getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Email").setValue(emailId);
                                    finish();
                                    startActivity(new Intent(SignUpActivtiy.this,MainActivity.class));
                                }
                                else{
                                    Toast.makeText(SignUpActivtiy.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        reenterpassword.setError("Password does not match. Re-Enter password correctly");
                    }
                }

            }
        });

    }

}
