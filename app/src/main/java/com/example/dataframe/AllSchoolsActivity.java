package com.example.dataframe;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dataframe.Adapters.SchoolAdapter;
import com.example.dataframe.data.models.SchoolModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AllSchoolsActivity extends AppCompatActivity{

    SchoolAdapter schoolAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SchoolModel> schools  = new ArrayList<>();
    GoogleProgressBar progressBar;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    private String childActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schools);

        childActivity = getIntent().getStringExtra("childActivity");

        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();

        recyclerView = findViewById(R.id.all_schools_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progress_bar);

        new FetchSchoolsData().execute();
    }

    class FetchSchoolsData extends AsyncTask<Void, Integer, ArrayList<SchoolModel>>{
        String url = "http://tela.planetsystems.co:8080/weca/webapi/capture/site/list";

        @Override
        protected ArrayList<SchoolModel> doInBackground(Void... voids) {

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<SchoolModel> list = new ArrayList<>();
                    DividerItemDecoration itemDecoration = new DividerItemDecoration(AllSchoolsActivity.this, DividerItemDecoration.VERTICAL);

                    schools = parseJsonDataIntoArrayList(list, response);

                    schoolAdapter = new SchoolAdapter(AllSchoolsActivity.this, schools, new SchoolAdapter.OnSchoolClickedListener() {
                        @Override
                        public void onSchoolClicked(View view, int position) {
                            Intent intent = null;

                            if(childActivity.equals("TeacherActivity")) {
                                intent = new Intent(AllSchoolsActivity.this, TeacherActivity.class);
                                intent.putExtra("school_id", schools.get(position).getSchoolId());
                            }
                            else if(childActivity.equals("InfrastructureActivity")){
                                intent = new Intent(AllSchoolsActivity.this, InfrastructureActivity.class);
                                intent.putExtra("school_id", schools.get(position).getSchoolId());
                            }
                            startActivity(intent);
                        }
                    }, progressBar, shimmerFrameLayout);

                    recyclerView.setAdapter(schoolAdapter);
                    recyclerView.addItemDecoration(itemDecoration);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AllSchoolsActivity.this, "Error fetching schools data: "+ error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


            RequestQueue requestQueue = Volley.newRequestQueue(AllSchoolsActivity.this);
            requestQueue.add(request);

            return schools;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(final ArrayList<SchoolModel> arrayList){
        }
    }

    ArrayList<SchoolModel> parseJsonDataIntoArrayList(ArrayList<SchoolModel> schools, String jsonString){
        SchoolModel school;
        String schoolName, schoolId;

        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonArray.length(); i++){

                if(jsonArray.getJSONObject(i).has("deploymentSiteName") && jsonArray.getJSONObject(i).has("id")){
                    schoolName = jsonArray.getJSONObject(i).getString("deploymentSiteName");
                    schoolId = jsonArray.getJSONObject(i).getString("id");

                    Log.d("school name", schoolName);
                    Log.d("school id", schoolId);

                    school = new SchoolModel(schoolName, schoolId);
                    schools.add(school);
                }
            }

        }
        catch (JSONException e){
            Toast.makeText(AllSchoolsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return schools;
    }
}
