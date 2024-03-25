package com.example.eChanjoKE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditChildActivity extends AppCompatActivity {
    //creating variables for our edit text, firebase database
    // database reference, course rv modal, progress bar.

    private TextInputEditText cerNumberEdt,fullNameEdt,doBEdt,genderEdt,fatherNameEdt,motherNameEdt,addressEdt,weightEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildDetails childDetails;
    private ProgressBar loadingPB;
    private String childId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        //initializing all our variables on below line
        Button addChildBtn = findViewById(R.id.idBtnAddChild);
        cerNumberEdt = findViewById(R.id.idEdtBirthCert);
        fullNameEdt = findViewById(R.id.idEdtFullName);
        doBEdt = findViewById(R.id.idEdtDoB);
        genderEdt = findViewById(R.id.idEdtGender);
        fatherNameEdt = findViewById(R.id.idEdtFatherName);
        motherNameEdt = findViewById(R.id.idEdtMotherName);
        addressEdt = findViewById(R.id.idEdtAddress);
        weightEdt = findViewById(R.id.idEdtWeight);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        childDetails = getIntent().getParcelableExtra("child");
        Button deleteChildBtn = findViewById(R.id.idBtnDeleteChild);

        if(childDetails != null){
            //on below line we are setting data to our edit text from the child details class
            cerNumberEdt.setText(childDetails.getCertNo());
            fullNameEdt.setText(childDetails.getFullname());
            doBEdt.setText(childDetails.getDoB());
            genderEdt.setText(childDetails.getGender());
            fatherNameEdt.setText(childDetails.getFathersName());
            motherNameEdt.setText(childDetails.getMothersName());
            addressEdt.setText(childDetails.getAddress());
            weightEdt.setText(childDetails.getWeight());
            childId = childDetails.getChildId();
        }

        //on below line we are initializing our database reference and we are adding a child as the fullName
        databaseReference = firebaseDatabase.getReference("Child Details").child(childId);

        //on below line we are adding a click listener for the edit button
        addChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are making our progress bar visible
                loadingPB.setVisibility(View.VISIBLE);

                //on below line we are getting data from our edittext
                String certNo = cerNumberEdt.getText().toString();
                String fullname = fullNameEdt.getText().toString();
                String doB = doBEdt.getText().toString();
                String gender = genderEdt.getText().toString();
                String fathersName = fatherNameEdt.getText().toString();
                String mothersName = motherNameEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String weight = weightEdt.getText().toString();

                //on below line we are creating a map for
                //passing data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("certNo",certNo);
                map.put("fullname",fullname);
                map.put("doB",doB);
                map.put("gender",gender);
                map.put("fathersName",fathersName);
                map.put("mothersName",mothersName);
                map.put("address",address);
                map.put("weight",weight);
                map.put("childId",childId);

                //on below line we are calling a database reference on
                //add value event listener and on data change
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //making progressbar visibility as gone
                        loadingPB.setVisibility(View.GONE);
                        //adding a map to our database
                        databaseReference.updateChildren(map);
                        //on below line we are displaying a toast message
                        Toast.makeText(EditChildActivity.this, "Child Details Updated..", Toast.LENGTH_SHORT).show();
                        //opening a new activity after updating the child details
                        startActivity(new Intent(EditChildActivity.this,ChildView.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //displaying a failure message on Toast
                        Toast.makeText(EditChildActivity.this, "Failed to update child details..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //adding a click listener for delete child event
        deleteChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to delete child
                deleteChild();
            }
        });

    }

    private void deleteChild() {
        //on line below calling a method to delete the child details
        databaseReference.removeValue();
        //opening the child view activity on below line
        startActivity(new Intent(EditChildActivity.this,ChildView.class));
    }
}