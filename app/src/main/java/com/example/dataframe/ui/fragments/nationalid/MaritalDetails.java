package com.example.dataframe.ui.fragments.nationalid;


import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaritalDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaritalDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView popupSurnameTxt, popupGivenNameTxt, popupOtherNamesTxt, popupNumOfChildrenTxt, popupChildNameTxt, popupChildSexTxt, popupChildAgeTxt;
    private Button yesBtn, noBtn;
    private final String emptyString = "", emptyFieldError = "field cannot be empty";
    private String surname, givenName, otherNames, numOfChildren, childName, childSex, childAge;

    private LinearLayout childrensDetailsLayout;
    private ViewPager viewPager;

    private TextInputEditText surnameTxt, givenNameTxt, otherNamesTxt, numOfChildrenTxt, childNameTxt, childAgeTxt;

    private Button nextBtn, backBtn, submitBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialBetterSpinner childSexSpinner;
    private ArrayAdapter<String> arrayAdapter;


    public MaritalDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaritalDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static MaritalDetails newInstance(String param1, String param2) {
        MaritalDetails fragment = new MaritalDetails();
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
        final String[] sexes = getResources().getStringArray(R.array.sexes);
        viewPager = getActivity().findViewById(R.id.applicant_viewpager);

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, sexes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marital_details, container, false);
        nextBtn = view.findViewById(R.id.next);
        backBtn = view.findViewById(R.id.back);
        submitBtn = view.findViewById(R.id.submit);

        surnameTxt = view.findViewById(R.id.surname);
        givenNameTxt = view.findViewById(R.id.given_name);
        otherNamesTxt = view.findViewById(R.id.other_names);
        numOfChildrenTxt = view.findViewById(R.id.number_of_children);
        childrensDetailsLayout = view.findViewById(R.id.children_details);
        childNameTxt = view.findViewById(R.id.name);
        childAgeTxt = view.findViewById(R.id.age);

        childSexSpinner = view.findViewById(R.id.sex_spinner);
        childSexSpinner.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    class SaveDetailsToDb extends AsyncTask<Void, Void, String>{

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

    private void getTextInputs(){
        surname = surnameTxt.getText().toString().trim();
        givenName = givenNameTxt.getText().toString().trim();
        otherNames = otherNamesTxt.getText().toString().trim();
        numOfChildren = numOfChildrenTxt.getText().toString().trim();

        childName = childNameTxt.getText().toString().trim();
        childSex = childSexSpinner.getText().toString();
        childAge = childAgeTxt.getText().toString().trim();
    }

    private void clearAllFields(){
        surnameTxt.setText(emptyString);
        givenNameTxt.setText(emptyString);
        otherNamesTxt.setText(emptyString);
        numOfChildrenTxt.setText(emptyString);
        childNameTxt.setText(emptyString);
        childAgeTxt.setText(emptyString);
    }

    private void displayDetailsSummary(){
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_marital_details, viewGroup, false);

        popupSurnameTxt = view.findViewById(R.id.popup_surname);
        popupGivenNameTxt = view.findViewById(R.id.given_name);
        popupOtherNamesTxt = view.findViewById(R.id.other_names);
        popupNumOfChildrenTxt = view.findViewById(R.id.popup_num_of_children);
        popupChildNameTxt = view.findViewById(R.id.popup_name);
        popupChildSexTxt = view.findViewById(R.id.popup_sex);
        popupChildAgeTxt = view.findViewById(R.id.popup_age);
        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Make sure all fields are correct")
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

    private boolean areFieldsEmpty(){
        if(surname.equals(emptyString)){
            surnameTxt.setError(emptyFieldError);
            return true;
        }
        else if(givenName.equals(emptyString)){
            givenNameTxt.setError(emptyFieldError);
            return true;
        }
        else {
            return false;
        }
    }
}
