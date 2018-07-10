package com.example.apoorva.trainingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "Log in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText e1 = findViewById(R.id.emailL);
        final EditText e2 = findViewById(R.id.passL);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        findViewById(R.id.openAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString().trim();
                final String password = e2.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "Enter Email Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, "Before Logging in");
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Toast.makeText(login.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(login.this, "Not a valid account", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Logged in");
                            final String id = task.getResult().getUser().getUid();
                            Query q = mDatabase.child("admins").orderByChild("userId");
                            q.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        Log.i(TAG, child.getKey());
                                        if(!child.getKey().equals(id)){
                                            continue;
                                        }
                                        else
                                        {
                                            startActivity(new Intent(login.this,adminHome.class));
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                                startActivity(new Intent(login.this,userHome.class));
                        }

                    }
                });
            }
        });
    }
}
