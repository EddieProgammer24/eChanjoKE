package com.example.eChanjoKE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VaccineSchedule extends AppCompatActivity {

    private EditText editTextDateOfBirth;
    private Button buttonGenerateSchedule;
    private LinearLayout vaccineContainer;
    private DatePickerDialog datePickerDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_schedule);

        // Initialize views
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        buttonGenerateSchedule = findViewById(R.id.buttonGenerateSchedule);
        vaccineContainer = findViewById(R.id.vaccineContainer);

        // Set click listener for the Select Date button
        editTextDateOfBirth .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set click listener for the Generate Schedule button
        buttonGenerateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateVaccinationSchedule();
            }
        });
    }

    private void showDatePickerDialog() {
        // Get current date to set as the initial date for the DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Calculate the minimum date (5 months ago)
        calendar.add(Calendar.MONTH, -5);
        int minYear = calendar.get(Calendar.YEAR);
        int minMonth = calendar.get(Calendar.MONTH);
        int minDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        datePickerDialog = new DatePickerDialog(VaccineSchedule.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Update the EditText with the selected date
                editTextDateOfBirth.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth);
            }
        }, year, month, dayOfMonth);

        // Set minimum date to 5 months ago
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Set maximum date to today's date to disable future dates
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    private void generateVaccinationSchedule() {
        // Retrieve date of birth from EditText
        String dobString = editTextDateOfBirth.getText().toString().trim();

        // Parse date of birth string to Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date dob;
        try {
            dob = dateFormat.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Clear previous vaccine cards
        vaccineContainer.removeAllViews();

        // List of vaccines with their corresponding months
        ArrayList<String> vaccines = new ArrayList<>();
        vaccines.add("HepB (Hepatitis B) - Birth");
        vaccines.add("RV (Rotavirus) - 2 months");
        vaccines.add("DTaP (Diphtheria, Tetanus, Pertussis) - 2 months");
        vaccines.add("Hib (Haemophilus influenzae type b) - 2 months");
        vaccines.add("PCV13 (Pneumococcal conjugate) - 2 months");
        vaccines.add("IPV (Inactivated Poliovirus) - 2 months");
        vaccines.add("RV (Rotavirus) - 4 months");
        vaccines.add("DTaP (Diphtheria, Tetanus, Pertussis) - 4 months");
        vaccines.add("Hib (Haemophilus influenzae type b) - 4 months");
        vaccines.add("PCV13 (Pneumococcal conjugate) - 4 months");
        vaccines.add("IPV (Inactivated Poliovirus) - 4 months");
        vaccines.add("RV (Rotavirus) - 6 months");
        vaccines.add("DTaP (Diphtheria, Tetanus, Pertussis) - 6 months");
        vaccines.add("Hib (Haemophilus influenzae type b) - 6 months");
        vaccines.add("PCV13 (Pneumococcal conjugate) - 6 months");
        vaccines.add("IPV (Inactivated Poliovirus) - 6 months");
        vaccines.add("HepB (Hepatitis B) - 6 months");
        vaccines.add("Hib (Haemophilus influenzae type b) - 12-15 months");
        vaccines.add("MMR (Measles, Mumps, Rubella) - 12-15 months");
        vaccines.add("Varicella (Chickenpox) - 12-15 months");
        vaccines.add("PCV13 (Pneumococcal conjugate) - 12-15 months");
        vaccines.add("HepA (Hepatitis A) - 12-23 months");
        vaccines.add("DTaP (Diphtheria, Tetanus, Pertussis) - 15-18 months");
        vaccines.add("IPV (Inactivated Poliovirus) - 4-6 years");

        // Calculate vaccination dates based on the date of birth
        Calendar vaccineDate = Calendar.getInstance();
        for (String vaccine : vaccines) {
            vaccineDate.setTime(dob);
            String[] parts = vaccine.split(" - ");
            String vaccineName = parts[0];
            String monthsString = parts[1];
            int months;
            if (monthsString.equalsIgnoreCase("Birth")) {
                months = 0; // Vaccination at birth
            } else if (monthsString.contains("-")) {
                String[] range = monthsString.split("-");
                int lowerBound = Integer.parseInt(range[0]);
                int upperBound = Integer.parseInt(range[1].split(" ")[0]);
                months = (lowerBound + upperBound) / 2; // Average months in range
            } else {
                months = Integer.parseInt(monthsString.split(" ")[0]);
            }

            vaccineDate.add(Calendar.MONTH, months);
            addVaccineCard(vaccineName, vaccineDate.getTime());
        }
    }

    private void addVaccineCard(String vaccineName, Date vaccineDate) {
        // Inflate card layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardViewLayout = inflater.inflate(R.layout.vaccine_card_layout, vaccineContainer, false);

        // Set vaccine name and date
        TextView textViewVaccineName = cardViewLayout.findViewById(R.id.textViewVaccineName);
        TextView textViewVaccineDate = cardViewLayout.findViewById(R.id.textViewVaccineDate);
        final CheckBox checkBoxVaccineStatus = cardViewLayout.findViewById(R.id.checkBoxVaccineStatus);
        textViewVaccineName.setText(vaccineName);
        textViewVaccineDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(vaccineDate));

        // Set tag to identify the CheckBox associated with the vaccine
        checkBoxVaccineStatus.setTag(vaccineDate);

        // Check if the vaccine date is in the past, mark as completed if true
        if (vaccineDate.before(Calendar.getInstance().getTime())) {
            checkBoxVaccineStatus.setChecked(true);
            checkBoxVaccineStatus.setText("Completed");
        } else {
            checkBoxVaccineStatus.setChecked(false);
            checkBoxVaccineStatus.setText("Pending");
            // Disable checkbox if the vaccination date has not yet been reached
            checkBoxVaccineStatus.setEnabled(false);
        }

        // Set click listener for the CheckBox
        checkBoxVaccineStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    checkBox.setText("Completed");
                } else {
                    checkBox.setText("Pending");
                }
            }
        });

        // Add card to container
        vaccineContainer.addView(cardViewLayout);
    }


}
