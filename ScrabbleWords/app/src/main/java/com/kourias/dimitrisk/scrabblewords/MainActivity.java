package com.kourias.dimitrisk.scrabblewords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private Button infoButton;

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playButton);
        infoButton = (Button) findViewById(R.id.infoButton);


        playButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            levelschoice();
            }
        });


        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });



    }



    public void levelschoice (){
        final CharSequence levels[] = new CharSequence[] {"Ευκολο (4 γράμματα)", "Μεσαίο (5 γράμματα)", "Δύσκολο (6 γράμματα)"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Διάλεξε επίπεδο δυσκολίας");
        builder.setItems(levels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                if (levels[selection] == "Ευκολο (4 γράμματα)")
                    level=1;
                if (levels[selection] == "Μεσαίο (5 γράμματα)")
                    level=2;
                if (levels[selection] == "Δύσκολο (6 γράμματα)")
                    level=3;
                //startActivity(new Intent(MainActivity.this, GameActivity.class));
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra("levelchoice", level);
                startActivity(myIntent);

            }
        });
        builder.show();


    }



}







