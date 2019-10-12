package com.kourias.dimitrisk.scrabblewords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private Button returnmenuButton;
    private TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        returnmenuButton = (Button) findViewById(R.id.returnmenuButton);
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);

        Intent intent = getIntent();
        int scoreValue = intent.getIntExtra("scoretries", 0);
        resultsTextView.setText(String.valueOf("Προσπάθειες: " + scoreValue));




        returnmenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            }
        });
    }


}
