package com.teamtreehouse.mememaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.teamtreehouse.mememaker.models.Meme;
import com.teamtreehouse.mememaker.models.MemeAnnotation;

import java.util.ArrayList;

public class MemeDataSource {

    private Context mContext;
    private MemeSQLiteHelper mMemeSqliteHelper;

    public MemeDataSource(Context context) {
        mContext = context;
        mMemeSqliteHelper = new MemeSQLiteHelper(context);
        SQLiteDatabase database = mMemeSqliteHelper.getReadableDatabase();
        database.close();
    }

    private SQLiteDatabase open() {
        return mMemeSqliteHelper.getWritableDatabase();
    }

    private void close (SQLiteDatabase database) {
        database.close();
    }

    public void create (Meme meme) {
        SQLiteDatabase database = open();

        // implementations
        ContentValues memeValues = new ContentValues();
        memeValues.put(MemeSQLiteHelper.COLUMN_MEME_NAME, meme.getName());
        memeValues.put(MemeSQLiteHelper.COLUMN_MEME_ASSET, meme.getAssetLocation());
        long memeID = database.insert(MemeSQLiteHelper.MEMES_TABLE,null,memeValues);

        for (MemeAnnotation annotation : meme.getAnnotations()) {
            ContentValues annotationValues = new ContentValues();
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATION_COLOR,  annotation.getColor());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATION_TITLE, annotation.getTitle());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATION_X, annotation.getLocationX());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATION_X, annotation.getLocationY());
            annotationValues.put(MemeSQLiteHelper.COLUMN_FOREIGN_KEY_MEME, memeID);

            database.insert(MemeSQLiteHelper.ANNOTATIONS_TABLE, null, annotationValues);
        }


        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }
}













