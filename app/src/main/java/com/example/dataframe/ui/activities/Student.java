package com.example.dataframe.ui.activities;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dataframe.MainActivity;
import com.example.dataframe.R;
import com.example.dataframe.ui.fragments.nationalid.ApplicantAddressFragment;
import com.example.dataframe.ui.fragments.nationalid.ApplicantDetailsFragment;
import com.example.dataframe.ui.fragments.nationalid.MaritalDetails;
import com.example.dataframe.ui.fragments.nationalid.ParentsDetails;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.relex.circleindicator.CircleIndicator;

public class Student extends AppCompatActivity {
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        viewPager = findViewById(R.id.applicant_viewpager);
        CircleIndicator circleIndicator = findViewById(R.id.circle_indicator);

        ScreenSliderPagerAdapter pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        circleIndicator.setViewPager(viewPager);

    }

    @Override
    public void onBackPressed(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want to exit?")
                .showCancelButton(true)
                .setConfirmText("Yes")
                .setCancelText("No")
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
                        startActivity(new Intent(Student.this, MainActivity.class));
                    }
                })
                .show();

    }

    public class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter{
        ArrayList<Fragment> fragmentsList = new ArrayList<>();

        ScreenSliderPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);

            fragmentsList.add(new ApplicantDetailsFragment());
            fragmentsList.add(new MaritalDetails());
            fragmentsList.add(new ApplicantAddressFragment());
            fragmentsList.add(new ParentsDetails());
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return fragmentsList.get(0);
                case 1:
                    return fragmentsList.get(1);
                case 2:
                    return fragmentsList.get(2);
                case 3:
                    return fragmentsList.get(3);
                default:
                    return fragmentsList.get(0);
            }

        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }
    }


}
