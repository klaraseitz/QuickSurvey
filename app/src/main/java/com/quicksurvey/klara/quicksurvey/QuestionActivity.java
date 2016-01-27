package com.quicksurvey.klara.quicksurvey;

/**
 * Created by Klara on 27.01.2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;


public class QuestionActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public int questionNumber = 0;

    private int numberOfQuestions = 2;
    private int knowledge[];
    private SurveyObjectDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");
        dataSource = new SurveyObjectDataSource(this);

        Log.d(LOG_TAG, "Das erste Bild wird geladen.");
        ImageButton resultYes = (ImageButton)findViewById(R.id.yes);
        resultYes.setImageResource(R.drawable.thumb_up);

        Log.d(LOG_TAG, "Das zweite Bild wird geladen.");
        ImageButton resultNo = (ImageButton)findViewById(R.id.no);
        resultNo.setImageResource(R.drawable.thumb_down);

        Log.d(LOG_TAG, "Knowledge Array wird angelegt.");
        knowledge = new int[numberOfQuestions];
        setImage();

    }

    public void resultIsYes(View v) {
        knowledge[questionNumber] = 1;
        questionNumber = questionNumber + 1;
        if (questionNumber < numberOfQuestions) {
            setImage();
        }
        else{
        // switch to MainActivity
        dataSource.createSurveyObject(knowledge[0], knowledge[1]);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
    }

    public void resultIsNo(View v) {
        knowledge[questionNumber] = 0;
        questionNumber = questionNumber + 1;
        if (questionNumber < numberOfQuestions) {
            setImage();
        } else {
            // switch to MainActivity
            dataSource.createSurveyObject(knowledge[0], knowledge[1]);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void setImage() {
        ImageView questionImage = (ImageView) findViewById(R.id.questionImage);
        Log.d(LOG_TAG, "Das dritte Bild wird geladen.");
        switch (questionNumber) {
            case 0: questionImage.setImageResource(R.drawable.wolle);
                    break;
            case 1: questionImage.setImageResource(R.drawable.dna);
                    break;
            default: questionImage.setImageResource(R.drawable.wolle);
                    break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

}

