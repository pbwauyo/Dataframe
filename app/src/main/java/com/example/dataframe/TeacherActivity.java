package com.example.dataframe;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dataframe.dbhelpers.TeacherDbHelper;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.UtilityMethods.setRequestContent;
import static com.example.dataframe.contracts.TeacherContract.Constants;

public class TeacherActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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

    SweetAlertDialog errorAlertDialog, successAlertDialog;

    TeacherDbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues;
    private final String emptyError = "field can't be empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        schoolId = intent.getStringExtra("school_id");

        progressBar = findViewById(R.id.teacher_progress_bar);

        dbHelper = new TeacherDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

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

        errorAlertDialog =  new SweetAlertDialog(TeacherActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error!")
                .setContentText("Problem saving details. Try again")
                .setCancelText("OK")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

        successAlertDialog = new SweetAlertDialog(TeacherActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Details saved successfully")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
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

                //phone number
                phoneNum = phoneNumTxt.getText().toString().trim();

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

                if(fName.equals("")){
                    fNameTxt.setError(emptyError);
                }
                else if(lName.equals("")){
                    lNameTxt.setError(emptyError);
                }
                else if(nin.equals("")){
                    ninTxt.setError(emptyError);
                }
                else if(employeeNum.equals("")){
                    employeeNumTxt.setError(emptyError);
                }
                else if(phoneNum.equals("")){
                    phoneNumTxt.setError(emptyError);
                }
                else if(email.equals("")){
                    emailTxt.setError(emptyError);
                }
                else if(mpsNumber.equals("")){
                    mpsNumberTxt.setError(emptyError);
                }
                else {
                    displayCustomDialog();
                }
            }
        });

        birthDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "birth_day_text_view";
                showDatePickerDialog();

            }
        });

        postingDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "posting_day_text_view";
                showDatePickerDialog();
            }
        });

        appointmentDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_VIEW = "appointment_text_view";
                showDatePickerDialog();
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
        conn.setRequestProperty("Content-Type", "com/example/dataframe/application/json; charset=utf-8");
        setRequestContent(conn, jsonObject);
        conn.connect();

        return conn.getResponseMessage()+"";
    }

    private class PostTeacherDetails extends AsyncTask<Void, Void, HashMap<String, String>>{
        String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/employee/register";

        @Override
        protected HashMap<String, String> doInBackground(Void... voids) {
            HashMap<String, String> responses = new HashMap<>();

            String responseMessage = "";
            try {
                responseMessage = httpPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String newRowId = String.valueOf(postValues()); //post values to database
            Log.d("new row id", newRowId);

            responses.put("urlResponse", responseMessage);
            responses.put("databaseResponse", newRowId);
            return responses;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> responses){
            super.onPostExecute(responses);
            progressBar.setVisibility(View.GONE);

            String urlResponse, databaseResponse;
            urlResponse = responses.get("urlResponse");
            databaseResponse = responses.get("databaseResponse");

            if(urlResponse.equalsIgnoreCase("OK")){
                clearAllFields();
                successAlertDialog.show();
            }
            else {
                errorAlertDialog.show();
            }

//            if(databaseResponse.equals("-1")){
//                errorAlertDialog.show();
//            }
//            else {
//                successAlertDialog.show();
//            }
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

    long postValues(){
        contentValues.put(Constants.FIRST_NAME, fName);
        contentValues.put(Constants.LAST_NAME, lName);
        contentValues.put(Constants.SEX, sex);
        contentValues.put(Constants.NIN, nin);
        contentValues.put(Constants.EMPLOYEE_NUMBER, employeeNum);
        contentValues.put(Constants.LICENSED, license);
        contentValues.put(Constants.PHONE_NUMBER, phoneNum);
        contentValues.put(Constants.EMAIL_ADDRESS, email);
        contentValues.put(Constants.DATE_OF_BIRTH, dateOfBirth);
        contentValues.put(Constants.QUALIFICATION, qualification);
        contentValues.put(Constants.DATE_OF_FIRST_POSTING, dateOfPosting);
        contentValues.put(Constants.DATE_OF_FIRST_APPOINTMENT, dateOfAppointment);
        contentValues.put(Constants.SALARY_SCALE, salaryScale);
        contentValues.put(Constants.MPS_NUMBER, mpsNumber);

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    String displaySummary(){
        return   "First Name:   " + fName + "\n" +
                 "Last name:    " + lName  + "\n" +
                 "Sex:          " + sex + "\n" +
                 "NIN:          " + nin + "\n" +
                 "Employee Num: " + employeeNum + "\n" +
                 "Licensed:     " + license + "\n" +
                 "Phone Num:    " + phoneNum + "\n" +
                 "Email:        " + email + "\n" +
                 "Date of birth:    " + dateOfBirth + "\n" +
                 "Date of first posting: " + dateOfPosting + "\n" +
                 "Date of first appointment: " + dateOfAppointment + "\n" +
                 "Salary scale: " + salaryScale + "\n" +
                 "MPS Number:   " + mpsNumber + "\n";
    }

    void displayCustomDialog(){
        TextView fNameTxt, lNameTxt, sexTxt, ninTxt, employeeNumTxt, licensedTxt, phoneNumTxt, emailTxt, dobTxt, qualificationTxt, dateOfFirstPostingTxt, dateOfFirstAppointmentTxt, salaryScaleTxt, mpsNumTxt;
        Button cancelBtn, confirmBtn;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_teacher_pop_up_screen, viewGroup, false);

        //find views
        fNameTxt = dialogView.findViewById(R.id.popup_fname);
        lNameTxt = dialogView.findViewById(R.id.popup_lname);
        sexTxt = dialogView.findViewById(R.id.popup_sex);
        ninTxt = dialogView.findViewById(R.id.popup_nin);
        employeeNumTxt = dialogView.findViewById(R.id.popup_employee_num);
        licensedTxt = dialogView.findViewById(R.id.popup_licensed);
        phoneNumTxt = dialogView.findViewById(R.id.popup_phone_num);
        emailTxt = dialogView.findViewById(R.id.popup_email);
        dobTxt = dialogView.findViewById(R.id.popup_dob);
        qualificationTxt = dialogView.findViewById(R.id.popup_qualification);
        dateOfFirstPostingTxt = dialogView.findViewById(R.id.popup_dop);
        dateOfFirstAppointmentTxt = dialogView.findViewById(R.id.popup_doa);
        salaryScaleTxt = dialogView.findViewById(R.id.popup_salary_scale);
        mpsNumTxt = dialogView.findViewById(R.id.popup_mps_computer_number);
        cancelBtn = dialogView.findViewById(R.id.cancel_button);
        confirmBtn = dialogView.findViewById(R.id.confirm_button);

        //set views
        fNameTxt.setText(fName);
        lNameTxt.setText(lName);
        sexTxt.setText(sex);
        ninTxt.setText(nin);
        employeeNumTxt.setText(employeeNum);
        licensedTxt.setText(license);
        phoneNumTxt.setText(phoneNum);
        emailTxt.setText(email);
        dobTxt.setText(dateOfBirth);
        qualificationTxt.setText(qualification);
        dateOfFirstPostingTxt.setText(dateOfPosting);
        dateOfFirstAppointmentTxt.setText(dateOfAppointment);
        salaryScaleTxt.setText(salaryScale);
        mpsNumTxt.setText(mpsNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                dialog.dismiss();
                                progressBar.setVisibility(View.VISIBLE);
                                new PostTeacherDetails().execute();
                            }
                        })
                        .show();
            }
        });
    }

    void clearAllFields(){
        fNameTxt.setText("");
        lNameTxt.setText("");
        ninTxt.setText("");
        employeeNumTxt.setText("");
        phoneNumTxt.setText("");
        emailTxt.setText("");
        mpsNumberTxt.setText("");

        birthDayTxt.setText("");
        birthMonthTxt.setText("");
        birthYearTxt.setText("");

        postingDayTxt.setText("");
        postingMonthTxt.setText("");
        postingYearTxt.setText("");

        appointmentDayTxt.setText("");
        appointmentMonthTxt.setText("");
        appointmentYearTxt.setText("");

        sexGrp.clearCheck();
        licenseGrp.clearCheck();
    }

}
