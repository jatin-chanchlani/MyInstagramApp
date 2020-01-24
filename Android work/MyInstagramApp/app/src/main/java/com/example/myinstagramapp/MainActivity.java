package com.example.myinstagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity
{
    private static final int signin = 123;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

        if(fbuser != null)
        {
            // User already signed in
            // go to feed activity
            String token = FirebaseInstanceId.getInstance().getToken();

            User user = new User(fbuser.getUid(), fbuser.getDisplayName() , token);
            database.child("users").child(user.uid).setValue(user);

            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        }

    }



    public void signIn(View view)
    {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),signin);
    }

    protected void onActivityResult(int requestCode , int resultCode , Intent data)
    {
        super.onActivityResult(requestCode , resultCode , data);

        if(requestCode == signin)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK)
            {
                //successful sign in
                FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

                String token = FirebaseInstanceId.getInstance().getToken();

                User user = new User(fbuser.getUid() , fbuser.getDisplayName() , token);

                database.child("users").child(user.uid).setValue(user);

                FirebaseApp.initializeApp(this);

                Toast.makeText(this, "Authenticated as " + fbuser.getDisplayName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, FeedActivity.class);
                startActivity(intent);

            }
            else
            {
                //sign in failed
                if(response!=null)
                {
                    Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}



