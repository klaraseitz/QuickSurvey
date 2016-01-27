package com.quicksurvey.klara.quicksurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SurveyObjectDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");
        dataSource = new SurveyObjectDataSource(this);

    }

    public void startOver (View v) {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }

    private void showAllListEntries () {
        List<SurveyObject> surveyObjectList = dataSource.getAllSurveyObjects();
        int occurences[] = new int[4];
        for(int i = 0; i < surveyObjectList.size(); i++){
            if(surveyObjectList.get(i).toString().equals("nichts gewusst")){
                occurences[0]++;
            }else if(surveyObjectList.get(i).toString().equals("nur Modell bekannt")){
                occurences[1]++;
            }else if(surveyObjectList.get(i).toString().equals("nur Microskopbild bekannt")){
                occurences[2]++;
            }else{
                occurences[3]++;
            }
        }

        List<String> outputList = new ArrayList<>();
        outputList.add(0, occurences[0]+ " x nichts gewusst");
        outputList.add(1, occurences[1]+ " x nur Modell bekannt");
        outputList.add(2, occurences[2]+ " x nur echter Strang bekannt");
        outputList.add(3, occurences[3]+ " x alles gewusst!");



        ArrayAdapter<String> surveyObjectArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_1,
                outputList);

        ListView surveyObjectListView = (ListView) findViewById(R.id.listview_survey_objects);
        surveyObjectListView.setAdapter(surveyObjectArrayAdapter);

        int numberOfPplAsked = occurences[0]+occurences[1]+occurences[2]+occurences[3];
        TextView howManyPplText = (TextView) findViewById(R.id.textView);
        String text;
        if (numberOfPplAsked != 1) {
            text = "Bisher " + numberOfPplAsked + " Personen befragt!";
        }else{
            text = "Bisher eine Person befragt!";
        }
        howManyPplText.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deleteAll) {
            dataSource.reset();
            showAllListEntries();
            return true;
        }else if (id == R.id.action_deleteOne) {
            dataSource.deleteLast();
            showAllListEntries();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
