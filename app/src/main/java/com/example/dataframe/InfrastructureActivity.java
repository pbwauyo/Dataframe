package com.example.dataframe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dataframe.dbhelpers.InfrastructureDbHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.UtilityMethods.getAlphaNumericRandomString;
import static com.example.dataframe.UtilityMethods.setRequestContent;
import static com.example.dataframe.contracts.InfrastructureContract.Constants;

public class InfrastructureActivity extends AppCompatActivity {
    private TextInputEditText infrastructureNumberTxt, employeeNumberTxt;
    private RadioGroup inUseGrp;
    private MaterialBetterSpinner chooseInfrastructureSpinner, infrastructureTypeSpinner, completionStatusSpinner;
    private TextView infrastructureTxt;

    private String schoolId, infrastructureId, infrastructureName, infrastructureNumber, infrastructureType, completionStatus, inUse, employeeNumber;

    private TextView popupInfrastructureNameTxt, popupInfrastructureNumberTxt, popInfrastructureTypeTxt, popupCompletionStatusTxt, popupInUseTxt, popupNumberOfEmployeesTxt;
    private Button yesBtn, noBtn;

    private InfrastructureDbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues;

    private String emptyFieldError = "Field cannot be empty", emptyString = "";

    private Button submitBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrastructure);
        final String[] infrastructureArray = getResources().getStringArray(R.array.infrastructures);
        final String[] typeArray = getResources().getStringArray(R.array.infrastructure_types);
        final String[] completionStatuses = getResources().getStringArray(R.array.completion_statuses);

        submitBtn = findViewById(R.id.submit_infrastructure_btn);
        progressBar = findViewById(R.id.infrastructure_progress_bar);

        dbHelper = new InfrastructureDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        schoolId = getIntent().getStringExtra("school_id");

        chooseInfrastructureSpinner = findViewById(R.id.infrastructure_spinner);

        infrastructureNumberTxt = findViewById(R.id.number_of_items);
        employeeNumberTxt = findViewById(R.id.number_of_employees);

        infrastructureTypeSpinner = findViewById(R.id.infrastructure_type_spinner);
        completionStatusSpinner = findViewById(R.id.completion_status_spinner);
        inUseGrp = findViewById(R.id.in_use_radio_grp);

        ArrayAdapter<String> infrastructureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, infrastructureArray);
        chooseInfrastructureSpinner.setAdapter(infrastructureAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeArray);
        infrastructureTypeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, completionStatuses);
        completionStatusSpinner.setAdapter(statusAdapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infrastructureName = chooseInfrastructureSpinner.getText().toString().trim();
                infrastructureNumber = infrastructureNumberTxt.getText().toString().trim();
                infrastructureType = infrastructureTypeSpinner.getText().toString().trim();
                completionStatus = completionStatusSpinner.getText().toString().trim();
                employeeNumber = employeeNumberTxt.getText().toString().trim();

                if(inUseGrp.getCheckedRadioButtonId() == R.id.yes_radio_btn){
                    inUse = "YES";
                }
                else if(inUseGrp.getCheckedRadioButtonId() == R.id.no_radio_btn){
                    inUse = "NO";
                }

                //check for empty fields
                if(infrastructureNumber.equals(emptyString)){
                    infrastructureNumberTxt.setError(emptyFieldError);
                }
                else if(employeeNumber.equals(emptyString)){
                    employeeNumberTxt.setError(emptyString);
                }
                else {
                    displaySummaryDialog();
                }


            }
        });

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

    JSONObject buildJsonObject() throws JSONException{
        JSONObject jsonObject = new JSONObject();

        JSONObject item = new JSONObject();
        item.accumulate("id", getAlphaNumericRandomString(15));
        item.accumulate("itemName", infrastructureName);

        JSONObject deploymentSite = new JSONObject();
        deploymentSite.accumulate("id", schoolId);

        JSONObject employee = new JSONObject();
        employee.accumulate("employeeNumber", employeeNumber);

        jsonObject.accumulate("item", item);
        jsonObject.accumulate("count", infrastructureNumber);
        jsonObject.accumulate("infrastructureType", infrastructureType);
        jsonObject.accumulate("completionStatus", completionStatus);
        jsonObject.accumulate("inUse", inUse);
        jsonObject.accumulate("deploymentSite", deploymentSite);
        jsonObject.accumulate("employee", employee);

        return jsonObject;
    }

    class PostInfrastructureData extends AsyncTask<Void, Void, HashMap<String, String>> {
        private final String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/infrastructure/register";

        @Override
        protected HashMap<String, String> doInBackground(Void... voids) {
            String responseMessage = "";
            HashMap<String, String> responses = new HashMap<>();

            try {
                responseMessage = httpPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String newRowId = String.valueOf(postValuesToDatabase());
            responses.put("urlResponse", responseMessage);
            responses.put("databaseResponse", newRowId);
            Log.d("new row id", newRowId);

            return responses;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> responses) {
            super.onPostExecute(responses);
            progressBar.setVisibility(View.GONE);

            String urlResponse, databaseResponse;
            urlResponse = responses.get("urlResponse");
            databaseResponse = responses.get("databaseResponse");

            if (urlResponse.equalsIgnoreCase("OK")) {
                createSuccessAlertDialog().show();
                clearAllFields();
            } else {
                createErrorAlertDialog().show();
            }

//            if(databaseResponse.equals("-1")){
//                createErrorAlertDialog().show();
//            }
//            else {
//                createSuccessAlertDialog().show();
//            }
        }
    }

    long postValuesToDatabase(){
        contentValues.put(Constants.INFRASTRUCTURE_NAME, infrastructureName);
        contentValues.put(Constants.INFRASTRUCTURE_NUMBER, Integer.parseInt(infrastructureNumber));
        contentValues.put(Constants.INFRASTRUCTURE_TYPE, infrastructureType);
        contentValues.put(Constants.COMPLETION_STATUS, completionStatus);
        contentValues.put(Constants.INUSE, inUse);
        contentValues.put(Constants.EMPLOYEES_TOTAL, Integer.parseInt(employeeNumber));

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    SweetAlertDialog createSuccessAlertDialog(){
        return new SweetAlertDialog(InfrastructureActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success")
                .setContentText("Details saved successfully")
                .setConfirmText("Done")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
    }

    SweetAlertDialog createErrorAlertDialog(){
        return new SweetAlertDialog(InfrastructureActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
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

    private void displaySummaryDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.infrastructure_popup, viewGroup, false);

        popupInfrastructureNameTxt = view.findViewById(R.id.popup_infrastructure_name);
        popupInfrastructureNumberTxt = view.findViewById(R.id.popup_infrastructure_number);
        popInfrastructureTypeTxt = view.findViewById(R.id.popup_infrastructure_type);
        popupCompletionStatusTxt = view.findViewById(R.id.popup_completion_status);
        popupInUseTxt = view.findViewById(R.id.popup_in_use);
        popupNumberOfEmployeesTxt = view.findViewById(R.id.popup_number_of_employees);

        popupInfrastructureNameTxt.setText(infrastructureName);
        popupInfrastructureNumberTxt.setText(infrastructureNumber);
        popInfrastructureTypeTxt.setText(infrastructureType);
        popupCompletionStatusTxt.setText(completionStatus);
        popupInUseTxt.setText(inUse);
        popupNumberOfEmployeesTxt.setText(employeeNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(InfrastructureActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Make sure all fields are correct")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                 sweetAlertDialog.dismissWithAnimation();
                                 progressBar.setVisibility(View.VISIBLE);
                                 dialog.dismiss();
                                 new PostInfrastructureData().execute();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void clearAllFields(){
        infrastructureNumberTxt.setText(emptyString);
        infrastructureNumberTxt.setText(emptyString);
        inUseGrp.clearCheck();
    }
}
