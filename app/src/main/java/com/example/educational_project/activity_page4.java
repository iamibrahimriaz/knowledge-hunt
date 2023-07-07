package com.example.educational_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_page4 extends AppCompatActivity {

    private TextView nameTextViewDev1, nameTextViewDev2;
    private TextView emailTextViewDev1, emailTextViewDev2;
    private TextView mobileTextViewDev1, mobileTextViewDev2;
    private TextView githubTextViewDev1, githubTextViewDev2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        Button backButton = findViewById(R.id.back_button_contact);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        nameTextViewDev1 = findViewById(R.id.nameTextViewDev1);
        emailTextViewDev1 = findViewById(R.id.emailTextViewDev1);
        mobileTextViewDev1 = findViewById(R.id.mobileTextViewDev1);
        githubTextViewDev1 = findViewById(R.id.githubTextViewDev1);

        nameTextViewDev2 = findViewById(R.id.nameTextViewDev2);
        emailTextViewDev2 = findViewById(R.id.emailTextViewDev2);
        mobileTextViewDev2 = findViewById(R.id.mobileTextViewDev2);
        githubTextViewDev2 = findViewById(R.id.githubTextViewDev2);

        // Update the TextViews with the actual contact information
        nameTextViewDev1.setText("Ibrahim Riaz");
        emailTextViewDev1.setText("iamibrahim.riaz@gmail.com");
        mobileTextViewDev1.setText("+8801687166885");
        githubTextViewDev1.setText("github.com/iamibrahimriaz");

        // Update the TextViews with the actual contact information
        nameTextViewDev2.setText("Ashraful Alam");
        emailTextViewDev2.setText("ashraful.cse@gmail.com");
        mobileTextViewDev2.setText("+8801517805906");
        githubTextViewDev2.setText("github.com/ashrafulalam");
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
