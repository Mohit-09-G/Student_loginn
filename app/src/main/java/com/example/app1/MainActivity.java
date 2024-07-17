package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private CheckBox checkBoxElectrical, checkBoxCivil, checkBoxCse;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxElectrical = findViewById(R.id.checkBoxElectrical);
        checkBoxCivil = findViewById(R.id.checkBoxCivil);
        checkBoxCse = findViewById(R.id.checkBoxCse);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        checkBoxElectrical.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) uncheckOtherCheckBoxes(checkBoxElectrical);
        });

        checkBoxCivil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) uncheckOtherCheckBoxes(checkBoxCivil);
        });

        checkBoxCse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) uncheckOtherCheckBoxes(checkBoxCse);
        });
    }

    private void uncheckOtherCheckBoxes(CheckBox checkedBox) {
        if (checkedBox != checkBoxElectrical) checkBoxElectrical.setChecked(false);
        if (checkedBox != checkBoxCivil) checkBoxCivil.setChecked(false);
        if (checkedBox != checkBoxCse) checkBoxCse.setChecked(false);
    }

    private void register() {
        // Get values from EditTexts
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        // Ensure only one checkbox is selected
        if (!(checkBoxElectrical.isChecked() || checkBoxCivil.isChecked() || checkBoxCse.isChecked())) {
            Toast.makeText(MainActivity.this, "Please select one course", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get course selection from CheckBoxes
        StringBuilder selectedCourses = new StringBuilder();
        if (checkBoxElectrical.isChecked()) {
            selectedCourses.append("Electrical, ");
        }
        if (checkBoxCivil.isChecked()) {
            selectedCourses.append("Civil, ");
        }
        if (checkBoxCse.isChecked()) {
            selectedCourses.append("Computer Science, ");
        }

        String coursesSelected;
        if (selectedCourses.length() > 0) {
            coursesSelected = selectedCourses.substring(0, selectedCourses.length() - 2); // Remove last comma and space
        } else {
            coursesSelected = "None selected";
        }

        String gender = "";
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            gender = radioButton.getText().toString();
        } else {
            Toast.makeText(MainActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

       
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.custom_toast_layout,
                findViewById(R.id.textViewToastMessage));


        TextView textViewToastMessage = toastLayout.findViewById(R.id.textViewToastMessage);
        String message = "Username: " + username + "\n"
                + "Password: " + password + "\n"
                + "Courses: " + coursesSelected + "\n"
                + "Gender: " + gender;
        textViewToastMessage.setText(message);


        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }
}
