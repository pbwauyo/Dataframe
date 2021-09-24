package com.example.dataframe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.Spinner;

import com.example.dataframe.Adapters.ClassSpinnerAdapter;

public class StudentsActivity extends AppCompatActivity {
    ClassSpinnerAdapter adapter;
    String[] list;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.classes_spinner);
        list = getResources().getStringArray(R.array.classes);
        adapter = new ClassSpinnerAdapter(this, list);

        spinner.setAdapter(adapter);
    }
}
