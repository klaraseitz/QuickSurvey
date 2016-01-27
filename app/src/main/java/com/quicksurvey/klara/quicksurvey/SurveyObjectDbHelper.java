package com.quicksurvey.klara.quicksurvey;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class SurveyObjectDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = SurveyObjectDbHelper.class.getSimpleName();
    public static final String DB_NAME = "survey_objects.db";
    public static final int DB_VERSION = 1;


    public static final String TABLE_SURVEY_LIST = "survey_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WOLLE = "wolle";
    public static final String COLUMN_TREPPE = "treppe";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_SURVEY_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WOLLE + " INTEGER NOT NULL, " +
                    COLUMN_TREPPE + " INTEGER NOT NULL);";


    public SurveyObjectDbHelper(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}