package com.example.dataframe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.dataframe.contracts.SportsContract.Constants;
import com.example.dataframe.dbhelpers.SportsDbHelper;

public class SportsActivity extends AppCompatActivity {
    private TextView sportsEquipmentTxt, sportsFacilityTxt, sneEquipmentTxt, extracurricularActivitiesTxt, expenditureTxt;
    private String sportsEquipment, sportsFacility, sneEquipment, extracurricularActivity, expenditure;

    private String emptyString="", emptyFieldErrorMsg="field cannot be empty" ;
    private Button submit;

    private ContentValues contentValues;
    private SQLiteDatabase db;
    private SportsDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        sportsEquipmentTxt = findViewById(R.id.sports_equipment);
        sportsFacilityTxt = findViewById(R.id.sports_facilities);
        sneEquipmentTxt = findViewById(R.id.sne_sports_equipment);
        extracurricularActivitiesTxt = findViewById(R.id.extracurricular_activities_participation);
        expenditureTxt = findViewById(R.id.expenditure);

        contentValues = new ContentValues();
        dbHelper = new SportsDbHelper(this);
        db = dbHelper.getWritableDatabase();

        submit= findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextInputs();

                if(sportsEquipment.equals(emptyString)){
                    sportsEquipmentTxt.setText(emptyFieldErrorMsg);
                }
                else if(sportsFacility.equals(emptyString)){
                    sportsFacilityTxt.setText(emptyFieldErrorMsg);
                }
                else if(sneEquipment.equals(emptyString)){
                    sneEquipmentTxt.setText(emptyFieldErrorMsg);
                }
                else if(extracurricularActivity.equals(emptyString)){
                    extracurricularActivitiesTxt.setText(emptyFieldErrorMsg);
                }
                else if(expenditure.equals(emptyString)){
                    expenditureTxt.setText(emptyFieldErrorMsg);
                }
                else {
                    displaySummaryDialog();
                }
            }
        });
    }

    private class PostToDatabase extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            String newRowId = String.valueOf(postToDatabase());
            Log.d("new row id", newRowId);

            return newRowId;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.equals("-1")){
                new SweetAlertDialog(SportsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Details saved successfully")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();

                clearAllFields();
            }
            else {
                new SweetAlertDialog(SportsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    private Long postToDatabase(){
        contentValues.put(Constants.SPORTS_EQUIPMENT, sportsEquipment);
        contentValues.put(Constants.SPORTS_FACILITY, sportsFacility);
        contentValues.put(Constants.SNE_SPORTS_EQUIPMENT, sneEquipment);
        contentValues.put(Constants.EXTRA_CURRICULAR_ACTIVITIES_PARTICIPATION, extracurricularActivity);
        contentValues.put(Constants.EXPENDITURE, expenditure);

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    private void clearAllFields(){
        sportsEquipmentTxt.setText(emptyString);
        sportsFacilityTxt.setText(emptyString);
        sneEquipmentTxt.setText(emptyString);
        extracurricularActivitiesTxt.setText(emptyString);
        expenditureTxt.setText(emptyString );
    }

    private void displaySummaryDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.sports_popup, viewGroup, false);

        TextView popupSportsEquipmentTxt = view.findViewById(R.id.popup_sports_equipment);
        TextView popupSportsFacilitiesTxt = view.findViewById(R.id.popup_sports_facilities);
        TextView popupSneEquipmentTxt = view.findViewById(R.id.popup_sne_sports_equipment);
        TextView popupExtracurricularTxt = view.findViewById(R.id.popup_extracurricular_activities);
        TextView popupExpenditureTxt = view.findViewById(R.id.popup_expenditure);

        Button yesBtn = view.findViewById(R.id.confirm_button);
        Button noBtn = view.findViewById(R.id.cancel_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        popupSportsEquipmentTxt.setText(sportsEquipment);
        popupSportsFacilitiesTxt.setText(sportsFacility);
        popupSneEquipmentTxt.setText(sneEquipment);
        popupExtracurricularTxt.setText(extracurricularActivity);
        popupExpenditureTxt.setText(expenditure);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(SportsActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                new PostToDatabase().execute();
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

        dialog.setView(view);
        dialog.dismiss();
        dialog.show();
    }

    void getTextInputs(){
        sportsEquipment = sportsEquipmentTxt.getText().toString().trim();
        sportsFacility = sportsFacilityTxt.getText().toString().trim();
        sneEquipment = sneEquipmentTxt.getText().toString().trim();
        extracurricularActivity = extracurricularActivitiesTxt.getText().toString().trim();
        expenditure = expenditureTxt.getText().toString().trim();
    }
}
