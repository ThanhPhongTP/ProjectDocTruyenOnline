package com.example.truyen.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {
    public static DatabaseSQLite database;

    public DatabaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    public void insertStory(int story_id, String story_title, String author, int rating, int rating_count, String description, String thumbnail_image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Stories VALUES(null, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, story_id);
        statement.bindString(2, story_title);
        statement.bindString(3, author);
        statement.bindDouble(4, rating);
        statement.bindDouble(5, rating_count);
        statement.bindString(6, description);
        statement.bindString(7, thumbnail_image);
        statement.executeInsert();
    }

    public void insertChapter(int chapter_id, int story_id, String title, String created_at, String contents){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Chapter VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, chapter_id);
        statement.bindDouble(2, story_id);
        statement.bindString(3, title);
        statement.bindString(4, created_at);
        statement.bindString(5, contents);
        statement.executeInsert();
    }

    public void deleteStory(int nID) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete("Stories", "story_id =?" , new String[]{String.valueOf(nID)});
        database.delete("Chapter", "story_id =?" , new String[]{String.valueOf(nID)});
        database.close();


//        String sql = "DELETE FROM Memory WHERE Content = " + Content;

//        database.execSQL(sql);
//        SQLiteStatement statement = database.compileStatement(sql);
//
//        statement.clearBindings();
//        statement.bindString(1, Content);
////        statement.bindString(1, Date);
////        statement.bindBlob(2, Image);
//        statement.executeUpdateDelete();
    }

//    public void updateData(String Date,String Content, byte[] Image, String s1, String s2) {
//        TimeLine timeLine = new TimeLine();
//        SQLiteDatabase database = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("Date", Date);
//        values.put("Content", Content);
//        values.put("Imgages", Image);
//
//        database.update("Memory", values, "Date = ? AND Content = ?", new String[]{s1, s2});
//        database.close();
//    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    String sql;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            GetData(sql);
        }
    }
}
