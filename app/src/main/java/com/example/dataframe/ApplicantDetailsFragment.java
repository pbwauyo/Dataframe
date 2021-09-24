package com.example.dataframe;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicantDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicantDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button nextBtn, submitBtn;
    private ImageView calenderImageView;
    private TextInputEditText dobTxt, surnameTxt, givenNameTxt, otherNamesTxt, previousNameTxt, placeOfBirthTxt, placeOfOriginTxt, indigenousCommunityTxt, clanTxt;
    private RadioGroup sexGrp;

    private TextView popupDobTxt, popupSurnameTxt, popupGivenNameTxt, popupOtherNamesTxt, popupPreviousNameTxt, popupSexTxt, popupPlaceOfBirthTxt, popupPlaceOfOriginTxt, popupIndigenousCommunityTxt, popupClanTxt;
    private Button yesBtn, noBtn;

    private String surname, givenName, otherNames, previousNames, dateOfBirth, sex, placeofBirth, placeOfOrigin, indigenousCommunity, clan;
    private final String emptyString = "", emptyFieldError = "field cannot be empty";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ApplicantDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplicantDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicantDetailsFragment newInstance(String param1, String param2) {
        ApplicantDetailsFragment fragment = new ApplicantDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applicant_details, container, false);
        nextBtn = view.findViewById(R.id.next);
        surnameTxt = view.findViewById(R.id.surname);
        givenNameTxt = view.findViewById(R.id.given_name);
        otherNamesTxt = view.findViewById(R.id.other_names);
        previousNameTxt = view.findViewById(R.id.previous_names);
        placeOfBirthTxt = view.findViewById(R.id.place_of_birth);
        placeOfOriginTxt = view.findViewById(R.id.place_of_origin);
        indigenousCommunityTxt = view.findViewById(R.id.indigenous_community);
        clanTxt = view.findViewById(R.id.clan);
        sexGrp = view.findViewById(R.id.sex_radio_grp);

        submitBtn = view.findViewById(R.id.submit);
        calenderImageView = view.findViewById(R.id.date_of_birth);
        dobTxt = view.findViewById(R.id.dob_txt);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewPager)getActivity().findViewById(R.id.applicant_viewpager)).setCurrentItem(1);
            }
        });

        dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextInputs();

                if(!areFieldsEmpty()){
                    displaySummaryDialog();
                }

            }
        });

        calenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
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
                                sweetAlertDialog.dismiss();
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
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

    void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dobTxt.setText(String.format(getString(R.string.date_string), dayOfMonth, month, year));
    }

    private void getTextInputs(){
        surname = surnameTxt.getText().toString().trim();
        givenName = givenNameTxt.getText().toString().trim();
        otherNames = otherNamesTxt.getText().toString().trim();
        previousNames = previousNameTxt.getText().toString().trim();
        dateOfBirth = dobTxt.getText().toString();
        sex = sexGrp.getCheckedRadioButtonId() == R.id.male_radio_btn ? "Male" : "Female";
        placeofBirth = placeOfBirthTxt.getText().toString().trim();
        placeOfOrigin = placeOfOriginTxt.getText().toString().trim();
        indigenousCommunity = indigenousCommunityTxt.getText().toString().trim();
        clan = clanTxt.getText().toString().trim();
    }

    private boolean areFieldsEmpty(){
        if(surname.equals(emptyString)){
            surnameTxt.setError(emptyFieldError);
            return true;
        }
        else if(givenName.equals(emptyString)){
            givenNameTxt.setError(emptyFieldError);
            return true;
        }
        else if(dateOfBirth.equals(emptyString)){
            dobTxt.setError(emptyFieldError);
            return true;
        }
        else if(placeofBirth.equals(emptyString)){
            placeOfBirthTxt.setError(emptyFieldError);
            return true;
        }
        else if(placeOfOrigin.equals(emptyString)){
            placeOfOriginTxt.setError(emptyFieldError);
            return true;
        }
        else if(clan.equals(emptyString)){
            clanTxt.setError(emptyFieldError);
            return true;
        }
        else {
            return false;
        }
    }

    private void clearAllFields(){
        surnameTxt.setText(emptyString);
        givenNameTxt.setText(emptyString);
        otherNamesTxt.setText(emptyString);
        previousNameTxt.setText(emptyString);
        dobTxt.setText(emptyString);
        placeOfBirthTxt.setText(emptyString);
        placeOfOriginTxt.setText(emptyString);
        indigenousCommunityTxt.setText(emptyString);
        clanTxt.setText(emptyString);
    }

    private void displaySummaryDialog(){
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.applicant_details_popup, viewGroup, false);

        popupSurnameTxt = view.findViewById(R.id.popup_surname);
        popupGivenNameTxt = view.findViewById(R.id.popup_given_name);
        popupOtherNamesTxt = view.findViewById(R.id.popup_other_names);
        popupPreviousNameTxt = view.findViewById(R.id.popup_previous_names);
        popupDobTxt = view.findViewById(R.id.popup_date_of_birth);
        popupSexTxt = view.findViewById(R.id.popup_sex);
        popupPlaceOfBirthTxt = view.findViewById(R.id.popup_place_of_birth);
        popupPlaceOfOriginTxt = view.findViewById(R.id.popup_place_of_origin);
        popupIndigenousCommunityTxt = view.findViewById(R.id.popup_indigenous_community);
        popupClanTxt = view.findViewById(R.id.popup_clan);
        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);

        popupSurnameTxt.setText(surname);
        popupGivenNameTxt.setText(givenName);
        popupOtherNamesTxt.setText(otherNames);
        popupPreviousNameTxt.setText(previousNames);
        popupDobTxt.setText(dateOfBirth);
        popupSexTxt.setText(sex);
        popupPlaceOfBirthTxt.setText(placeofBirth);
        popupPlaceOfOriginTxt.setText(placeOfOrigin);
        popupIndigenousCommunityTxt.setText(indigenousCommunity);
        popupClanTxt.setText(clan);

        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        final AlertDialog dialog = builder.create();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Make sure all fields are correct!")
                        .showCancelButton(true)
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                dialog.dismiss();
                                new SaveDetailsToDb().execute();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
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
}
