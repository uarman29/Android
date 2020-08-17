package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener, View.OnKeyListener
{
    EditText usernameEditText;
    EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        ImageView logoImage = findViewById(R.id.image);
        ConstraintLayout layout = findViewById(R.id.layout);

        logoImage.setOnClickListener(this);
        layout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                // if defined
                .clientKey("CJQYcDufBOb5")
                .server("http://18.191.141.12/parse/")
                .build()
        );

        if(ParseUser.getCurrentUser() != null)
            moveToUserList();

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.image || view.getId() == R.id.layout)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() ==  KeyEvent.ACTION_DOWN)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        return false;
    }

    public void signUp(View view)
    {
        if(usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        }
        else {
            final ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        moveToUserList();
                    else
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void login(View view)
    {
        if(usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null && user != null)
                        moveToUserList();
                    else
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void moveToUserList()
    {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }
}