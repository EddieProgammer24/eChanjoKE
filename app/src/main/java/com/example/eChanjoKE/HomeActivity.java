package com.example.eChanjoKE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ImageButton deptButton, trackButton, reviewButton, tipsButton, emergencyButton, faqButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        deptButton = findViewById(R.id.deptButton);
        tipsButton = findViewById(R.id.tipsButton);
        reviewButton = findViewById(R.id.reviewButton);
        emergencyButton = findViewById(R.id.emergencyButton);
        faqButton = findViewById(R.id.faqButton);
        trackButton = findViewById(R.id.trackButton);

        deptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Departments.class);
                startActivity(intent);
            }
        });
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackSerial.class);
                startActivity(intent);
            }
        });
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Reviews.class);
                startActivity(intent);
            }
        });
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Emergency.class);
                startActivity(intent);
            }
        });
        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Tips.class);
                startActivity(intent);
            }
        });
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FAQ.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navDashboardPage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navDashboardPage) {
                    return true;
                } else if (itemId == R.id.navAdminPage) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });

        if (Prevalent.ParentDB.isEmpty() || Prevalent.UserIdKey.isEmpty() || Prevalent.UserPasswordKey.isEmpty()) {
            Paper.book().write(Prevalent.UserIdKey, "id");
            Paper.book().write(Prevalent.UserPasswordKey, "pass");
            Paper.book().write(Prevalent.ParentDB, "user");
        }
        searchAcc();
    }

    public void searchAcc() {
        String ID = Paper.book().read(Prevalent.UserIdKey);
        String Pass = Paper.book().read(Prevalent.UserPasswordKey);
        String ParentDB = Paper.book().read(Prevalent.ParentDB);

        if (ID != "id" && Pass != "pass" && ParentDB != "user") {
            if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(Pass) && !TextUtils.isEmpty(ParentDB)) {
                AllowAccount(ID, ParentDB);
            }
        }
    }
    public void AllowAccount(final String ID, final String ParentDB) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (isFinishing()) {
                    // If the activity is finishing, don't proceed
                    return;
                }

                if (snapshot.child(ParentDB).child(ID).exists()) {
                    if (ParentDB.equals("Admin")) {
                        showProgressDialog("Logging in as Admin...");
                        startActivity(new Intent(HomeActivity.this, AdminHomeActivity.class));
                    } else if (ParentDB.equals("Doctors")) {
                        showProgressDialog("Logging in as Doctor...");
                        startActivity(new Intent(HomeActivity.this, DoctorHomeActivity.class));
                    }
                } else {
                    hideProgressDialog(); // Dismiss the loading bar if account not found
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog(); // Dismiss the loading bar on cancellation
            }
        });
    }

    private void showProgressDialog(String message) {
        if (loadingBar == null) {
            loadingBar = new ProgressDialog(HomeActivity.this);
        }
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    private void hideProgressDialog() {
        if (loadingBar != null && loadingBar.isShowing()) {
            loadingBar.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        HomeActivity.super.onBackPressed(); // Call super.onBackPressed() after dismissing the dialog
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

}
