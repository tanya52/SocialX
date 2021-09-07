package com.example.socialx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    TextView alreadyHaveAccount,signIn;
    EditText name,email,number,password;
    Button signUP;

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signIn=(TextView)findViewById(R.id.signin);
        alreadyHaveAccount=(TextView)findViewById(R.id.alreadyHaveAccount);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        number=(EditText)findViewById(R.id.number);
        password=(EditText)findViewById(R.id.password);
        signUP=(Button)findViewById(R.id.signup);
        progressDialog=new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String inputEmail=email.getText().toString();
        String inputPassword=password.getText().toString();



       if (inputPassword.isEmpty()|| password.length()<6){
            password.setError("Enter password more than 6 characters");
        }else if (inputEmail.isEmpty()){
            email.setError("Enter Email");
        }else{
            progressDialog.setMessage("Please wait while we register");
            progressDialog.setTitle("registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(SignupActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}