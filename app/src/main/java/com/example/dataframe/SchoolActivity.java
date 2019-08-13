package com.example.dataframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.dataframe.dbhelpers.SchoolDbHelper;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.example.dataframe.contracts.SchoolContract.Constants;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.UtilityMethods.getAlphaNumericRandomString;
import static com.example.dataframe.UtilityMethods.setRequestContent;

public class SchoolActivity extends AppCompatActivity {
    TextInputEditText schoolNameTxt, locationTxt, emisNumberTxt, foundingYearTxt;
    Spinner ownershipSpinner, regStatusSpinner, foundingBodySpinner, fundingSourceSpinner, schoolTypeSpinner, schoolLevelSpinner;
    Button submitBtn;
    String schoolName, location, emisNumber, foundingYear, foundingBody, fundingSource, schoolType, schoolLevel, ownership, regStatus;
    private GoogleProgressBar progressBar;
    private TextView popupSchoolNameTxt, popupSchoolTypeTxt, popupLocationTxt, popupEmisNumberTxt, popupSchoolLevelTxt, popupOwnershipTxt, popupRegStatus, popupFoundingYearTxt, popupFoundingBodyTxt, popupFundingSourceTxt;
    private Button popupConfirmBtn, popupCancelBtn;

    private Toolbar toolbar;

    SchoolDbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues;

    private String fieldError = "field cannot be empty";
    private String emptyString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schoolNameTxt = findViewById(R.id.school_name);
        locationTxt = findViewById(R.id.location);
        emisNumberTxt = findViewById(R.id.emis_number);
        foundingYearTxt = findViewById(R.id.founding_year);

        progressBar = findViewById(R.id.schools_progress_bar);

        foundingBodySpinner = findViewById(R.id.founding_body_spinner);
        fundingSourceSpinner = findViewById(R.id.funding_source_spinner);
        schoolTypeSpinner = findViewById(R.id.school_type_spinner);
        schoolLevelSpinner = findViewById(R.id.school_level_spinner);
        ownershipSpinner = findViewById(R.id.ownership_spinner);
        regStatusSpinner = findViewById(R.id.registration_status_spinner);

        submitBtn = findViewById(R.id.submit_school_characteristics);

        toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SchoolDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        if(checkNetworkConnection()){
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Please connect to the internet first", Toast.LENGTH_SHORT).show();
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schoolName = schoolNameTxt.getText().toString().trim();
                location = locationTxt.getText().toString().trim();
                emisNumber = emisNumberTxt.getText().toString().trim();
                foundingYear = foundingYearTxt.getText().toString().trim();
                foundingBody = foundingBodySpinner.getSelectedItem().toString().trim();

                if(fundingSourceSpinner.getSelectedItem().toString().equals("GOVERNMENT AIDED")){
                    fundingSource = "Government_Aided";
                }
                else if(fundingSourceSpinner.getSelectedItem().toString().equals("NON-GOVERNMENT AIDED")){
                    fundingSource = "Not_Government_Aided";
                }

                schoolType = schoolTypeSpinner.getSelectedItem().toString();

                schoolLevel = schoolLevelSpinner.getSelectedItem().toString().trim();

                ownership = ownershipSpinner.getSelectedItem().toString().trim();

                if(regStatusSpinner.getSelectedItem().toString().trim().equals("REGISTERED")){
                    regStatus = "Registered";
                }
                else if(regStatusSpinner.getSelectedItem().toString().trim().equals("NOT REGISTERED")){
                    regStatus = "NOT_Registered";
                }
                else if(regStatusSpinner.getSelectedItem().toString().trim().equals("LICENSED")){
                    regStatus = "Licensed";
                }

                //check for empty fields
                if(schoolName.equals(emptyString)){
                    schoolNameTxt.setError(fieldError);
                }
                else if(location.equals(emptyString)){
                    locationTxt.setError(fieldError);
                }
                else if(emisNumber.equals(emptyString)){
                    emisNumberTxt.setError(fieldError);
                }
                else if(foundingYear.equals(emptyString)){
                    foundingYearTxt.setError(fieldError);
                }
                else {
                    createCustomAlertDialog();
                }

            }
        });
    }

    private class PostSchoolCharacteristics extends AsyncTask<Void, Void, HashMap<String, String>> {

        String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/site/register";

        @Override
        protected HashMap<String, String> doInBackground(Void... voids) {
            HashMap<String, String> results = new HashMap<>();

            String responseMessage = "";
            try {
                responseMessage = httpPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String newRowId = String.valueOf(postToDatabase());

            results.put("urlResponse", responseMessage);
            results.put("databaseResponse", newRowId);

            Log.d("new row id", newRowId);

            return results;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> responses) {
            super.onPostExecute(responses);

            String urlResponse, databaseResponse;
            urlResponse = responses.get("urlResponse");
            databaseResponse = responses.get("databaseResponse");

            if(urlResponse.equalsIgnoreCase("OK")){
                createSuccessAlertDialog().show();
                clearAllFields();
            }
            else {
                createErrorAlertDialog().show();
            }

//            if(databaseResponse.equals("-1")){
//                createErrorAlertDialog("failed to save to database").show();
//            }
//            else {
//                createSuccessAlertDialog().show();
//            }
        }
    }

    private Boolean checkNetworkConnection(){
        boolean isConnected;
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        isConnected = networkInfo != null && networkInfo.isConnected();

        return isConnected;
    }

    private String httpPost(String myUrl) throws IOException, JSONException {
        URL url = new URL(myUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        JSONObject jsonObject = buildJsonObject();
        setRequestContent(conn, jsonObject);
        conn.connect();

        Log.d("getResponseMessage", conn.getResponseMessage()+"");

        return conn.getResponseMessage()+"";
    }

    private JSONObject buildJsonObject() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONObject deploymentSiteCategory = new JSONObject();
        deploymentSiteCategory.accumulate("id", getAlphaNumericRandomString(32));
        deploymentSiteCategory.accumulate("deploymentSiteCategory", schoolLevel);

        jsonObject.accumulate("code", emisNumber);
        jsonObject.accumulate("deploymentSiteCategory", deploymentSiteCategory);
        jsonObject.accumulate("deploymentSiteName", schoolName);
        jsonObject.accumulate("latitude","0.3236586");
        jsonObject.accumulate("location", location);
        jsonObject.accumulate("longitude", "32.6164403");
        jsonObject.accumulate("regNo", emisNumber);
        jsonObject.accumulate("schoolType", schoolType);
        jsonObject.accumulate("ownership", ownership);
        jsonObject.accumulate("registrationStatus", regStatus);
        jsonObject.accumulate("foundingYear", foundingYear);
        jsonObject.accumulate("foundingBody", foundingBody);
        jsonObject.accumulate("fundingSource", fundingSource);

        return jsonObject;
    }

    Long postToDatabase(){
        contentValues.put(Constants.SCHOOL_NAME, schoolName);
        contentValues.put(Constants.SCHOOL_TYPE, schoolType);
        contentValues.put(Constants.LOCATION, location);
        contentValues.put(Constants.EMIS_NUMBER, emisNumber);
        contentValues.put(Constants.SCHOOL_LEVEL, ownership);
        contentValues.put(Constants.REGISTRATION_STATUS, regStatus);
        contentValues.put(Constants.FOUNDING_YEAR, foundingYear);
        contentValues.put(Constants.FOUNDING_BODY, foundingBody);
        contentValues.put(Constants.FUNDING_SOURCE, fundingSource);

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    SweetAlertDialog createErrorAlertDialog(){
        return new SweetAlertDialog(SchoolActivity.this, SweetAlertDialog.ERROR_TYPE)
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
    }

    SweetAlertDialog createSuccessAlertDialog(){
        return new SweetAlertDialog(SchoolActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Details saved successfully")
                .setConfirmText("Done")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
    }

    void createCustomAlertDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.school_xtics_popup, viewGroup, false);

        //find views
        popupSchoolNameTxt = dialogView.findViewById(R.id.popup_school_name);
        popupSchoolTypeTxt = dialogView.findViewById(R.id.popup_school_type);
        popupLocationTxt = dialogView.findViewById(R.id.popup_location);
        popupEmisNumberTxt = dialogView.findViewById(R.id.popup_emis_number);
        popupSchoolLevelTxt = dialogView.findViewById(R.id.popup_school_level);
        popupOwnershipTxt = dialogView.findViewById(R.id.popup_ownership);
        popupRegStatus = dialogView.findViewById(R.id.popup_reg_status);
        popupFoundingYearTxt = dialogView.findViewById(R.id.popup_founding_year);
        popupFoundingBodyTxt = dialogView.findViewById(R.id.popup_founding_body);
        popupFundingSourceTxt = dialogView.findViewById(R.id.popup_funding_source);

        popupConfirmBtn = dialogView.findViewById(R.id.confirm_button);
        popupCancelBtn = dialogView.findViewById(R.id.cancel_button);

        //set text
        popupSchoolNameTxt.setText(schoolName);
        popupSchoolTypeTxt.setText(schoolType);
        popupLocationTxt.setText(location);
        popupEmisNumberTxt.setText(emisNumber);
        popupSchoolLevelTxt.setText(schoolLevel);
        popupOwnershipTxt.setText(ownership);
        popupRegStatus.setText(regStatus);
        popupFoundingYearTxt.setText(foundingYear);
        popupFoundingBodyTxt.setText(foundingBody);
        popupFundingSourceTxt.setText(fundingSource);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        popupConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(SchoolActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                new PostSchoolCharacteristics().execute();
                            }
                        })
                        .show();
            }
        });

        popupCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    void clearAllFields(){
        schoolNameTxt.setText("");
        locationTxt.setText("");
        emisNumberTxt.setText("");
        foundingYearTxt.setText("");
    }

}
