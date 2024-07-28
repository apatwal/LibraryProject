package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

public class Home extends AppCompatActivity implements Reading.sendDataToMain {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    private ViewPageAdapter viewPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null) {
            String personName = acct.getDisplayName();

        }

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);


        viewPager = findViewById(R.id.viewpager);

        // setting up the adapter
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        // add the fragments
        viewPageAdapter.add(new Search(), "Search");
        viewPageAdapter.add(new Reading(), "Reading");
        viewPageAdapter.add(new History(), "History");

        // Set the adapter
        viewPager.setAdapter(viewPageAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(Home.this,MainActivity.class));
            }
        });

    }


    @Override
    public void sendStuff(String str) {

    }
}