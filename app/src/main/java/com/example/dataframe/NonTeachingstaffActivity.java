package com.example.dataframe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.dataframe.dbhelpers.NonTeachingStaffDbHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import static com.example.dataframe.contracts.NonTeachingStaffContract.Constants;

public class NonTeachingstaffActivity extends AppCompatActivity {
    private TextView popupMaleCleanersTxt, popupFemaleCleanersTxt, popupMaleSecurityTxt, popupFemaleSecurityTxt, popupMaleCooksTxt, popupFemaleCooksTxt, popupMaleLibrariansTxt, popupFemaleLibrariansTxt;
    private Button popupConfirmBtn, popupCancelBtn, submitBtn;

    private String maleCleaners, femaleCleaners, maleSecurity, femaleSecurity, maleCooks, femaleCooks, maleLibrarians, femaleLibrarians;
    private final String emptyString = "", fieldEmptyErrorMsg="field cannot be empty";

    private TextView maleCleanersTxt, femaleCleanersTxt, maleSecurityTxt, femaleSecurityTxt, maleCooksTxt, femaleCooksTxt, maleLibrariansTxt, femaleLibrariansTxt;

    private ContentValues contentValues;
    private SQLiteDatabase db;
    private NonTeachingStaffDbHelper dbHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_teachingstaff);

        dbHelper = new NonTeachingStaffDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        maleCleanersTxt = findViewById(R.id.male_cleaners);
        femaleCleanersTxt = findViewById(R.id.female_cleaners);
        maleSecurityTxt = findViewById(R.id.male_security);
        femaleSecurityTxt = findViewById(R.id.female_security);
        maleCooksTxt = findViewById(R.id.male_cooks);
        femaleCooksTxt = findViewById(R.id.female_cooks);
        maleLibrariansTxt = findViewById(R.id.male_librarians);
        femaleLibrariansTxt = findViewById(R.id.female_librarians);
        submitBtn = findViewById(R.id.submit);

        toolbar = findViewById(R.id.menu_toolbar);

        setSupportActionBar(toolbar);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleCleaners = maleCleanersTxt.getText().toString().trim();
                femaleCleaners = femaleCleanersTxt.getText().toString().trim();
                maleSecurity = maleSecurityTxt.getText().toString().trim();
                femaleSecurity = femaleSecurityTxt.getText().toString().trim();
                maleCooks = maleCooksTxt.getText().toString().trim();
                femaleCooks = femaleCooksTxt.getText().toString().trim();
                maleLibrarians = maleLibrariansTxt.getText().toString().trim();
                femaleLibrarians = femaleLibrariansTxt.getText().toString().trim();

                if(maleCleaners.equals(emptyString)){
                    maleCleanersTxt.setError(fieldEmptyErrorMsg);
                }
                else if(femaleCleaners.equals(emptyString)){
                    femaleCleanersTxt.setError(fieldEmptyErrorMsg);
                }

                else if(maleSecurity.equals(emptyString)){
                    maleSecurityTxt.setError(fieldEmptyErrorMsg);
                }

                else if(femaleSecurity.equals(emptyString)){
                    femaleSecurityTxt.setError(fieldEmptyErrorMsg);
                }

                else if(maleCooks.equals(emptyString)){
                    maleCooksTxt.setError(fieldEmptyErrorMsg);
                }

                else if(femaleCooks.equals(emptyString)){
                    femaleCooksTxt.setError(fieldEmptyErrorMsg);
                }

                else if(maleLibrarians.equals(emptyString)){
                    maleLibrariansTxt.setError(fieldEmptyErrorMsg);
                }

                else if(femaleLibrarians.equals(emptyString)){
                    femaleLibrariansTxt.setError(fieldEmptyErrorMsg);
                }

                else {
                    showConfirmDialog();
                }
            }
        });

    }

    class PostToDatabase extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            String newRowId = String.valueOf(saveToDatabase());
            Log.d("new row id", newRowId);

            return newRowId;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!result.equals("-1")){
                new SweetAlertDialog(NonTeachingstaffActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Details saved successfully")
                        .setConfirmText("Done")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();

                clearAllFields();
            }
            else{
                new SweetAlertDialog(NonTeachingstaffActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error!")
                        .setContentText("error saving details")
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

    void showConfirmDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.non_teaching_staff_popup, viewGroup, false);

        //find views
        popupMaleCleanersTxt = view.findViewById(R.id.popup_male_cleaners);
        popupFemaleCleanersTxt = view.findViewById(R.id.popup_female_cleaners);
        popupMaleSecurityTxt = view.findViewById(R.id.popup_security_male);
        popupFemaleSecurityTxt = view.findViewById(R.id.popup_security_female);
        popupMaleCooksTxt = view.findViewById(R.id.popup_cooks_male);
        popupFemaleCooksTxt = view.findViewById(R.id.popup_cooks_female);
        popupMaleLibrariansTxt = view.findViewById(R.id.popup_librarian_male);
        popupFemaleLibrariansTxt = view.findViewById(R.id.popup_librarian_female);
        popupConfirmBtn = view.findViewById(R.id.confirm_button);
        popupCancelBtn = view.findViewById(R.id.cancel_button);

        //set text
        popupMaleCleanersTxt.setText(maleCleaners);
        popupFemaleCleanersTxt.setText(femaleCleaners);
        popupMaleSecurityTxt.setText(maleSecurity);
        popupFemaleSecurityTxt.setText(femaleSecurity);
        popupMaleCooksTxt.setText(maleCooks);
        popupFemaleCooksTxt.setText(femaleCooks);
        popupMaleLibrariansTxt.setText(maleLibrarians);
        popupFemaleLibrariansTxt.setText(femaleLibrarians);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();

        //set listeners
        popupCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popupConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(NonTeachingstaffActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                //progressBar.setVisibility(View.VISIBLE);
                                new PostToDatabase().execute();
                            }
                        })
                        .show();
            }
        });

        dialog.show();
    }

    void clearAllFields(){

        maleCleanersTxt.setText(emptyString);
        femaleCleanersTxt.setText(emptyString);
        maleSecurityTxt.setText(emptyString);
        femaleSecurityTxt.setText(emptyString);
        maleCooksTxt.setText(emptyString);
        femaleCooksTxt.setText(emptyString);
        maleLibrariansTxt.setText(emptyString);
        femaleLibrariansTxt.setText(emptyString);
    }

    Long saveToDatabase(){
        contentValues.put(Constants.MALE_CLEANERS, maleCleaners);
        contentValues.put(Constants.FEMALE_CLEANERS, femaleCleaners);
        contentValues.put(Constants.MALE_SECURITY, maleSecurity);
        contentValues.put(Constants.FEMALE_SECURITY, femaleSecurity);
        contentValues.put(Constants.MALE_COOKS, maleCooks);
        contentValues.put(Constants.FEMALE_COOKS, femaleCooks);
        contentValues.put(Constants.MALE_LIBRARIANS, maleLibrarians);
        contentValues.put(Constants.FEMALE_LIBRARIANS, femaleLibrarians);

        return db.insert(Constants.TABLE_NAME, null, contentValues);
    }
}
