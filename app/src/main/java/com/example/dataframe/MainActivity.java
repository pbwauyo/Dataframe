package com.example.dataframe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView studentCardView, schoolCardView, teacherCardView, nonTeachingStaffCardView, infrastructureActivityCardView, teachingMaterialsActivityCardView, hivCardView, sportsCardView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.menu_toolbar);
        studentCardView = findViewById(R.id.students_card_view);
        schoolCardView = findViewById(R.id.school_card_view);
        teacherCardView = findViewById(R.id.teacher_card_view);
        nonTeachingStaffCardView = findViewById(R.id.non_teaching_staff_card_view);
        infrastructureActivityCardView = findViewById(R.id.infrastructure_card_view);
        teachingMaterialsActivityCardView = findViewById(R.id.teaching_materials_activity_card_view);
        sportsCardView = findViewById(R.id.sports_card_view);
        hivCardView = findViewById(R.id.hiv_card_view);
        setSupportActionBar(toolbar);

        studentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Student.class));
            }
        });

        schoolCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SchoolActivity.class));
            }
        });

        teacherCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllSchoolsActivity.class);
                intent.putExtra("childActivity", "TeacherActivity");
                startActivity(intent);
            }
        });

        nonTeachingStaffCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NonTeachingstaffActivity.class));
            }
        });

        infrastructureActivityCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllSchoolsActivity.class);
                intent.putExtra("childActivity", "InfrastructureActivity");
                startActivity(intent);
            }
        });

        teachingMaterialsActivityCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TeachingMaterialsActivity.class));
            }
        });

        hivCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HIVActivity.class));
            }
        });

        sportsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SportsActivity.class));
            }
        });
    }

}
