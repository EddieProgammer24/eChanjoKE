package com.example.eChanjoKE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class TrialActivity extends AppCompatActivity {

    private EditText userID;
    private Button login;

    private String parentDbName = "Child Details";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        userID = findViewById(R.id.userID);
        login = findViewById(R.id.login);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(TrialActivity.this, ChildHomeActivity.class);
               startActivity(intent);
               LoginAllow();
           }
       });
    }

    private void LoginAllow() {
        final String certNo = userID.getText().toString();

        if (TextUtils.isEmpty(certNo)) {
            Toast.makeText(this, "Please enter certificate number", Toast.LENGTH_SHORT).show();
            return;
        }else {

            AllowAccount(certNo);
        }
    }

    private void AllowAccount(final String certNo) {

        Paper.book().write(ChildPrevalent.ChildIdKey, certNo);
        parentDbName = "Child Details";
        Paper.book().write(ChildPrevalent.ParentDB, parentDbName);


        final DatabaseReference RootRef;
               RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(certNo).exists()) {
                    ChildDetails userData = snapshot.child(parentDbName).child(certNo).getValue(ChildDetails.class);
                    if (userData != null && userData.getCertNo().equals(certNo)) {
                        Toast.makeText(TrialActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(TrialActivity.this, ChildHomeActivity.class);
                        intent.putExtra("childID", certNo);
                        startActivity(intent);
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(TrialActivity.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(TrialActivity.this, "User with certificate number " + certNo + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
                Toast.makeText(TrialActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
