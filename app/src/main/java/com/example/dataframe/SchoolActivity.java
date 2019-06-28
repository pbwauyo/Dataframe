package com.example.dataframe;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.UtilityMethods.getAlphaNumericRandomString;
import static com.example.dataframe.UtilityMethods.setRequestContent;

public class SchoolActivity extends AppCompatActivity {
    TextInputEditText schoolNameTxt, locationTxt, emisNumberTxt, foundingYearTxt;
    Spinner ownershipSpinner, regStatusSpinner, foundingBodySpinner, fundingSourceSpinner, schoolTypeSpinner, schoolLevelSpinner;
    Button submitBtn;
    String schoolName, location, emisNumber, foundingYear, foundingBody, fundingSource, schoolType, schoolLevel, ownership, regStatus;
    private GoogleProgressBar progressBar;

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
                                progressBar.setVisibility(View.VISIBLE);
                                new PostSchoolCharacteristics().execute();
                            }
                        })
                        .show();

                new PostSchoolCharacteristics().execute();
            }
        });
    }

    private class PostSchoolCharacteristics extends AsyncTask<Void, Void, String> {

        String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/site/register";

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
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if(response.equalsIgnoreCase("OK")){
                new SweetAlertDialog(SchoolActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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
                new SweetAlertDialog(SchoolActivity.this, SweetAlertDialog.ERROR_TYPE)
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

}
