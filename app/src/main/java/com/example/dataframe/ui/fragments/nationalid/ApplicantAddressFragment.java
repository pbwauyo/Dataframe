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
import android.widget.TextView;

//import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicantAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicantAddressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button nextBtn;

//    private MaterialBetterSpinner educationLevelSpinner, maritalStatusSpinner;
    private ArrayAdapter<String> educationLevelAdapter, maritalStatusAdapter;

    private TextInputEditText postalAddressTxt, villageTxt, parishTxt, subCountyTxt, countyTxt, districtTxt, occupationTxt, professionTxt;

    private TextView popupPostalAddressTxt, popupVillageTxt, popupParishTxt, popupSubCountyTxt, popupCountyTxt, popupDistrictTxt, popupOccupationTxt, popupProfessionTxt, popupEducationLevelTxt, popupMaritalStatusTxt;
    private String postalAddress, village, parish, subCounty, county, district, occupation, profession, educationLevel, maritalStatus;

    private final String emptyString = "", emptyFieldError = "field cannot be empty";

    private Button backBtn, submitTxt;
    private ViewPager viewPager;

    private Button yesBtn, noBtn;

    public ApplicantAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplicantAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicantAddressFragment newInstance(String param1, String param2) {
        ApplicantAddressFragment fragment = new ApplicantAddressFragment();
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

        viewPager = getActivity().findViewById(R.id.applicant_viewpager);

        final String[] educationLevels = getResources().getStringArray(R.array.education_levels);
        final String[] maritalStatuses = getResources().getStringArray(R.array.marital_statuses);

        educationLevelAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, educationLevels);
        maritalStatusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, maritalStatuses);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applicant_adress, container, false);
        nextBtn = view.findViewById(R.id.next);
        backBtn = view.findViewById(R.id.back);
        submitTxt = view.findViewById(R.id.submit);

        postalAddressTxt = view.findViewById(R.id.postal_address);
        villageTxt = view.findViewById(R.id.village);
        parishTxt = view.findViewById(R.id.parish);
        subCountyTxt = view.findViewById(R.id.sub_county);
        countyTxt = view.findViewById(R.id.county);
        districtTxt = view.findViewById(R.id.district);
        occupationTxt = view.findViewById(R.id.occupation);
        professionTxt = view.findViewById(R.id.profession);

//        educationLevelSpinner = view.findViewById(R.id.education_level_spinner);
//        maritalStatusSpinner = view.findViewById(R.id.marital_status_spinner);

//        educationLevelSpinner.setAdapter(educationLevelAdapter);
//        maritalStatusSpinner.setAdapter(maritalStatusAdapter);

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

        submitTxt.setOnClickListener(new View.OnClickListener() {
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
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Success!")
//                        .setContentText("Details saved successfully")
//                        .setConfirmText("Ok")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismiss();
//                                clearAllFields();
//                            }
//                        })
//                        .show();
            }
            else {
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Error!")
//                        .setContentText("Failed to save details")
//                        .setConfirmText("Ok")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismiss();
//                            }
//                        })
//                        .show();
            }
        }
    }

    private void getTextInputs(){
        postalAddress = postalAddressTxt.getText().toString().trim();
        village = villageTxt.getText().toString().trim();
        parish = parishTxt.getText().toString().trim();
        subCounty = subCountyTxt.getText().toString().trim();
        county = countyTxt.getText().toString().trim();
        district = districtTxt.getText().toString().trim();
        occupation = occupationTxt.getText().toString().trim();
        profession = professionTxt.getText().toString().trim();
//        educationLevel = educationLevelSpinner.getText().toString();
//        maritalStatus = maritalStatusSpinner.getText().toString();
    }

    private void clearAllFields(){
        postalAddressTxt.setText(emptyString);
        villageTxt.setText(emptyString);
        parishTxt.setText(emptyString);
        subCountyTxt.setText(emptyString);
        countyTxt.setText(emptyString);
        districtTxt.setText(emptyString);
        occupationTxt.setText(emptyString);
        professionTxt.setText(emptyString);
    }

    private boolean areFieldsEmpty(){
        if(village.equals(emptyString)){
            villageTxt.setError(emptyFieldError);
            return true;
        }
        else if(parish.equals(emptyString)){
            parishTxt.setError(emptyFieldError);
            return true;
        }
        else if(subCounty.equals(emptyString)){
            subCountyTxt.setError(emptyFieldError);
            return true;
        }
        else if(county.equals(emptyString)){
            countyTxt.setError(emptyFieldError);
            return true;
        }
        else if(district.equals(emptyString)){
            districtTxt.setError(emptyFieldError);
            return true;
        }
        else if (profession.equals(emptyString)){
            professionTxt.setError(emptyString);
            return true;
        }
        else {
            return false;
        }
    }

    private void displayDetailsSummary(){
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_applicant_address, viewGroup, false);

        findDialogViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        popupPostalAddressTxt.setText(postalAddress);
        popupVillageTxt.setText(village);
        popupParishTxt.setText(parish);
        popupSubCountyTxt.setText(subCounty);
        popupCountyTxt.setText(county);
        popupDistrictTxt.setText(district);
        popupOccupationTxt.setText(occupation);
        popupProfessionTxt.setText(profession);
        popupEducationLevelTxt.setText(educationLevel);
        popupMaritalStatusTxt.setText(maritalStatus);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Are you sure?")
//                        .setContentText("Make sure all fields are correct")
//                        .showCancelButton(true)
//                        .setConfirmText("Yes")
//                        .setCancelText("No")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismissWithAnimation();
//                                dialog.dismiss();
//                                new SaveDetailsToDb().execute();
//                            }
//                        })
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
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
        popupPostalAddressTxt = view.findViewById(R.id.popup_postal_address);
        popupVillageTxt = view.findViewById(R.id.popup_village);
        popupParishTxt = view.findViewById(R.id.popup_parish);
        popupSubCountyTxt = view.findViewById(R.id.popup_sub_county);
        popupCountyTxt = view.findViewById(R.id.popup_county);
        popupDistrictTxt = view.findViewById(R.id.popup_district);
        popupOccupationTxt = view.findViewById(R.id.popup_occupation);
        popupProfessionTxt = view.findViewById(R.id.popup_profession);
        popupEducationLevelTxt = view.findViewById(R.id.popup_education_level);
        popupMaritalStatusTxt = view.findViewById(R.id.popup_marital_status);

        yesBtn = view.findViewById(R.id.confirm_button);
        noBtn = view.findViewById(R.id.cancel_button);
    }

}
