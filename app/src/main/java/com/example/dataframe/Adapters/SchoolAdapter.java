package com.example.dataframe.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.dataframe.Models.SchoolModel;
import com.example.dataframe.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SchoolModel> schools;
    private OnSchoolClickedListener onSchoolClickedListener;
    private GoogleProgressBar progressBar;
    private ShimmerFrameLayout shimmerFrameLayout;

    private HashMap<Integer, String> colors = new HashMap<>();


    public SchoolAdapter(Context context, ArrayList<SchoolModel> schools, OnSchoolClickedListener onSchoolClickedListener, GoogleProgressBar progressBar, ShimmerFrameLayout shimmerFrameLayout){
        this.context = context;
        this.schools = schools;
        this.onSchoolClickedListener = onSchoolClickedListener;
        this.progressBar = progressBar;
        this.shimmerFrameLayout = shimmerFrameLayout;

        populateColorHashMap();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.school_row_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        progressBar.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        String schoolName = schools.get(i).getSchoolName();
        Random random = new Random();
        int min = 1;
        int max = 26;

        int color =random.nextInt((max - min) + 1) + min;

        viewHolder.schoolNameTxt.setText(schoolName);
        viewHolder.imagePlaceholder.setText(getInitial(schoolName));

        //viewHolder.imagePlaceholder.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_text_view));
        viewHolder.imagePlaceholder.setBackgroundColor(Color.parseColor(colors.get(color)));
    }

    @Override
    public int getItemCount() {
        if(schools.size() != 0){
            return schools.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView schoolImageView;
        private TextView schoolNameTxt;
        private TextView imagePlaceholder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            schoolImageView = itemView.findViewById(R.id.school_badge);
            schoolNameTxt = itemView.findViewById(R.id.school_name);
            imagePlaceholder = itemView.findViewById(R.id.placeholder);

            schoolNameTxt.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onSchoolClickedListener.onSchoolClicked(v, getAdapterPosition());
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    public interface OnSchoolClickedListener{
        void onSchoolClicked(View view, int position);
    }

    private String getInitial(String schoolName){
        return String.valueOf(schoolName.charAt(0));
    }

    private void populateColorHashMap(){
        colors.put(1, "#00FFFF");
        colors.put(2, "#f88885");
        colors.put(3, "#6495ED");
        colors.put(4, "#cd0df6");
        colors.put(5, "#add8e6");
        colors.put(6, "#B22222");
        colors.put(7, "#581845");
        colors.put(8, "#5218FA");
        colors.put(9, "#00416A");
        colors.put(10, "#00A86B");
        colors.put(11, "#C3B091");
        colors.put(12, "#B57EDC");
        colors.put(13, "#2fc6fe");
        colors.put(14, "#e0d191");
        colors.put(15, "#FF7F00");
        colors.put(16, "#003153");
        colors.put(17, "#436b95");
        colors.put(18, "#734A12");
        colors.put(19, "#5ccf70");
        colors.put(20, "#008080");
        colors.put(21, "#8B00FF");
        colors.put(22, "#339966");
        colors.put(23, "#C9A0DC");
        colors.put(24, "#eeed09");
        colors.put(25, "#a97164");
        colors.put(26, "#506022");
    }
}
