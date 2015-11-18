package com.brainbeanapps.rosty.printseditordemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.brainbeanapps.rosty.printseditorlib.model.FilterDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.ImageDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.TextDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public class PrintsDataSource {

    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;

    private String[] allColumns = {
            DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_RESULT_PATH,
            DataBaseHelper.COLUMN_ORIGIN_PATH, DataBaseHelper.COLUMN_ORIGIN_URL,
            DataBaseHelper.COLUMN_ORIGIN_ID, DataBaseHelper.COLUMN_SCALE,
            DataBaseHelper.COLUMN_TEXT, DataBaseHelper.COLUMN_TEXT_COLOR,
            DataBaseHelper.COLUMN_TEXT_FONT, DataBaseHelper.COLUMN_TEXT_GRAVITY,
            DataBaseHelper.COLUMN_TEXT_SIZE, DataBaseHelper.COLUMN_MATRIX_VALUES,
            DataBaseHelper.COLUMN_BRIGHTNESS, DataBaseHelper.COLUMN_CONTRAST,
            DataBaseHelper.COLUMN_FILTER, DataBaseHelper.COLUMN_TEMPLATE,
            DataBaseHelper.COLUMN_SVG_COLOR};

    public PrintsDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPrint(PrintDataSet print) {
        ContentValues values = getValues(print);
        database.insert(DataBaseHelper.TABLE_PRINTS, null, values);
    }

    public void deletePrint(PrintDataSet print) {
        long id = print.getId();
        database.delete(DataBaseHelper.TABLE_PRINTS, DataBaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<PrintDataSet> getAllPrints() {
        List<PrintDataSet> prints = new ArrayList<>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_PRINTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PrintDataSet print = cursorToPrint(cursor);
            prints.add(print);
            cursor.moveToNext();
        }

        cursor.close();
        return prints;
    }

    public PrintDataSet getPrintById(int id){
        Cursor cursor = database.query(DataBaseHelper.TABLE_PRINTS, allColumns, DataBaseHelper.COLUMN_ID
                + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        PrintDataSet printDataSet = cursorToPrint(cursor);
        cursor.close();

        return printDataSet;
    }

    private  ContentValues getValues(PrintDataSet dataSet){
        ContentValues values = new ContentValues();

        ImageDataSet imageDataSet = dataSet.getImageDataSet();

        values.put(DataBaseHelper.COLUMN_RESULT_PATH, imageDataSet.getResultImagePath());
        values.put(DataBaseHelper.COLUMN_ORIGIN_PATH, imageDataSet.getOriginImagePath());
        values.put(DataBaseHelper.COLUMN_ORIGIN_URL, imageDataSet.getOriginImageUrl());
        values.put(DataBaseHelper.COLUMN_ORIGIN_ID, imageDataSet.getOriginImageId());
        values.put(DataBaseHelper.COLUMN_SCALE, imageDataSet.getScale());

        TextDataSet textDataSet = dataSet.getTextDataSet();

        values.put(DataBaseHelper.COLUMN_TEXT, textDataSet.getText());
        values.put(DataBaseHelper.COLUMN_TEXT_COLOR, textDataSet.getTextColor());
        values.put(DataBaseHelper.COLUMN_TEXT_FONT, textDataSet.getTextFont());
        values.put(DataBaseHelper.COLUMN_TEXT_GRAVITY, textDataSet.getTextGravity());
        values.put(DataBaseHelper.COLUMN_TEXT_SIZE, textDataSet.getTextSize());

        FilterDataSet filterDataSet = dataSet.getFilterDataSet();

        values.put(DataBaseHelper.COLUMN_MATRIX_VALUES, filterDataSet.getMatrixValuesString());
        values.put(DataBaseHelper.COLUMN_BRIGHTNESS, filterDataSet.getBrightness());
        values.put(DataBaseHelper.COLUMN_CONTRAST, filterDataSet.getContrast());
        values.put(DataBaseHelper.COLUMN_FILTER, filterDataSet.getFilter());
        values.put(DataBaseHelper.COLUMN_TEMPLATE, filterDataSet.getTemplate());
        values.put(DataBaseHelper.COLUMN_SVG_COLOR, filterDataSet.getSvgColor());

        return values;
    }

    private PrintDataSet cursorToPrint(Cursor cursor) {
        PrintDataSet dataSet = new PrintDataSet();

        dataSet.setId(cursor.getInt(0));

        ImageDataSet imageDataSet = new ImageDataSet();
        TextDataSet textDataSet = new TextDataSet();
        FilterDataSet filterDataSet = new FilterDataSet();

        imageDataSet.setResultImagePath(cursor.getString(1));
        imageDataSet.setOriginImagePath(cursor.getString(2));
        imageDataSet.setOriginImageUrl(cursor.getString(3));
        imageDataSet.setOriginImageId(cursor.getInt(4));
        imageDataSet.setScale(cursor.getFloat(5));

        textDataSet.setText(cursor.getString(6));
        textDataSet.setTextColor(cursor.getInt(7));
        textDataSet.setTextFont(cursor.getString(8));
        textDataSet.setTextGravity(cursor.getInt(9));
        textDataSet.setTextSize(cursor.getInt(10));

        filterDataSet.setMatrixValues(cursor.getString(11));
        filterDataSet.setBrightness(cursor.getFloat(12));
        filterDataSet.setContrast(cursor.getFloat(13));
        filterDataSet.setFilter(cursor.getInt(14));
        filterDataSet.setTemplate(cursor.getInt(15));
        filterDataSet.setSvgColor(cursor.getInt(16));

        dataSet.setImageDataSet(imageDataSet);
        dataSet.setTextDataSet(textDataSet);
        dataSet.setFilterDataSet(filterDataSet);

        return dataSet;
    }
}
