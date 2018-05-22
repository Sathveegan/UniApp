package com.app.uni.uniapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {

    private Button commentButton, applyRating;
    private RatingBar ratingBar;
    private EditText commentText;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private boolean admin;
    private FirebaseUser currentUser;

    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_rating, container, false );

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        applyRating = (Button) view.findViewById(R.id.applyRating);
        commentText = (EditText) view.findViewById(R.id.commentMultiLine);
        commentButton = (Button) view.findViewById(R.id.commentButton);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        currentUser = user;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        applyRating.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Rating : " + String.valueOf(ratingBar.getRating()),Toast.LENGTH_LONG).show();

                String username = currentUser.getEmail().split("@")[0].toString();
                databaseReference.child("users").child(username).child("rating").setValue(String.valueOf(ratingBar.getRating()));
            }
        } );

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentText.getText().toString();
                String username = currentUser.getEmail().split("@")[0].toString();
                databaseReference.child("users").child(username).child("comment").setValue(String.valueOf(comment));
                Toast.makeText(getActivity(),"Comment Sent!",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
