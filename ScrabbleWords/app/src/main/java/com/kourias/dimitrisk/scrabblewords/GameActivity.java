package com.kourias.dimitrisk.scrabblewords;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Δήλωση Μεταβλητών
    private Button first_letter_Button;
    private Button second_letter_Button;
    private Button third_letter_Button;
    private Button fourth_letter_Button;
    private Button fifth_letter_Button;
    private Button sixth_letter_Button;

    private TextView first_letter_answer_TextView;
    private TextView second_letter_answer_TextView;
    private TextView third_letter_answer_TextView;
    private TextView fourth_letter_answer_TextView;
    private TextView fifth_letter_answer_TextView;
    private TextView sixth_letter_answer_TextView;

    private Button answerButton;
    private Button resetButton;
    private Button skipButton;

    private String pressedletter;

    private int answeredcounter=0;
    private int triescounter=0;
    private int answered_position = 0;
    private int firstletterpressed = 0;
    private int secondletterpressed = 0;
    private int thirdletterpressed = 0;
    private int fourthletterpressed = 0;
    private int fifthletterpressed = 0;
    private int sixthletterpressed = 0;
    private int wordorder;
    private int level;

    private int[] letters_pointer_array;
    int[] words_order_pointer_array = {0,1,2,3,4,5,6,7,8,9};


    //Δήλωση πινάκων με λέξεων παιχνιδιου ανα επίπεδο
    String[][] mywordseasy = new String[][]{
            { "Μ", "Η", "Λ", "Ο" },
            { "Β", "Α", "Σ", "Η" },
            { "Α", "Υ", "Λ", "Η" },
            { "Β", "Α", "Ζ", "Ο" },
            { "Η", "Χ", "Ο", "Σ" },
            { "Κ", "Ε", "Ν", "Ο" },
            { "Ξ", "Ι", "Ν", "Ο" },
            { "Φ", "Υ", "Σ", "Η" },
            { "Ψ", "Ε", "Μ", "Α" },
            { "Φ", "Ω", "Τ", "Α" },
    };

    String[][] mywordsmedium = new String[][]{
            { "Α", "Γ", "Ω", "Γ", "Η" },
            { "Γ", "Υ", "Π", "Α", "Σ" },
            { "Γ", "Ν", "Ω", "Σ", "Η" },
            { "Δ", "Υ", "Τ", "Η", "Σ" },
            { "Ι", "Ρ", "Ι", "Δ", "Α" },
            { "Κ", "Ω", "Φ", "Ο", "Σ" },
            { "Λ", "Ε", "Ι", "Ο", "Σ" },
            { "Ρ", "Ω", "Γ", "Μ", "Η" },
            { "Κ", "Ο", "Λ", "Ι", "Ε" },
            { "Ο", "Μ", "Ο", "Ι", "Ο" },
    };

    String[][] mywordshard = new String[][]{
            { "Ε", "Γ", "Κ", "Υ", "Ρ", "Ο" },
            { "Ξ", "Ι", "Φ", "Ι", "Α", "Σ" },
            { "Σ", "Η", "Μ", "Α", "Ι", "Α" },
            { "Φ", "Ο", "Ρ", "Τ", "Ι", "Ο" },
            { "Α", "Π", "Ε", "Ι", "Ρ", "Α" },
            { "Φ", "Ι", "Λ", "Τ", "Ρ", "Ο" },
            { "Υ", "Φ", "Α", "Λ", "Ο", "Σ" },
            { "Φ", "Α", "Ρ", "Δ", "Υ", "Σ" },
            { "Σ", "Υ", "Ν", "Ο", "Ρ", "Α" },
            { "Σ", "Τ", "Α", "Δ", "Ι", "Ο" },
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        first_letter_Button = (Button) findViewById(R.id.first_letter_btn);
        second_letter_Button = (Button) findViewById(R.id.second_letter_btn);
        third_letter_Button = (Button) findViewById(R.id.third_letter_btn);
        fourth_letter_Button = (Button) findViewById(R.id.fourth_letter_btn);
        fifth_letter_Button = (Button) findViewById(R.id.fifth_letter_btn);
        sixth_letter_Button = (Button) findViewById(R.id.sixth_letter_btn);

        first_letter_answer_TextView = (TextView) findViewById(R.id.first_letter_answer_tv);
        second_letter_answer_TextView = (TextView) findViewById(R.id.second_letter_answer_tv);
        third_letter_answer_TextView = (TextView) findViewById(R.id.third_letter_answer_tv);
        fourth_letter_answer_TextView = (TextView) findViewById(R.id.fourth_letter_answer_tv);
        fifth_letter_answer_TextView = (TextView) findViewById(R.id.fifth_letter_answer_tv);
        sixth_letter_answer_TextView = (TextView) findViewById(R.id.sixth_letter_answer_tv);

        answerButton = (Button) findViewById(R.id.answerButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        skipButton = (Button) findViewById(R.id.skipButton);

        //Λήψη επίπεδου που επέλεξε ο χρήστης απο την MainActivity
        Intent intent = getIntent();
        level = intent.getIntExtra("levelchoice", 0);


        //Φόρτωση πρώτης λέξης
        nextWord();


        answerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                checkAnswer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                resetAnswer();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                skipAnswer();
            }
        });


        first_letter_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (firstletterpressed == 0)
                {
                    //Αν πατηθεί το πρώτο γράμμα της μπερδεμένης λέξης το μετατρέπει σε string και το φορτώνει μέσω της συνάρτησης loadLetter() στο κατάλληλο textview της απάντησης
                    //Ομοίως και για τα υπόλοιπα γράμματα,σε ποιά θέση της απάντησης θα μπει το καθορίζει η loadLetter(),όπου παίρνει ως παράμετρο το γράμμα που πατήθηκε
                    //Η μεταβλητη firstletterpressed δειχνει ότι έχει πατηθεί ήδη το κουμπί και δεν μπορεί να ξαναπατηθεί
                    pressedletter = first_letter_Button.getText().toString();
                    loadLetter(pressedletter);
                    firstletterpressed = 1;
                    first_letter_Button.setVisibility(View.INVISIBLE);
                }
            }
        });


        second_letter_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (secondletterpressed == 0)
                {
                    pressedletter = second_letter_Button.getText().toString();
                    loadLetter(pressedletter);
                    secondletterpressed = 1;
                    second_letter_Button.setVisibility(View.INVISIBLE);
                }
            }
        });


        third_letter_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (thirdletterpressed == 0)
                {
                    pressedletter = third_letter_Button.getText().toString();
                    loadLetter(pressedletter);
                    thirdletterpressed = 1;
                    third_letter_Button.setVisibility(View.INVISIBLE);
                }
            }
        });


        fourth_letter_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (fourthletterpressed == 0)
                {
                    pressedletter = fourth_letter_Button.getText().toString();
                    loadLetter(pressedletter);
                    fourthletterpressed = 1;
                    fourth_letter_Button.setVisibility(View.INVISIBLE);

                }
            }
        });

        //Το 5 γράμμα θα "ενεργοποιηθει" (αλλιως εχει Visibility:Gone) όταν ο χρήστης επιλέξει το μεσαίο επίπεδο ή το δύσκολο
        if (level == 2 || level == 3)
        {
            fifth_letter_Button.setVisibility(View.VISIBLE);
            fifth_letter_answer_TextView.setVisibility(View.INVISIBLE);

            fifth_letter_Button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (fifthletterpressed == 0) {
                        pressedletter = fifth_letter_Button.getText().toString();
                        loadLetter(pressedletter);
                        fifthletterpressed = 1;
                        fifth_letter_Button.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        //Το 6 γράμμα θα "ενεργοποιηθεί"  όταν ο χρήστης επιλέξει δύσκολο επίπεδο
        if (level == 3) {

            sixth_letter_Button.setVisibility(View.VISIBLE);
            sixth_letter_answer_TextView.setVisibility(View.INVISIBLE);

            sixth_letter_Button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (sixthletterpressed == 0) {
                        pressedletter = sixth_letter_Button.getText().toString();
                        loadLetter(pressedletter);
                        sixthletterpressed = 1;
                        sixth_letter_Button.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }


    public void loadLetter(String letter) {

        //Έχει οριστεί η μεταβλητή answered_position όπου δείχνει πόσα γράμματα έχει απαντήσει ο χρήστης ώστε να φορτώνεται στο κατάλληλο Textview,
        // δηλάδή αν η answered_position δείχνει 2,σημαίνει ότι ο χρήστης έχει δώσει 3 γράμματα ως απάντηση για να συμπληρώσει την λέξη και το επόμενο γράμμα θα φορτωθεί στο 4ο textview
        //Οπότε όταν πατηθεί,κρύβεται το δωσμένο γράμμα και φορτώνεται στην απάντηση

        if (answered_position == 0){
            first_letter_answer_TextView.setText(letter);
            first_letter_answer_TextView.setVisibility(View.VISIBLE);
        }

        if (answered_position == 1){
            second_letter_answer_TextView.setText(letter);
            second_letter_answer_TextView.setVisibility(View.VISIBLE);
        }

        if (answered_position == 2){
            third_letter_answer_TextView.setText(letter);
            third_letter_answer_TextView.setVisibility(View.VISIBLE);
        }

        if (answered_position == 3){
            fourth_letter_answer_TextView.setText(letter);
            fourth_letter_answer_TextView.setVisibility(View.VISIBLE);
        }

        if (level == 2 || level == 3) {
            if (answered_position == 4) {
                fifth_letter_answer_TextView.setText(letter);
                fifth_letter_answer_TextView.setVisibility(View.VISIBLE);
            }
        }

        if (level == 3) {
            if (answered_position == 5) {
                sixth_letter_answer_TextView.setText(letter);
                sixth_letter_answer_TextView.setVisibility(View.VISIBLE);
            }
        }

        answered_position++;
    }


    public void checkAnswer() {

        //Η συνάρτηση αυτή καλείται με το κουμπι answerButton και παίρνει τα γράμματα που έχουν δωθεί ως απαντήσεις και τα έλεγχει με τον αντίστοιχο του επιπέδου πίνακα
        //Μετράει προσπάθειες σε μια μεταβλητή και περνάει σε μια άλλη μεταβλητή αν ο χρήστης απάντησε σωστά ή όχι,
        //όπου στο τέλος καλεί την συνάρτηση AnswerMessage() με παράμετρο την μεταβλητή για να δείξει το κατάλληλο μήνυμα σωστού/λάθους

        String firstletteranswer = first_letter_answer_TextView.getText().toString();
        String secondletteranswer = second_letter_answer_TextView.getText().toString();
        String thirdletteranswer = third_letter_answer_TextView.getText().toString();
        String fourthletteranswer = fourth_letter_answer_TextView.getText().toString();
        String fifthletteranswer = fifth_letter_answer_TextView.getText().toString();
        String sixthletteranswer = sixth_letter_answer_TextView.getText().toString();
        int correctanswer=0;
        triescounter++;

        if (level == 1) {
            if (firstletteranswer.equals(mywordseasy[wordorder][0]) && secondletteranswer.equals(mywordseasy[wordorder][1]) && thirdletteranswer.equals(mywordseasy[wordorder][2]) && fourthletteranswer.equals(mywordseasy[wordorder][3]))
                correctanswer=1;
            else
                correctanswer=0;
        }

        if (level == 2){
            if (firstletteranswer.equals(mywordsmedium[wordorder][0]) && secondletteranswer.equals(mywordsmedium[wordorder][1]) && thirdletteranswer.equals(mywordsmedium[wordorder][2]) && fourthletteranswer.equals(mywordsmedium[wordorder][3]) && fifthletteranswer.equals(mywordsmedium[wordorder][4]))
                correctanswer=1;
            else
                correctanswer=0;
        }

        if (level == 3){
            if (firstletteranswer.equals(mywordshard[wordorder][0]) && secondletteranswer.equals(mywordshard[wordorder][1]) && thirdletteranswer.equals(mywordshard[wordorder][2]) && fourthletteranswer.equals(mywordshard[wordorder][3]) && fifthletteranswer.equals(mywordshard[wordorder][4]) && sixthletteranswer.equals(mywordshard[wordorder][5]))
                correctanswer=1;
            else
                correctanswer=0;
        }

        AnswerMessage(correctanswer);
    }


    public void AnswerMessage(int correctanswer) {

        //Η συνάρτηση αυτή δείχνει τα μηνύματα σωστού/λάθους ανάλογα την απάντηση,αν είναι λάθος σβήνει την απάντηση και εμφανίζει την ίδια μπερδεμένη λέξη πάλι,
        // άμα είναι σωστή,σβήνει την απάντηση και φορτώνει την επόμενη λέξη

        final MediaPlayer correctsound = MediaPlayer.create(this, R.raw.correct);

        if (correctanswer == 1)   {
            //Toast.makeText(GameActivity.this, "Μπράβο", Toast.LENGTH_SHORT).show();
            answeredcounter++;
            if (answeredcounter < 10)
            {
                //σε αυτή την συνθήκη ελέγχεται αν έχουν απαντηθεί όλες οι λέξεις,αν όχι,συνεχίζει στο παιχνίδι

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                correctsound.start();
                builder.setMessage("Μπράβο!!! Βρήκες την λέξη.");
                builder.show();
                resetAnswer();
                nextWord();
            }
            else
            {
                //αν έχουν απαντηθεί όλες οι λέξεις τότε θα ξεκινήσει το ResultsActivity όπου στέλνεται η μεταβλητή που μέτρησε τις προσπάθειες

                correctsound.start();
                Intent myIntent = new Intent(GameActivity.this, ResultsActivity.class);
                myIntent.putExtra("scoretries", triescounter);
                startActivity(myIntent);
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final MediaPlayer wrongsound = MediaPlayer.create(this, R.raw.wrong);
            wrongsound.start();
            builder.setMessage("Δυστυχώς δεν είναι αυτή η λέξη,προσπάθησε ξανά.00");
            builder.show();
            resetAnswer();
            //Toast.makeText(GameActivity.this, "Παρακαλώ προσπαθήστε ξανά.", Toast.LENGTH_SHORT).show();
        }
    }


    public void resetAnswer() {
        //Η συνάρτηση αυτή σβήνει την δωσμένη απάντηση,μηδενίζει την θέση απάντησης,ξαναεμφανίζει την πάνω λέξη και αναιρεί το πάτημα των κουμπιών ώστε να μπορούν να ξαναπατηθούν

        first_letter_answer_TextView.setText(null);
        second_letter_answer_TextView.setText(null);
        third_letter_answer_TextView.setText(null);
        fourth_letter_answer_TextView.setText(null);
        fifth_letter_answer_TextView.setText(null);
        sixth_letter_answer_TextView.setText(null);

        answered_position = 0;
        firstletterpressed = 0;
        secondletterpressed = 0;
        thirdletterpressed = 0;
        fourthletterpressed = 0;

        first_letter_Button.setVisibility(View.VISIBLE);
        second_letter_Button.setVisibility(View.VISIBLE);
        third_letter_Button.setVisibility(View.VISIBLE);
        fourth_letter_Button.setVisibility(View.VISIBLE);
        first_letter_answer_TextView.setVisibility(View.INVISIBLE);
        second_letter_answer_TextView.setVisibility(View.INVISIBLE);
        third_letter_answer_TextView.setVisibility(View.INVISIBLE);
        fourth_letter_answer_TextView.setVisibility(View.INVISIBLE);

        if (level == 2 || level == 3)
        {
            fifthletterpressed = 0;
            fifth_letter_Button.setVisibility(View.VISIBLE);
            fifth_letter_answer_TextView.setVisibility(View.INVISIBLE);
        }

        if (level == 3)
        {
            sixthletterpressed = 0;
            sixth_letter_Button.setVisibility(View.VISIBLE);
            sixth_letter_answer_TextView.setVisibility(View.INVISIBLE);
        }
    }


    public void skipAnswer() {
        answeredcounter++;
        if (answeredcounter < 10)
        {
            resetAnswer();
            nextWord();
        }
        else
        {
            Intent myIntent = new Intent(GameActivity.this, ResultsActivity.class);
            myIntent.putExtra("scoretries", triescounter);
            startActivity(myIntent);
        }
    }


    public void nextWord() {

        //Η λειτουργία της έιναι να φορτώνει την λέξη και να την εμφανίζει
        // Αρχικά καλείται η συνάρτηση randomwordsorder() όπου θα δώσει μια τύχαια σειρά του array (θα εξηγηθεί η λειτουργία της μέσα στην συνάρτηση αυτή)
        //Ανάλογα το επίπεδο θα επιλεχτεί και ο αντίστοιχος πίνακας επιπέδου και θα παίρνεται η λέξη που δίαλεξε η συνάρτηση randomwordsorder()
        //και θα φορτωθεί σε κάθε button ένα τυχαίο γράμμα της λέξης αυτης,για το τυχαίο γράμμα καλείται η συνάρτηση scrabbleletters()
        //Σε κάθε καινουργία λέξη αρχικοποιείται ένας πίνακας-δείκτης θέσεων γραμμάτων όπου θα χρειαστεί στην scrabbleletters()

        wordorder = randomwordsorder();
        if (level == 1) {
            letters_pointer_array = new int[] {0,1,2,3};
            first_letter_Button.setText(mywordseasy[wordorder][scrabbleletters()]);
            second_letter_Button.setText(mywordseasy[wordorder][scrabbleletters()]);
            third_letter_Button.setText(mywordseasy[wordorder][scrabbleletters()]);
            fourth_letter_Button.setText(mywordseasy[wordorder][scrabbleletters()]);
        }
        if (level == 2){
            letters_pointer_array = new int[] {0,1,2,3,4};
            first_letter_Button.setText(mywordsmedium[wordorder][scrabbleletters()]);
            second_letter_Button.setText(mywordsmedium[wordorder][scrabbleletters()]);
            third_letter_Button.setText(mywordsmedium[wordorder][scrabbleletters()]);
            fourth_letter_Button.setText(mywordsmedium[wordorder][scrabbleletters()]);
            fifth_letter_Button.setText(mywordsmedium[wordorder][scrabbleletters()]);
        }
        if (level == 3){
            letters_pointer_array = new int[] {0,1,2,3,4,5};
            first_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
            second_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
            third_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
            fourth_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
            fifth_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
            sixth_letter_Button.setText(mywordshard[wordorder][scrabbleletters()]);
        }
    }


    public int scrabbleletters(){
        int randomletter=0;
        int showletterpos=0;

        //Η scrabbleletters δουλευει ως εξής,εχει δημιουργηθεί ένας νέος πίνακας με μήκος τα γράμματα του επιπέδου δυσκολίας,αυτός ο πίνακας δείχνει την θέση των γραμμάτων της λέξης
        //η random διαλέγει μια τυχαία θέση σε αυτόν τον πίνακα και την κανει -1 ώστε αν την ξαναδιαλέξει η random να ξανατρεχτει γιατί αυτό το γράμμα έχει διαλεχτεί
        //οπότε κάθε φόρα που καλέιται η scrabbleletters θα δίνει μέσω της random ένα γράμμα που δεν έχει επιλεχθεί παραπάνω από μία φορές

        do
        {
            Random rand = new Random();

            if (level == 1) {
                randomletter = rand.nextInt(4 );
            }
            if (level == 2){
                randomletter = rand.nextInt(5 );
            }
            if (level == 3){
                randomletter = rand.nextInt(6 );
            }


            if (letters_pointer_array[randomletter] != -1)
            {
                showletterpos=letters_pointer_array[randomletter];
            }
        } while (letters_pointer_array[randomletter] == -1);

        letters_pointer_array[randomletter] = -1;

        return showletterpos;
    }


    public int randomwordsorder(){
        int randomword;
        int wordpos=0;

        //Δουλεύει με τον ίδιο τρόπο με την scrabbleletters(), η random καθοριζει μια λεξη/(γραμμη του array λεξεων) και στον πίνακα δεικτη θέσεων μπαίνει το -1 ώστε να μην ξαναεμφανιστεί αυτή η λέξη

        do
        {
            Random rand2 = new Random();
            randomword = rand2.nextInt(10 );
            if (words_order_pointer_array[randomword] != -1)
            {
                wordpos=words_order_pointer_array[randomword];
            }
        } while (words_order_pointer_array[randomword] == -1);

        words_order_pointer_array[randomword] = -1;

        return wordpos;
    }

}
