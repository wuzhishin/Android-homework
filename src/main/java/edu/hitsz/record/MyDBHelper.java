package edu.hitsz.record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDBHelper";
    private static final String DB_NAME = "rank.db";
    private static final int DB_VERSION = 1;
    private static MyDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private static final String TABLE_NAME = "user_info";

    private MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private MyDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static MyDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new MyDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new MyDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    public String getDBName() {
        if (mHelper != null) {
            return mHelper.getDatabaseName();
        } else {
            return DB_NAME;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "rank INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
                + "name VARCHAR NOT NULL,"
                + "score INTEGER NOT NULL,"
                + "time VARCHAR NOT NULL"
                + ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public int delete(String whereClause,String[] whereArgs) {
        int count = mDB.delete(TABLE_NAME, whereClause, whereArgs);
        return count;
    }

    public int deleteAll() {
        int count = mDB.delete(TABLE_NAME, "1=1", null);
        return count;
    }

    public long insert(Ranking rank) {
        LinkedList<Ranking> infoArray = new LinkedList<>();
        infoArray.add(rank);
        return insert(infoArray);
    }

    public long insert(LinkedList<Ranking> infoArray) {
        long result = -1;
        for (int i = 0; i < infoArray.size(); i++) {
            Ranking info = infoArray.get(i);
            LinkedList<Ranking> tempArray = new LinkedList<>();

            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();
            cv.put("name", info.getUsername());
            cv.put("score", info.getScore());
            cv.put("time", info.getRecordTime());
            result = mDB.insert(TABLE_NAME, "", cv);
            // 添加成功后返回行号，失败后返回-1
            if (result == -1) {
                return result;
            }
        }
        return result;
    }

    public void sortList(LinkedList<Ranking> records){
        Collections.sort(records);
        int rank = 1;
        for(Ranking record:records){
            record.setRank(rank);//排序后重新设置排名
            rank++;
        }
    }


    public LinkedList<Ranking> query(String condition) {
        String sql = String.format("select rank,name,score,time " +
                "from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: "+sql);
        LinkedList<Ranking> infoArray = new LinkedList<>();
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            for (;; cursor.moveToNext()) {
                Ranking info = new Ranking();
                info.setRank(cursor.getInt(0));
                info.setUsername(cursor.getString(1));
                info.setScore(cursor.getInt(2));
                //SQLite没有布尔型，用0表示false，用1表示true
                info.setRecordTime(cursor.getString(3));

                infoArray.add(info);
                if (cursor.isLast() == true) {
                    break;
                }
            }
        }
        cursor.close();
        sortList(infoArray);
        return infoArray;
    }
}
