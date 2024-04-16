package com.example.eChanjoKE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class ChildHomeActivity extends AppCompatActivity {

    private ImageButton childProfile;
    private TextView childNameShow;

    private DatabaseReference myRef;

    private String ChildNameShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);

        childNameShow = findViewById(R.id.doctorNameShow);
        Paper.init(ChildHomeActivity.this);

        final String Chid = Paper.book().read(ChildPrevalent.ChildIdKey);
        myRef = FirebaseDatabase.getInstance().getReference("Child Details").child(Chid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChildNameShow = snapshot.child("fullname").getValue(String.class);
                if(ChildNameShow != null){
                    childNameShow.setText(ChildNameShow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        childProfile = findViewById(R.id.doctorProfile);

        childProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildHomeActivity.this, ChildProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
