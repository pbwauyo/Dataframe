package com.example.dataframe;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.UtilityMethods.setRequestContent;

public class TeacherActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
    private TextView birthDayTxt, birthMonthTxt, birthYearTxt;
    private TextView postingDayTxt, postingMonthTxt, postingYearTxt;
    private TextView appointmentDayTxt, appointmentMonthTxt, appointmentYearTxt;
    private ImageView birthDatePicker, postingDatePicker, appointmentDatePicker;
    private DatePickerFragment datePickerFragment;
    private DatePickerDialog datePickerDialog;
    private String SELECTED_VIEW;
    private GoogleProgressBar progressBar;

    private int day;
    private int month;
    private int year;

    private TextInputEditText fNameTxt, lNameTxt, ninTxt, employeeNumTxt, licenseNumTxt, phoneNumTxt, emailTxt, mpsNumberTxt;
    private RadioGroup sexGrp, licenseGrp;
    private Spinner qualificationSpinner, salaryScaleSpinner;
    private Button submitBtn;

    TextInputLayout licenseNumLayout;

    private String fName, lName, nin, employeeNum, licenseNum, phoneNum, email, sex, license, qualification, salaryScale, mpsNumber, dateOfBirth, dateOfPosting, dateOfAppointment;
    private String schoolId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Intent intent = getIntent();
        schoolId = intent.getStringExtra("school_id");

        progressBar = findViewById(R.id.teacher_progress_bar);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        licenseNumLayout = findViewById(R.id.teacher_license_number_layout);

        fNameTxt = findViewById(R.id.teacher_first_name);
        lNameTxt = findViewById(R.id.teacher_last_name);
        ninTxt = findViewById(R.id.teacher_nin);
        employeeNumTxt = findViewById(R.id.teacher_employee_number);
        licenseNumTxt = findViewById(R.id.teacher_license_number);
        phoneNumTxt = findViewById(R.id.teacher_phone_number);
        emailTxt = findViewById(R.id.teacher_email_address);

        sexGrp = findViewById(R.id.teacher_sex);
        licenseGrp = findViewById(R.id.teacher_licensed);

        qualificationSpinner = findViewById(R.id.qualification_spinner);
        salaryScaleSpinner = findViewById(R.id.teacher_salary_scale_spinner);
        mpsNumberTxt = findViewById(R.id.teacher_mps_number);
        submitBtn = findViewById(R.id.submit_teacher_btn);

        birthDayTxt = findViewById(R.id.birth_day);
        birthMonthTxt = findViewById(R.id.birth_month);
        birthYearTxt = findViewById(R.id.birth_year);

        postingDayTxt = findViewById(R.id.first_posting_day);
        postingMonthTxt = findViewById(R.id.first_posting_month);
        postingYearTxt = findViewById(R.id.first_posting_year);

        appointmentDayTxt = findViewById(R.id.first_appointment_day);
        appointmentMonthTxt = findViewById(R.id.first_appointment_month);
        appointmentYearTxt = findViewById(R.id.first_appointment_year);

        birthDatePicker = findViewById(R.id.dob_date_picker);
        postingDatePicker = findViewById(R.id.dop_date_picker);
        appointmentDatePicker = findViewById(R.id.doa_date_picker);

        licenseGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.teacher_licensed_yes){
                    licenseNumLayout.setVisibility(View.VISIBLE);
                }
                else if(checkedId == R.id.teacher_licensed_no){
                    licenseNumLayout.setVisibility(View.GONE);
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //first name
                fName = fNameTxt.getText().toString().trim();

                //last name
                lName = lNameTxt.getText().toString().trim();

                //national id number
                nin = ninTxt.getText().toString().trim();

                //employee number
                employeeNum = employeeNumTxt.getText().toString().trim();

                //license number
                if(licenseNumLayout.getVisibility() == View.VISIBLE){
                    licenseNum = licenseNumTxt.getText().toString().trim();
                }

                //email
                email = emailTxt.getText().toString().trim();

                //sex
                if(sexGrp.getCheckedRadioButtonId() == R.id.teacher_male){
                    sex = "MALE";
                }
                else if(sexGrp.getCheckedRadioButtonId() == R.id.teacher_female){
                    sex = "FEMALE";
                }

                //license
                if(licenseGrp.getCheckedRadioButtonId() == R.id.teacher_licensed_yes){
                    license = "TRUE";
                }
                else if(licenseGrp.getCheckedRadioButtonId() == R.id.teacher_licensed_no){
                    license = "NO";
                }

                //qualification
                qualification = getQualification(qualificationSpinner.getSelectedItem().toString());

                //salary scale
                salaryScale = salaryScaleSpinner.getSelectedItem().toString();

                //MPS Number
                mpsNumber = mpsNumberTxt.getText().toString().trim();

                new SweetAlertDialog(TeacherActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure")
                        .setContentText("Make sure all fields are correct")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                progressBar.setVisibility(View.VISIBLE);
                                new PostTeacherDetails().execute();
                            }
                        })
                        .show();

            }
        });

        birthDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "birth_day_text_view";
                showDatePickerDialog();

//                datePickerFragment = new DatePickerFragment();
//                datePickerFragment.show(getSupportFragmentManager(), "birthDateFragment");

            }
        });

        postingDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "posting_day_text_view";
                showDatePickerDialog();
//                datePickerFragment = new DatePickerFragment();
//                datePickerFragment.show(getSupportFragmentManager(), "postingDateFragment");
            }
        });

        appointmentDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "appointment_text_view";
                showDatePickerDialog();
//                datePickerFragment = new DatePickerFragment();
//                datePickerFragment.show(getSupportFragmentManager(), "appointmentDateFragment");
            }
        });
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        switch (SELECTED_VIEW){
            case "birth_day_text_view":
                dateOfBirth = year + "-" + month + "-" + dayOfMonth;
                birthDayTxt.setText(String.valueOf(dayOfMonth));
                birthMonthTxt.setText(String.valueOf(month));
                birthYearTxt.setText(String.valueOf(year));
                break;

            case "posting_day_text_view":
                dateOfPosting = year + "-" + month + "-" + dayOfMonth;
                postingDayTxt.setText(String.valueOf(dayOfMonth));
                postingMonthTxt.setText(String.valueOf(month));
                postingYearTxt.setText(String.valueOf(year));
                break;

            case "appointment_text_view":
                dateOfAppointment = year + "-" + month + "-" + dayOfMonth;
                appointmentDayTxt.setText(String.valueOf(dayOfMonth));
                appointmentMonthTxt.setText(String.valueOf(month));
                appointmentYearTxt.setText(String.valueOf(year));
                break;
        }
    }

    JSONObject buildJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject deploymentSite = new JSONObject();
        deploymentSite.accumulate("id", schoolId);

        jsonObject.accumulate("nationalId", nin);
        jsonObject.accumulate("employeeNumber", employeeNum);
        jsonObject.accumulate("firstName", fName);
        jsonObject.accumulate("lastName", lName);
        jsonObject.accumulate("licensed", license);
        jsonObject.accumulate("phoneNumber", phoneNum);
        jsonObject.accumulate("emailAddress", email);
        jsonObject.accumulate("deploymentSite", deploymentSite);
        jsonObject.accumulate("gender", sex);
        jsonObject.accumulate("dob", dateOfBirth);
        jsonObject.accumulate("registrationType", "STAFF");
        jsonObject.accumulate("staffQualification", qualification);
        jsonObject.accumulate("dateOfFirstPosting", dateOfPosting);
        jsonObject.accumulate("dateOfFirstAppointment", dateOfAppointment);
        jsonObject.accumulate("MPSComputerNumber", mpsNumber);

        return jsonObject;
    }

    private String httpPost(String postUrl) throws IOException, JSONException {
        URL url = new URL(postUrl);
        JSONObject jsonObject = buildJsonObject();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        setRequestContent(conn, jsonObject);
        conn.connect();

        return conn.getResponseMessage()+"";
    }

    private class PostTeacherDetails extends AsyncTask<Void, Void, String>{
        String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/employee/register";

        @Override
        protected String doInBackground(Void... voids) {
            String responseMessage = "";
            try {
                responseMessage = httpPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseMessage;
        }

        @Override
        protected void onPostExecute(String response){
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);

            if(response.equalsIgnoreCase("OK")){
                new SweetAlertDialog(TeacherActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText("Details saved successfully")
                        .setConfirmText("Done")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(TeacherActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Problem saving details. Try again")
                        .setCancelText("OK")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }

    }

    private String getQualification(String selectedQualification){
        String qualification="";

        switch (selectedQualification){
            case "DPE":
                qualification = "DPE";
                break;

            case "GRADE II":
                qualification = "Grade_II";
                break;

            case "GRADE III":
                qualification = "Grade_III";
                break;

            case "GRADE IV":
                qualification = "Grade_IV";
                break;

            case "GRADE V":
                qualification = "Grade_V";
                break;

            case "GRADUATE TEACHER":
                qualification = "Graduate_Teacher";
                break;

            case "LICENSED TEACHER":
                qualification = "Licensed_Teacher";
                break;

            case "OTHER TRAINING":
                qualification = "Other_Training";
                break;

            case "NOT STATED":
                qualification = "Not_stated";
                break;
        }

        return qualification;
    }

}
