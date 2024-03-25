package com.example.eChanjoKE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class DoctorHomeActivity extends AppCompatActivity {

    private ImageButton docappointmentsButton, doctrackButton, doctorProfile, doctorLogout;
    private TextView doctorNameShow;
    private DatabaseReference myRef;
    private String DocNameShow;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        doctorNameShow = findViewById(R.id.doctorNameShow); // Move this line up

        Paper.init(DoctorHomeActivity.this);
        final String Docid = Paper.book().read(Prevalent.UserIdKey);

        myRef = FirebaseDatabase.getInstance().getReference("Doctors").child(Docid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DocNameShow = snapshot.child("name").getValue(String.class);
                if (DocNameShow != null) {
                    doctorNameShow.setText(DocNameShow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        docappointmentsButton = findViewById(R.id.docappointmentsButton);
        doctorProfile = findViewById(R.id.doctorProfile);
        doctrackButton = findViewById(R.id.doctrackButton);
        doctorLogout = findViewById(R.id.doctorLogout);

        doctrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, TrackSerial.class);
                startActivity(intent);
            }
        });

        docappointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Appointments.class);
                startActivity(intent);
            }
        });
        doctorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
            }
        });

        doctorLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorHomeActivity.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Paper.book().write(Prevalent.UserIdKey, "id");
                                Paper.book().write(Prevalent.UserPasswordKey, "pass");
                                Paper.book().write(Prevalent.ParentDB, "user");
                                finish();
                                //startActivity(new Intent(DoctorHomeActivity.this, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Paper.book().write(Prevalent.UserIdKey, "id");
                        Paper.book().write(Prevalent.UserPasswordKey, "pass");
                        Paper.book().write(Prevalent.ParentDB, "user");
                        finish();
                        //startActivity(new Intent(DoctorHomeActivity.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        this.moveTaskToBack(true);
    }
}
