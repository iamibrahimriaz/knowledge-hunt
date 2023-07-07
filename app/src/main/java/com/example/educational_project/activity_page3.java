package com.example.educational_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView;
import android.net.Uri;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class activity_page3 extends AppCompatActivity {

    private ListView listView;

    private TextView applyLink;
    private List<JobCircular> jobCirculars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        Button backButton = findViewById(R.id.back_button_res);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        listView = findViewById(R.id.jobCircularsListView);

        // Create the job circulars list
        jobCirculars = new ArrayList<>();
        jobCirculars.add(new JobCircular("Software Engineer", "https://example.com/job1", "Open Position: 12"));
        jobCirculars.add(new JobCircular("Web Developer", "https://example.com/job2", "Open Position: 7"));
        jobCirculars.add(new JobCircular("App Developer", "https://example.com/job2", "Open Position: 8"));
        jobCirculars.add(new JobCircular("Data Analyst", "https://example.com/job2", "Open Position: 5"));
        jobCirculars.add(new JobCircular("Support Engineer", "https://example.com/job2", "Open Position: 9"));
        jobCirculars.add(new JobCircular("Backend Developer", "https://example.com/job2", "Open Position: 3"));
        jobCirculars.add(new JobCircular("Frontend Developer", "https://example.com/job2", "Open Position: 6"));
        jobCirculars.add(new JobCircular("Full Stack Developer", "https://example.com/job2", "Open Position: 8"));
        jobCirculars.add(new JobCircular("Digital Marketer", "https://example.com/job2", "Open Position: 3"));
        jobCirculars.add(new JobCircular("Creative Designer", "https://example.com/job2", "Open Position: 5"));
        // Add more job circulars...

        // Create a custom adapter
        JobCircularAdapter adapter = new JobCircularAdapter(this, jobCirculars);
        listView.setAdapter(adapter);

        // Set item click listener to handle job circular selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected job circular
            JobCircular selectedJobCircular = jobCirculars.get(position);

            // Handle job circular selection (e.g., open the link)
            String link = selectedJobCircular.getLink();
            // Implement the logic to open the link
        });
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public class JobCircular {
        private String title;
        private String link;
        private String position;

        public JobCircular(String title, String link, String position) {
            this.title = title;
            this.link = link;
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getPosition() {
            return position;
        }
    }

    public class JobCircularAdapter extends ArrayAdapter<JobCircular> {

        private LayoutInflater inflater;

        public JobCircularAdapter(Context context, List<JobCircular> jobCirculars) {
            super(context, 0, jobCirculars);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflater.inflate(R.layout.list_item_job_circular, parent, false);
            }

            // Get the current job circular
            JobCircular jobCircular = getItem(position);

            // Set the circular title, link, and position to the respective TextViews in the list item
            TextView titleTextView = itemView.findViewById(R.id.titleTextView);
            TextView linkTextView = itemView.findViewById(R.id.linkTextView);
            TextView positionTextView = itemView.findViewById(R.id.positionTextView);

            if (jobCircular != null) {
                titleTextView.setText(jobCircular.getTitle());
                linkTextView.setText(jobCircular.getLink());
                positionTextView.setText(jobCircular.getPosition());
            }

            // Set click listener on the list item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JobCircular item = getItem(position);
                    if (item != null) {
                        String url = item.getLink();
                        openLink(url);
                    }
                }
            });

            return itemView;
        }

        private void openLink(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }


}
