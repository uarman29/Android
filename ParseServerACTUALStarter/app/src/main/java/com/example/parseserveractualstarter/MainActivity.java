package com.example.parseserveractualstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                // if defined
                .clientKey("CJQYcDufBOb5")
                .server("http://18.191.141.12/parse/")
                .build()
        );

        /*
        ParseObject score = new ParseObject("Score");
        score.put("username", "sean");
        score.put("score", 65);

        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                    Log.i("SUCCESS","We saved the score");
                else
                    e.printStackTrace();
            }
        });
        */

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.getInBackground("0E8Zpy6J0i", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e == null && object != null) {
                    object.put("score", 85);
                    object.saveInBackground();
                    Log.i("username", object.getString("username"));
                    Log.i("score", Integer.toString(object.getInt("score")));
                }
                else
                    e.printStackTrace();
            }
        });
         */

        /*
        ParseObject tweet = new ParseObject("Tweet");
        tweet.put("username","nick");
        tweet.put("tweet", "AYOOOOOOOOOOOO");

        tweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                    Log.i("SUCESS", "Tweet Saved");
                else
                    Log.i("FAILURE","FAILED");
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.getInBackground("UAKxo6nr12", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e == null && object != null)
                {
                    object.put("tweet","Something else");
                    object.saveInBackground();
                    Log.i("Tweet",object.getString("username") + ": " + object.getString("tweet"));
                }
                else
                {
                    Log.i("FAILED","FAILED");
                }
            }
        });
        */

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.whereGreaterThanOrEqualTo("score", 50);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(e == null && objects.size() > 0)
                {
                    for(ParseObject object: objects)
                    {
                        object.put("score", object.getInt("score") + 20);
                        object.saveInBackground();
                        Log.i("INFO", object.getString("username") + ": "+ object.getInt("score"));
                    }
                }
            }
        });
        */

        // Empty User for Analytic Details
        //ParseUser.enableAutomaticUser();

        /*
        // REGISTER NEW USER
        ParseUser user = new ParseUser();
        user.setUsername("nick");
        user.setPassword("password");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                    Log.i("Success","User signed up");
                else
                    e.printStackTrace();
            }
        });
        */

        /*
        // LOG IN
        ParseUser.logInInBackground("nick", "password", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null && user != null)
                    Log.i("SUCCESS", "User Logged In");
                else
                    e.printStackTrace();
            }
        });
         */


        ParseUser.logOut();
        if(ParseUser.getCurrentUser() != null)
        {
            Log.i("Signed In", ParseUser.getCurrentUser().getUsername() + " is logged in");
        }
        else
            Log.i("Not Signed In","No user is signed in");

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}