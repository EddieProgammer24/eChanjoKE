package com.example.eChanjoKE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eChanjoKE.frontend.Activity_AlarmsList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChildView extends AppCompatActivity implements ChildDetailsAdapter.ChildClickInterface {

    private FloatingActionButton addChildFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView childRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<ChildDetails> childDetailsArrayList;
    private ChildDetailsAdapter childDetailsAdapter;
    private RelativeLayout homeRL;

    // Inside your ChildView activity
    private boolean isRecyclerViewEnabled = false;

    private BottomNavigationView.OnItemSelectedListener navigationItemSelectedListener
            = item -> {
        Intent intent = null;
        if (item.getItemId() == R.id.bottom_home) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else if (item.getItemId() == R.id.bottom_vaccine) {
            intent = new Intent(getApplicationContext(), ChildView.class);
        } else if (item.getItemId() == R.id.bottom_reminders) {
            intent = new Intent(getApplicationContext(), Activity_AlarmsList.class);
        }

        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return false;
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_view);

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_vaccine);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);

        // Initializing variables
        childRV = findViewById(R.id.idRVChildren);
        loadingPB = findViewById(R.id.idPBLoading);
        addChildFAB = findViewById(R.id.idFABAddChild);
        homeRL = findViewById(R.id.idRLBSheet);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        childDetailsArrayList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Child Details");

        // Adding a click listener for the floating action button
        addChildFAB.setOnClickListener(v -> {
            Intent i = new Intent(ChildView.this, AddChildDetails.class);
            startActivity(i);
        });

        // Initializing the adapter class
        childDetailsAdapter = new ChildDetailsAdapter(childDetailsArrayList, this, this::onChildClick);
        childRV.setLayoutManager(new LinearLayoutManager(this));
        childRV.setAdapter(childDetailsAdapter);

        // Getting child data from the database
        getInfant();

        // Initially disable the RecyclerView
        childRV.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_child_view, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Enable RecyclerView only if the search query is not empty
                isRecyclerViewEnabled = !query.isEmpty();
                childRV.setEnabled(isRecyclerViewEnabled);
                if (isRecyclerViewEnabled) {
                    filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Enable RecyclerView only if the search query is not empty
                isRecyclerViewEnabled = !newText.isEmpty();
                childRV.setEnabled(isRecyclerViewEnabled);
                if (isRecyclerViewEnabled) {
                    filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    private void filter(String text) {
        ArrayList<ChildDetails> filteredList = new ArrayList<>();
        for (ChildDetails item : childDetailsArrayList) {
            if (item.getFullname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        childDetailsAdapter.filterList(filteredList);
    }

    private void getInfant() {
        childDetailsArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                childDetailsArrayList.add(snapshot.getValue(ChildDetails.class));
                childDetailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                childDetailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                childDetailsAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                childDetailsAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onChildClick(int position) {
        displayBottomSheet(childDetailsArrayList.get(position));
    }

    private void displayBottomSheet(ChildDetails childDetails) {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View layout = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, homeRL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView FullNameTV = layout.findViewById(R.id.idTVFullName);
        TextView DoBTV = layout.findViewById(R.id.idTVDoB);
        TextView GenderTV = layout.findViewById(R.id.idTVGender);
        TextView WeightTV = layout.findViewById(R.id.idTVWeight);
        FullNameTV.setText(childDetails.getFullname());
        DoBTV.setText(childDetails.getDoB());
        GenderTV.setText(childDetails.getGender());
        WeightTV.setText(childDetails.getWeight());
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditDetails);

        editBtn.setOnClickListener(v -> {
            Intent i = new Intent(ChildView.this, EditChildActivity.class);
            i.putExtra("child", childDetails);
            startActivity(i);
        });

        viewBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            startActivity(i);
        });
    }
}
