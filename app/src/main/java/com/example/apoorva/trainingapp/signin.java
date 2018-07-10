package com.example.apoorva.trainingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {
    boolean isAdmin;
    private String TAG = "Sign in";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        final EditText e1 = findViewById(R.id.email);
        final EditText e2 = findViewById(R.id.pass);
        final EditText e3 = findViewById(R.id.passCon);
        final CheckBox c1 = findViewById(R.id.checkbox);

        mAuth = FirebaseAuth.getInstance();
        //Database intialization
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        findViewById(R.id.createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked())
                    isAdmin = true;
                else
                    isAdmin = false;
                createAccount(e1.getText().toString(), e2.getText().toString(),e3.getText().toString());
            }
        });

    }

    private void createAccount(String email, String password,String conPass) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm(email, password,conPass)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createAccount: Success!");
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Log.e(TAG, "createAccount: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(String email, String password,String conPass) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(password != conPass)
//        {
//            Toast.makeText(getApplicationContext(), "Password not correct.Re enter the passwords.", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;



    }

    private void onAuthSuccess(FirebaseUser user) {
        //Write the user to the database
        writeNewUser(user.getUid(),user.getEmail());
        finish();
    }

    private void writeNewUser(String userId,String email) {
        if(isAdmin == true) {
            Admin admin = new Admin(userId,email);
            mDatabase.child("admins").child(userId).setValue(admin);
            startActivity(new Intent(signin.this, adminHome.class));
        }
        else
        {
            Visitors user = new Visitors(userId,email);
            mDatabase.child("visitors").child(userId).setValue(user);
            startActivity(new Intent(signin.this, userHome.class));
        }
    }

}
