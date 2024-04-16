package com.example.eChanjoKE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class ChildProfileActivity extends AppCompatActivity {

    private Button updateProfile, goBackProfile, saveUpdatedProfile, cancelUpdating;
    private TextView showProfileName, nameText, doBText, genderText, fnameText, mnameText, addressText,phoneText,weightText;

    private EditText name,doB,gender,fname,mname,address,phone,weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        Paper.init(this);

        updateProfile = findViewById(R.id.updateProfile);
        goBackProfile = findViewById(R.id.goBackProfile);
        saveUpdatedProfile = findViewById(R.id.saveUpdatedProfile);
        cancelUpdating = findViewById(R.id.cancelUpdating);

        showProfileName = findViewById(R.id.showProfileName);
        nameText = findViewById(R.id.name);
        doBText = findViewById(R.id.doBText);
        genderText = findViewById(R.id.genderText);
        fnameText = findViewById(R.id.fnameText);
        mnameText = findViewById(R.id.mnameText);
        addressText = findViewById(R.id.addressText);
        phoneText = findViewById(R.id.phoneText);
        weightText = findViewById(R.id.weightText);

        name = findViewById(R.id.name);
        doB = findViewById(R.id.doB);
        gender = findViewById(R.id.gender);
        fname = findViewById(R.id.fname);
        mname = findViewById(R.id.mname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        weight = findViewById(R.id.weight);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAllVisible();

            }
        });

        goBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelUpdating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAllGone();
            }
        });

        searchAcc();

        saveUpdatedProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = Paper.book().read(ChildPrevalent.ChildIdKey);
                String ParentDB = Paper.book().read(ChildPrevalent.ParentDB);

                if (ID != "childId" && ParentDB != "Child Details") {
                    if (!TextUtils.isEmpty(ID)  && !TextUtils.isEmpty(ParentDB)) {
                        final DatabaseReference myRef;
                        myRef = FirebaseDatabase.getInstance().getReference().child("Child Details").child(ID);

                        HashMap<String, Object> updatedValues = new HashMap<>();

                        updatedValues.put("fullname", name.getText().toString());
                        updatedValues.put("doB", doB.getText().toString());
                        updatedValues.put("gender", gender.getText().toString());
                        updatedValues.put("fathersName", fname.getText().toString());
                        updatedValues.put("mothersName", mname.getText().toString());
                        updatedValues.put("address", address.getText().toString());
                        updatedValues.put("contact", phone.getText().toString());
                        updatedValues.put("weight", weight.getText().toString());

                        myRef.updateChildren(updatedValues);
                        Toast.makeText(ChildProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        makeAllGone();
                        finish();
                        startActivity(getIntent());
                    }
                }

            }
        });

        if(ChildPrevalent.ParentDB.isEmpty() || ChildPrevalent.ChildIdKey.isEmpty()){
            Paper.book().write(ChildPrevalent.ChildIdKey,"childId");
            Paper.book().write(ChildPrevalent.ParentDB, "Child Details");
        }


    }

    public void searchAcc() {
        String ID = Paper.book().read(ChildPrevalent.ChildIdKey);
        String ParentDB = Paper.book().read(ChildPrevalent.ParentDB);

        if (ID != "childId" && ParentDB != "Child Details") {
            if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(ParentDB)) {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference().child("Child Details").child(ID);
                RootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            nameText.setText(snapshot.child("fullname").getValue().toString());
                            doBText.setText(snapshot.child("doB").getValue().toString());
                            genderText.setText(snapshot.child("gender").getValue().toString());
                            phoneText.setText(snapshot.child("contact").getValue().toString());
                            fnameText.setText(snapshot.child("fathersName").getValue().toString());
                            mnameText.setText(snapshot.child("mothersName").getValue().toString());
                            addressText.setText(snapshot.child("address").getValue().toString());
                            phoneText.setText(snapshot.child("contact").getValue().toString());
                            weightText.setText(snapshot.child("weight").getValue().toString());

                            showProfileName.setText(snapshot.child("fullname").getValue().toString());

                            name.setText(snapshot.child("fullname").getValue().toString());
                            doB.setText(snapshot.child("doB").getValue().toString());
                            gender.setText(snapshot.child("gender").getValue().toString());
                            fname.setText(snapshot.child("fathersName").getValue().toString());
                            mname.setText(snapshot.child("mothersName").getValue().toString());
                            address.setText(snapshot.child("address").getValue().toString());
                            phone.setText(snapshot.child("contact").getValue().toString());
                            weight.setText(snapshot.child("weight").getValue().toString());
                        }
                    }

                    private String getStringValue(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            return dataSnapshot.getValue(String.class);
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    public void makeAllVisible() {
        nameText.setVisibility(View.GONE);
        doBText.setVisibility(View.GONE);
        genderText.setVisibility(View.GONE);
        fnameText.setVisibility(View.GONE);
        mnameText.setVisibility(View.GONE);
        addressText.setVisibility(View.GONE);
        phoneText.setVisibility(View.GONE);
        weightText.setVisibility(View.GONE);

        updateProfile.setVisibility(View.GONE);
        goBackProfile.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        doB.setVisibility(View.VISIBLE);
        gender.setVisibility(View.VISIBLE);
        fname.setVisibility(View.VISIBLE);
        mname.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);

        saveUpdatedProfile.setVisibility(View.VISIBLE);
        cancelUpdating.setVisibility(View.VISIBLE);
    }

    public void makeAllGone() {
        nameText.setVisibility(View.VISIBLE);
        doBText.setVisibility(View.VISIBLE);
        genderText.setVisibility(View.VISIBLE);
        fnameText.setVisibility(View.VISIBLE);
        mnameText.setVisibility(View.VISIBLE);
        addressText.setText(View.VISIBLE);
        phoneText.setVisibility(View.VISIBLE);
        weightText.setVisibility(View.VISIBLE);

        updateProfile.setVisibility(View.VISIBLE);
        goBackProfile.setVisibility(View.VISIBLE);

        name.setVisibility(View.GONE);
        doB.setVisibility(View.GONE);
        gender.setVisibility(View.GONE);
        fname.setVisibility(View.GONE);
        mname.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);

        saveUpdatedProfile.setVisibility(View.GONE);
        cancelUpdating.setVisibility(View.GONE);
    }
}