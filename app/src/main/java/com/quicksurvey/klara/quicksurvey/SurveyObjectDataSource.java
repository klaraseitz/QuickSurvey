package com.quicksurvey.klara.quicksurvey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SurveyObjectDataSource {

    private static final String LOG_TAG = SurveyObjectDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private SurveyObjectDbHelper dbHelper;

    private String[] columns = {
            SurveyObjectDbHelper.COLUMN_ID,
            SurveyObjectDbHelper.COLUMN_WOLLE,
            SurveyObjectDbHelper.COLUMN_TREPPE
    };

    public SurveyObjectDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new SurveyObjectDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public void reset() {
        //close();
        database.delete(SurveyObjectDbHelper.TABLE_SURVEY_LIST,
                "1 = 1",
                null);
    }

    public SurveyObject createSurveyObject(int wolle, int treppe) {
        ContentValues values = new ContentValues();
        values.put(SurveyObjectDbHelper.COLUMN_WOLLE, wolle);
        values.put(SurveyObjectDbHelper.COLUMN_TREPPE, treppe);

        long insertId = database.insert(SurveyObjectDbHelper.TABLE_SURVEY_LIST, null, values);

        Cursor cursor = database.query(SurveyObjectDbHelper.TABLE_SURVEY_LIST,
                columns, SurveyObjectDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        SurveyObject surveyObject = cursorToSurveyObject(cursor);
        cursor.close();

        return surveyObject;
    }

    private SurveyObject cursorToSurveyObject(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(SurveyObjectDbHelper.COLUMN_ID);
        int idTreppe = cursor.getColumnIndex(SurveyObjectDbHelper.COLUMN_WOLLE);
        int idWolle = cursor.getColumnIndex(SurveyObjectDbHelper.COLUMN_TREPPE);

        int treppe = cursor.getInt(idTreppe);
        int wolle = cursor.getInt(idWolle);
        long id = cursor.getLong(idIndex);

        SurveyObject surveyObject = new SurveyObject(treppe, wolle, id);

        return surveyObject;
    }

    public List<SurveyObject> getAllSurveyObjects() {
        List<SurveyObject> surveyObjectList = new ArrayList<>();

        Cursor cursor = database.query(SurveyObjectDbHelper.TABLE_SURVEY_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        SurveyObject surveyObject;

        while(!cursor.isAfterLast()) {
            surveyObject = cursorToSurveyObject(cursor);
            surveyObjectList.add(surveyObject);
            Log.d(LOG_TAG, "ID: " + surveyObject.getId() + ", Inhalt: " + surveyObject.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return surveyObjectList;
    }
}
