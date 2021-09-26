package com.example.dataframe.ui.fragments.nationalid;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dataframe.R;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParentsDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParentsDetails extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView calenderImageView;
    private TextInputEditText  fathersSurnameTxt, fathersGivenNameTxt, fathersOtherNameTxt, mothersSurnameTxt, mothersGivenNameTxt, mothersOtherNameTxt, previousNationalityTxt, passportNumberTxt, placeOfIssueTxt, dateOfIssueTxt, issuingAuthorityTxt, drivingLicenseTxt, tinNumberTxt;

    private TextView popupFathersSurnameTxt, popupFathersGivenNameTxt, popupFathersOtherNameTxt, popupMothersSurnameTxt, popupMothersGivenNameTxt, popupMothersOtherNameTxt, popupPreviousNationalityTxt, popupPassportNumberTxt, popupPlaceOfIssueTxt, popupDateOfIssueTxt, popupIssuingAuthorityTxt, popupDrivingLicenseTxt, popupTinNumberTxt;

    private String  fathersSurname, fathersGivenName, fathersOtherName, mothersSurname, mothersGivenName, mothersOtherName, previousNationality, passportNumber, placeOfIssue, dateOfIssue, issuingAuthority, drivingLicense, tinNumber;
    private String emptyString = "", emptyFieldError = "field cannot be empty";

    private Button yesBtn, noBtn;

    private Button backBtn, submitBtn;

    public ParentsDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParentsDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ParentsDetails newInstance(String param1, String param2) {
        ParentsDetails fragment = new ParentsDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parents_details, container, false);
        calenderImageView = view.findViewById(R.id.date_of_issue);

        fathersSurnameTxt = view.findViewById(R.id.father_surname);
        fathersGivenNameTxt = view.findViewById(R.id.father_given_name);
        fathersOtherNameTxt = view.findViewById(R.id.father_other_names);
        mothersSurnameTxt = view.findViewById(R.id.mother_surname);
        mothersGivenNameTxt = view.findViewById(R.id.mother_given_name);
        mothersOtherNameTxt = view.findViewById(R.id.mother_other_name);
        previousNationalityTxt = view.findViewById(R.id.previous_nationality);
        passportNumberTxt = view.findViewById(R.id.passport_number);
        placeOfIssueTxt = view.findViewById(R.id.place_of_issue);
        dateOfIssueTxt = view.findViewById(R.id.date_of_issue_txt);
        issuingAuthorityTxt = view.findViewById(R.id.issuing_authority);
        drivingLicenseTxt = view.findViewById(R.id.driving_license);
        tinNumberTxt = view.findViewById(R.id.tin_number);

        backBtn = view.findViewById(R.id.back);
        submitBtn = view.findViewById(R.id.submit);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.applicant_viewpager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextInputs();
                if(!areFieldsEmpty()){
                    displayDetailsSummary();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(),this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateOfIssueTxt.setText(String.format(getString(R.string.date_string), dayOfMonth, month, year));
    }

    private class SaveDetailsToDb extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return "1";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.equals("-1")){
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
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
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
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

    private void clearAllFields(){
        fathersSurnameTxt.setText(emptyString);
        fathersGivenNameTxt.setText(emptyString);
        fathersOtherNameTxt.setText(emptyString);
        mothersSurnameTxt.setText(emptyString);
        mothersGivenNameTxt.setText(emptyString);
        mothersOtherNameTxt.setText(emptyString);
        previousNationalityTxt.setText(emptyString);
        passportNumberTxt.setText(emptyString);
        placeOfIssueTxt.setText(emptyString);
        dateOfIssueTxt.setText(emptyString);
        issuingAuthorityTxt.setText(emptyString);
        drivingLicenseTxt.setText(emptyString);
        tinNumberTxt.setText(emptyString);
    }

    private void getTextInputs(){
        fathersSurname = fathersSurnameTxt.getText().toString().trim();
        fathersGivenName = fathersGivenNameTxt.getText().toString().trim();
        fathersOtherName = fathersOtherNameTxt.getText().toString().trim();
        mothersSurname = mothersSurnameTxt.getText().toString().trim();
        mothersGivenName = mothersGivenNameTxt.getText().toString().trim();
        mothersOtherName = mothersOtherNameTxt.getText().toString().trim();
        previousNationality = previousNationalityTxt.getText().toString().trim();
        passportNumber = passportNumberTxt.getText().toString().trim();
        placeOfIssue = placeOfIssueTxt.getText().toString().trim();
        dateOfIssue = dateOfIssueTxt.getText().toString().trim();
        issuingAuthority = issuingAuthorityTxt.getText().toString().trim();
        drivingLicense = drivingLicenseTxt.getText().toString().trim();
        tinNumber = tinNumberTxt.getText().toString().trim();
    }

    private boolean areFieldsEmpty(){
        if(fathersSurname.equals(emptyString)){
            fathersSurnameTxt.setError(emptyFieldError);
            return true;
        }
        else if(fathersGivenName.equals(emptyString)){
            fathersGivenNameTxt.setError(emptyFieldError);
            return true;
        }
        else if(mothersSurname.equals(emptyString)){
            mothersSurnameTxt.setError(emptyFieldError);
            return true;
        }
        else if(mothersGivenName.equals(emptyString)){
            mothersGivenNameTxt.setError(emptyFieldError);
            return true;
        }
        else if(placeOfIssue.equals(emptyString)){
            placeOfIssueTxt.setError(emptyFieldError);
            return true;
        }
        else if(dateOfIssue.equals(emptyString)){
            dateOfIssueTxt.setError(emptyFieldError);
            return true;
        }
        else if(issuingAuthority.equals(emptyString)){
            issuingAuthorityTxt.setError(emptyFieldError);
            return true;
        }
        else{
            return false;
        }
    }

    private void displayDetailsSummary(){
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.popup_parent_details, viewGroup, false);

        findDialogViews(view);

        popupFathersSurnameTxt.setText(fathersSurname);
        popupFathersGivenNameTxt.setText(fathersGivenName);
        popupFathersOtherNameTxt.setText(fathersOtherName);
        popupMothersSurnameTxt.setText(mothersSurname);
        popupMothersGivenNameTxt.setText(mothersGivenName);
        popupMothersOtherNameTxt.setText(mothersOtherName);
        popupPreviousNationalityTxt.setText(previousNationality);
        popupPassportNumberTxt.setText(passportNumber);
        popupPlaceOfIssueTxt.setText(placeOfIssue);
        popupDateOfIssueTxt.setText(dateOfIssue);
        popupIssuingAuthorityTxt.setText(issuingAuthority);
        popupDrivingLicenseTxt.setText(drivingLicense);
        popupTinNumberTxt.setText(tinNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Make sure all fields are correct")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                dialog.dismiss();
                                new SaveDetailsToDb().execute();
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

    private void findDialogViews(View view){
        popupFathersSurnameTxt = view.findViewById(R.id.popup_fathers_surname);
        popupFathersGivenNameTxt = view.findViewById(R.id.popup_fathers_given_name);
        popupFathersOtherNameTxt = view.findViewById(R.id.popup_mothers_other_name);
        popupMothersSurnameTxt = view.findViewById(R.id.popup_mothers_surname);
        popupMothersGivenNameTxt = view.findViewById(R.id.popup_mothers_given_name);
        popupMothersOtherNameTxt = view.findViewById(R.id.mother_other_name);
        popupPreviousNationalityTxt = view.findViewById(R.id.popup_previous_nationality);
        popupPassportNumberTxt = view.findViewById(R.id.popup_passport_number);
        popupPlaceOfIssueTxt = view.findViewById(R.id.popup_place_of_issue);
        popupDateOfIssueTxt = view.findViewById(R.id.popup_date_of_issue);
        popupIssuingAuthorityTxt = view.findViewById(R.id.popup_issuing_authority);
        popupDrivingLicenseTxt = view.findViewById(R.id.popup_driving_license_number);
        popupTinNumberTxt = view.findViewById(R.id.popup_tin_number);

        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);
    }


}
