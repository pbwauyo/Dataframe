package com.example.dataframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        spinner = findViewById(R.id.classes_spinner);
        list = getResources().getStringArray(R.array.classes);
        adapter = new ClassSpinnerAdapter(this, list);

        spinner.setAdapter(adapter);
    }
}
