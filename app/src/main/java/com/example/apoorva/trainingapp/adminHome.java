package com.example.apoorva.trainingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class adminHome extends AppCompatActivity {
  String[] listCourses;
    Button mBtn;
    EditText ev;
    EditText ev2;
    String etStr;

    private Button mDoneButton;





    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        mAuth = FirebaseAuth.getInstance();
        mDoneButton = (Button) findViewById(R.id.add);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(adminHome.this);
                mBuilder.setTitle("Add a new course detail");
                LinearLayout l = new LinearLayout(adminHome.this);
                l.setOrientation(LinearLayout.VERTICAL);
                ev = new EditText(adminHome.this);
                ev2 = new EditText(adminHome.this);

                ev.setHint("Enter Course Name");
                ev2.setHint("Enter Course detail");
                l.addView(ev);
                l.addView(ev2);

                mBuilder.setView(l);

                mBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                etStr = ev.getText().toString();
                                etStr = ev2.getText().toString();


                                dialog.cancel();
                                Log.v("adffhguj",etStr);
                            }

                        });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }




        });
        findViewById(R.id.exitAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(adminHome.this,mainPage.class));

            }
        });
       /* mBtn = findViewById(R.id.add);
        final TextView mResult = (TextView) findViewById(R.id.tvResult);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCourses = new String[]{"Course Name", "Course Detail"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(adminHome.this);
                mBuilder.setTitle("Choose courses");
                mBuilder.setItems(listCourses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),listCourses[i],Toast.LENGTH_LONG).show();
                    }
                });
                mBuilder.setSingleChoiceItems(listCourses, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      mTextView.setTextContent(listCourses[i]);
                      dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mResult.setText(listCourses[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
           AlertDialog mDialog = mBuilder.create();
                mDialog.show();


        }
    }*/


    }


    }
