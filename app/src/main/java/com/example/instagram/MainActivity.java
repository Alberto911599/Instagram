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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button signupButton;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.user_et);
        passwordInput = findViewById(R.id.password_et);
        loginButton = findViewById(R.id.btnLogin);
        signupButton = findViewById(R.id.btnSignup);
        ivLogo = findViewById(R.id.ivLogo);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Glide.with(this)
                .load("https://www.sccpre.cat/mypng/full/28-286927_fonty-instagram-logo-meat-instagram-letters-png-white.png")
                .into(ivLogo);

    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }

}
