package com.neuroandroid.pyreader.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neuroandroid.pyreader.model.response.ChapterRead;

/**
 * Created by NeuroAndroid on 2017/7/17.
 */

public class PYReaderStore extends SQLiteOpenHelper {
    @Nullable
    private static PYReaderStore sInstance = null;
    private static final String DATABASE_NAME = "py_reader.db";
    private static final String TABLE_NAME = "novel";

    public PYReaderStore(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase, TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createTable(@NonNull final SQLiteDatabase sqLiteDatabase, String tableName) {
        String sql = "create table " + tableName +
                " (book_id string, chapter integer not null, body text)";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 单例模式
     */
    @NonNull
    public static synchronized PYReaderStore getInstance(@NonNull final Context context) {
        if (sInstance == null) {
            sInstance = new PYReaderStore(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * 添加章节内容
     */
    public void addChapter(int chapter, String bookId, String body) {
        if (findChapterByBookId(chapter, bookId)) {  // 如果没有添加过才去添加
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME, null, getContentValues(chapter, bookId, body));
            db.close();
        }
    }

    /**
     * 获取缓存的章节信息
     */
    public ChapterRead.Chapter getChapter(int chapter, String bookId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "book_id=? and chapter=?", new String[]{bookId, String.valueOf(chapter)},
                null, null, null);
        cursor.moveToNext();
        ChapterRead.Chapter data = new ChapterRead.Chapter();
        data.setBody(cursor.getString(cursor.getColumnIndex("body")));
        return data;
    }

    /**
     * 根据bookId查询有没有此章节的内容
     * 防止添加时重复添加
     */
    public boolean findChapterByBookId(int chapter, String bookId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "book_id=? and chapter=?", new String[]{bookId, String.valueOf(chapter)},
                null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count == 0;
    }

    private ContentValues getContentValues(int chapter, String bookId, String body) {
        ContentValues values = new ContentValues();
        values.put("book_id", bookId);
        values.put("chapter", chapter);
        values.put("body", body);
        return values;
    }
}
