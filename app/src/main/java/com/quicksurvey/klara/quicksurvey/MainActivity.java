package com.quicksurvey.klara.quicksurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

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


        ArrayAdapter<SurveyObject> surveyObjectArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_1,
                surveyObjectList);

        ListView surveyObjectListView = (ListView) findViewById(R.id.listview_survey_objects);
        surveyObjectListView.setAdapter(surveyObjectArrayAdapter);
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
        if (id == R.id.action_settings) {
            dataSource.reset();
            showAllListEntries();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
