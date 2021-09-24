package com.example.dataframe.popups;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dataframe.R;

public class TeacherPopUpScreen extends AppCompatActivity {
    TextView fNameTxt, lNameTxt, sexTxt, ninTxt, employeeNumTxt, licensedTxt, phoneNumTxt, emailTxt, dobTxt, qualificationTxt, dateOfFirstPostingTxt, dateOfFirstAppointmentTxt, salaryScaleTxt, mpsNumTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pop_up_screen);

        fNameTxt = findViewById(R.id.popup_fname);
        lNameTxt = findViewById(R.id.popup_lname);
        sexTxt = findViewById(R.id.popup_sex);
        ninTxt = findViewById(R.id.popup_nin);
        employeeNumTxt = findViewById(R.id.popup_employee_num);
        licensedTxt = findViewById(R.id.popup_licensed);
        phoneNumTxt = findViewById(R.id.popup_phone_num);
        emailTxt = findViewById(R.id.popup_email);
        dobTxt = findViewById(R.id.popup_dob);
        qualificationTxt = findViewById(R.id.popup_qualification);
        dateOfFirstPostingTxt = findViewById(R.id.popup_dop);
        dateOfFirstAppointmentTxt = findViewById(R.id.popup_doa);
        salaryScaleTxt = findViewById(R.id.popup_salary_scale);
        mpsNumTxt = findViewById(R.id.popup_mps_computer_number);
    }
}
