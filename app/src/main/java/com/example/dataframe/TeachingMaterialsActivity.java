package com.example.dataframe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dataframe.dbhelpers.TeachingMaterialsDbHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.dataframe.contracts.TeachingMaterialsContract.Constants;

public class TeachingMaterialsActivity extends AppCompatActivity {
    MaterialBetterSpinner subjectsSpinner;
    TextInputEditText textBooksTxt, teachingGuidesTxt, classPeriodsTxt;
    Button submitBtn;
    int numberOfTextBooks, numberOfTeachingGuides, classPeriods;
    String subject;
    TeachingMaterialsDbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues;
    SweetAlertDialog warningAlertDialog, errorAlertDialog, successAlertDialog;

    private TextView popupSubjectTxt, popupNumOfTextBksTxt, popupTeachingGuidesTxt, popupClassPeriodsTxt;
    private Button yesBtn, noBtn;

    private String emptyString = "", emptyFieldError = "field cannot be empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_materials);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        subjectsSpinner = findViewById(R.id.choose_subject);
        textBooksTxt = findViewById(R.id.number_of_text_books);
        teachingGuidesTxt = findViewById(R.id.number_of_teaching_guides);
        classPeriodsTxt = findViewById(R.id.class_periods);
        submitBtn = findViewById(R.id.submit);

        final String[] subjects = getResources().getStringArray(R.array.subjects);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, subjects);
        subjectsSpinner.setAdapter(adapter);
        dbHelper = new TeachingMaterialsDbHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        warningAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                 .setTitleText("Are you sure?")
                                 .setConfirmText("Yes")
                                 .setCancelText("No")
                                 .showCancelButton(true)
                                 .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                     @Override
                                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                                         sweetAlertDialog.dismissWithAnimation();
                                         new PostToCacheDatabase().execute();
                                     }
                                 })
                                 .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                     @Override
                                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                                         sweetAlertDialog.dismissWithAnimation();
                                     }
                                 });

        errorAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                               .setTitleText("Error while saving")
                               .setContentText("please try again!")
                               .setConfirmText("Ok")
                               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                   @Override
                                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                                       sweetAlertDialog.dismissWithAnimation();
                                   }
                               });

        successAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                 .setTitleText("Success!")
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
                if(String.valueOf(numberOfTextBooks).equals(emptyString)){
                    textBooksTxt.setError(emptyFieldError);
                }
                else if(String.valueOf(numberOfTeachingGuides).equals(emptyString)){
                    teachingGuidesTxt.setError(emptyFieldError);
                }
                else if(String.valueOf(classPeriods).equals(emptyString)){
                    classPeriodsTxt.setError(emptyFieldError);
                }
                else {
                    displaySummaryDialog();
                }

            }
        });
    }

    class PostToCacheDatabase extends AsyncTask<Void, Void, Long>{

        @Override
        protected Long doInBackground(Void... aVoid) {
            subject = subjectsSpinner.getText().toString();
            numberOfTextBooks = Integer.parseInt(textBooksTxt.getText().toString());
            numberOfTeachingGuides = Integer.parseInt(teachingGuidesTxt.getText().toString());
            classPeriods = Integer.parseInt(classPeriodsTxt.getText().toString());

            contentValues.put(Constants.SUBJECT, subject);
            contentValues.put(Constants.TEXT_BOOK, numberOfTextBooks);
            contentValues.put(Constants.TEACHING_GUIDE, numberOfTeachingGuides);
            contentValues.put(Constants.CLASS_PERIOD, classPeriods);

            return db.insert(Constants.TABLE_NAME, null, contentValues);
        }

        @Override
        protected void onPostExecute(Long newRowId) {
            super.onPostExecute(newRowId);
            Log.d("new row id", String.valueOf(newRowId));

            if(newRowId == -1){
                errorAlertDialog.show();
            }
            else {
                clearAllFields();
                successAlertDialog.show();
            }

        }
    }

    void displaySummaryDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.teaching_materials_popup, viewGroup, false);

        popupSubjectTxt = view.findViewById(R.id.popup_subject);
        popupNumOfTextBksTxt = view.findViewById(R.id.popup_num_of_textbooks);
        popupTeachingGuidesTxt = view.findViewById(R.id.popup_num_of_teaching_guides);
        popupClassPeriodsTxt = view.findViewById(R.id.popup_class_periods);

        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        popupSubjectTxt.setText(subject);
        popupNumOfTextBksTxt.setText(String.valueOf(numberOfTextBooks));
        popupTeachingGuidesTxt.setText(String.valueOf(numberOfTeachingGuides));
        popupClassPeriodsTxt.setText(String.valueOf(classPeriods));

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningAlertDialog.show();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    void clearAllFields(){
        textBooksTxt.setText(emptyString);
        teachingGuidesTxt.setText(emptyString);
        classPeriodsTxt.setText(emptyString);
    }
}
