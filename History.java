package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class History extends Fragment {

    Button signOut;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    FirebaseDatabase firebaseDatabase;

    ImageView i1, i2, i3;
    TextView a1,a2,a3;
    TextView t1,t2,t3;




    public History() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, null);
        signOut = fragmentView.findViewById(R.id.signOut);
        i1 = fragmentView.findViewById(R.id.retrieveImage);
        i2 = fragmentView.findViewById(R.id.retrieveImage2);
        i3 = fragmentView.findViewById(R.id.retrieveImage3);
        t1 = fragmentView.findViewById(R.id.retrieveTitle);
        t2 = fragmentView.findViewById(R.id.retrieveTitle2);
        t3 = fragmentView.findViewById(R.id.retrieveTitle3);
        a1 = fragmentView.findViewById(R.id.retrieveAuthor);
        a2 = fragmentView.findViewById(R.id.retrieveAuthor2);
        a3 = fragmentView.findViewById(R.id.retrieveAuthor3);


        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

            databaseReference = firebaseDatabase.getReference("Books").child("item 1");
            databaseReference2 = firebaseDatabase.getReference("Books").child("item 2");
            databaseReference3 = firebaseDatabase.getReference("Books").child("item 3");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    t1.setText(String.valueOf(snapshot.child("title").getValue()));
                    a1.setText(String.valueOf(snapshot.child("author").getValue()));
                    Picasso.with(i1.getContext()).load(String.valueOf(snapshot.child("imageUrl").getValue())).into(i1);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    t2.setText(String.valueOf(snapshot.child("title").getValue()));
                    a2.setText(String.valueOf(snapshot.child("author").getValue()));
                    Picasso.with(i2.getContext()).load(String.valueOf(snapshot.child("imageUrl").getValue())).into(i2);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    t3.setText(String.valueOf(snapshot.child("title").getValue()));
                    a3.setText(String.valueOf(snapshot.child("author").getValue()));
                    Picasso.with(i3.getContext()).load(String.valueOf(snapshot.child("imageUrl").getValue())).into(i3);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        // Inflate the layout for this fragment
        return fragmentView;
    }
}