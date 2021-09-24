package com.example.dataframe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dataframe.dbhelpers.HIVDbHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.dataframe.contracts.HIVContract.Constants;

public class HIVActivity extends AppCompatActivity {
    private TextView maleHIVTxt, femaleHIVTxt, informationDisseminationTxt;
    private TextView popupMaleHIVTxt, popupFemaleHIVTxt, popupInformationDisseminationTxt;
    private Button submitBtn;
    private Button yesBtn, noBtn;
    private String emptyFieldErrorMsg = "field cannot be empty", emptyString = "";
    private String maleHIVCases, femaleHIVCases, informationDisseminationMtds;

    private ContentValues contentValues;
    private HIVDbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiv);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new HIVDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        findViews();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextInputs();

                if(maleHIVCases.equals(emptyString)){
                    maleHIVTxt.setError(emptyFieldErrorMsg);
                }
                else if (femaleHIVCases.equals(emptyString)){
                    femaleHIVTxt.setError(emptyFieldErrorMsg);
                }
                else if(informationDisseminationMtds.equals(emptyString)){
                    informationDisseminationTxt.setError(emptyFieldErrorMsg);
                }
                else {
                    displaySummaryDialog();
                }
            }
        });
    }

    void findDialogViews(View view){
        popupMaleHIVTxt = view.findViewById(R.id.popup_male_hiv_cases);
        popupFemaleHIVTxt = view.findViewById(R.id.popup_female_cases);
        popupInformationDisseminationTxt = view.findViewById(R.id.popup_information_dissemination);
        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);
    }

    void initialiseDialogViewsText(){
        popupMaleHIVTxt.setText(maleHIVCases);
        popupFemaleHIVTxt.setText(femaleHIVCases);
        popupInformationDisseminationTxt.setText(informationDisseminationMtds);
    }

    void findViews(){
        maleHIVTxt = findViewById(R.id.male_hiv);
        femaleHIVTxt = findViewById(R.id.female_hiv);
        informationDisseminationTxt = findViewById(R.id.information_dissemination);
        submitBtn = findViewById(R.id.submit);
    }

    void getTextInputs(){
        maleHIVCases = maleHIVTxt.getText().toString().trim();
        femaleHIVCases = femaleHIVTxt.getText().toString().trim();
        informationDisseminationMtds = informationDisseminationTxt.getText().toString().trim();
    }

    void clearAllFields(){
        maleHIVTxt.setText(emptyString);
        femaleHIVTxt.setText(emptyString);
        informationDisseminationTxt.setText(emptyString);
    }

    void displaySummaryDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_hiv, viewGroup, false);

        findDialogViews(view);
        initialiseDialogViewsText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(HIVActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("make sure all fields are correct")
                        .showCancelButton(true)
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                dialog.dismiss();
                                new PostDetails().execute();
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

    Long postDataToDatabase(){
        contentValues.put(Constants.MALES, maleHIVCases);
        contentValues.put(Constants.FEMALES, femaleHIVCases);
        contentValues.put(Constants.INFORMATION_DISSEMINATION, informationDisseminationMtds);

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    class PostDetails extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... voids) {
            String newRowId = String.valueOf(postDataToDatabase());
            Log.d("new row id", newRowId);

            return newRowId;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.equals("-1")){
                new SweetAlertDialog(HIVActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Details saved successfully")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                clearAllFields();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(HIVActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error!")
                        .setContentText("Failed to save details")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
    }


}
