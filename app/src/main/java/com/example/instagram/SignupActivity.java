package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.user_su);
        emailInput = findViewById(R.id.email_su);
        passwordInput = findViewById(R.id.password_su);
        btnSignup = findViewById(R.id.btnSignupsu);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


        ImageView myImage =  findViewById(R.id.ivSignup);
        Glide.with(this)
                .load("https://1.bp.blogspot.com/-5cSNkUlM6Ns/WEfffCkKw7I/AAAAAAAABMo/odiYMhl33M8nav4HjLnSQB1DeIjQgcdJQCLcB/s1600/instagram-1594387_960_720.png")
                .into(myImage);
    }


    private void signup(){
        // Create the ParseUser
        ParseUser user = new ParseUser();

        // Set core properties
        user.setUsername(usernameInput.getText().toString());
        user.setPassword(passwordInput.getText().toString());
        user.setEmail(emailInput.getText().toString());

        // Set custom properties
        user.put("handle", "sample");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("SignupActivity", "Signup successful");
                }else{
                    Log.d("SignupActivity", "Signup failed");
                }
            }
        });

        Intent intent = new Intent(SignupActivity.this, TimelineActivity.class);
        startActivity(intent);
    }
}
