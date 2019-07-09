package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}
