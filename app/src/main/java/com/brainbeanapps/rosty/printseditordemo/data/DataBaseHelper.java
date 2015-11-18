package com.brainbeanapps.rosty.printseditordemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rosty on 7/7/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG =DataBaseHelper.class.getSimpleName();

    public static final String TABLE_PRINTS = "PRINTS";
    public static final String COLUMN_ID = "_ID";

    public static final String COLUMN_RESULT_PATH = "RESULT_PATH";
    public static final String COLUMN_ORIGIN_PATH = "ORIGIN_PATH";
    public static final String COLUMN_ORIGIN_URL = "ORIGIN_URL";
    public static final String COLUMN_ORIGIN_ID = "ORIGIN_ID";
    public static final String COLUMN_SCALE = "SCALE";

    public static final String COLUMN_TEXT = "TEXT";
    public static final String COLUMN_TEXT_COLOR = "TEXT_COLOR";
    public static final String COLUMN_TEXT_FONT = "TEXT_FONT";
    public static final String COLUMN_TEXT_GRAVITY = "TEXT_GRAVITY";
    public static final String COLUMN_TEXT_SIZE = "TEXT_SIZE";

    public static final String COLUMN_MATRIX_VALUES = "MATRIX_VALUES";
    public static final String COLUMN_BRIGHTNESS = "BRIGHTNESS";
    public static final String COLUMN_CONTRAST = "CONTRAST";
    public static final String COLUMN_FILTER = "FILTER";
    public static final String COLUMN_TEMPLATE = "TEMPLATE";
    public static final String COLUMN_SVG_COLOR = "SVG_COLOR";

    private static final String DATABASE_NAME = "prints.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_PRINTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_RESULT_PATH + " TEXT," + COLUMN_ORIGIN_PATH + " TEXT,"
            + COLUMN_ORIGIN_URL + " TEXT," + COLUMN_ORIGIN_ID + " INTEGER,"
            + COLUMN_SCALE + " REAL," + COLUMN_TEXT + " TEXT,"
            + COLUMN_TEXT_COLOR + " INTEGER," + COLUMN_TEXT_FONT + " TEXT,"
            + COLUMN_TEXT_GRAVITY + " INTEGER," + COLUMN_TEXT_SIZE + " INTEGER,"
            + COLUMN_MATRIX_VALUES + " STRING," + COLUMN_BRIGHTNESS + " REAL,"
            + COLUMN_CONTRAST + " REAL," + COLUMN_FILTER + " INTEGER,"
            + COLUMN_TEMPLATE + " INTEGER," + COLUMN_SVG_COLOR + " INTEGER)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRINTS);
        onCreate(db);
    }

}
