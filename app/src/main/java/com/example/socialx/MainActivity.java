package com.example.socialx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView regiterNow;
    EditText email, password;
    Button logIn,btnsignUp;
    ImageView googleLogin,facebookLogin;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        regiterNow = (TextView) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.login);
        btnsignUp=(Button)findViewById(R.id.btnsignUp);
        googleLogin=(ImageView)findViewById(R.id.google);
        facebookLogin=(ImageView)findViewById(R.id.facebook);
        progressDialog = new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

        regiterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GoogleSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FacebookSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }



    private void performLogin() {
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();


        if (inputEmail.isEmpty()) {
            email.setError("Enter Correct Email");
        } else if (inputPassword.isEmpty() || password.length() < 6) {
            password.setError("Enter proper password");
        } else {
            progressDialog.setMessage("Please wait while we register");
            progressDialog.setTitle("registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}