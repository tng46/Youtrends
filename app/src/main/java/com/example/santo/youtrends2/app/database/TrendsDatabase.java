package com.example.santo.youtrends2.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.santo.youtrends2.app.pojo.Trend;

import java.util.ArrayList;
import java.util.Date;


public class TrendsDatabase {


    private TrendsDBHelper mHelper;
    private SQLiteDatabase mDatabase;

    public TrendsDatabase (Context context) {
        mHelper = new TrendsDBHelper(context);
        mDatabase = mHelper.getWritableDatabase();

    }

    public void insertTrends (ArrayList<Trend> listTrends, boolean clearPrevious ) {
        if (clearPrevious) {
            deleteTrends();
        }

        String sql = "INSERT OR REPLACE INTO trends" + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i< listTrends.size(); i++) {
            Trend currentTrend = listTrends.get(i);
            statement.clearBindings();
            statement.bindString(1, currentTrend.getId());
            statement.bindString(2, currentTrend.getTitle());
            statement.bindLong(3, Long.parseLong(currentTrend.getPosition()));
            statement.bindLong(4, currentTrend.getPublishedAt().getTime());
            statement.bindString(5, (currentTrend.getRegion() == null) ? "ALL" : currentTrend.getRegion());
            statement.bindLong(6, currentTrend.getCategoryId());
            statement.bindLong(7, currentTrend.getTrendDifference());
            statement.bindString(8, currentTrend.getUrlThumb());
            statement.bindLong(9, Long.parseLong(currentTrend.getPosition()));

            statement.execute();

        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();

    }


    public ArrayList<Trend> readTrends(int categoryId, int limit, String region, String whereClausule){
        ArrayList<Trend> listTrends = new ArrayList<>();

        String[] columns = {
                            TrendsDBHelper.COLUMN_ID,
                            TrendsDBHelper.COLUMN_TITLE,
                            TrendsDBHelper.COLUMN_LAST_POSITION,
                            TrendsDBHelper.COLUMN_DATE,
                            TrendsDBHelper.COLUMN_REGION,
                            TrendsDBHelper.COLUMN_CATEGORY_ID,
                            TrendsDBHelper.COLUMN_TREND_DIFFERENCE,
                            TrendsDBHelper.COLUMN_THUMB_URL,
                            TrendsDBHelper.COLUMN_TRIGGER_POSITION

        };


        Cursor cursor = mDatabase.query("trends",
                columns,
                TrendsDBHelper.COLUMN_CATEGORY_ID + "=?" + " and " +
                        TrendsDBHelper.COLUMN_REGION + "=?" +
                        whereClausule,
                new String[]{"" + categoryId, region},
                null,
                null,
                TrendsDBHelper.COLUMN_LAST_POSITION,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Trend currentTrend = new Trend();

                currentTrend.setId(cursor.getString((cursor.getColumnIndex(TrendsDBHelper.COLUMN_ID))));

                currentTrend.setTitle(cursor.getString((cursor.getColumnIndex(TrendsDBHelper.COLUMN_TITLE))));

                currentTrend.setPosition("" + cursor.getInt((cursor.getColumnIndex(TrendsDBHelper.COLUMN_LAST_POSITION))));

                long publishedAt = cursor.getLong(cursor.getColumnIndex(TrendsDBHelper.COLUMN_DATE));
                currentTrend.setPublishedAt(publishedAt != -1 ? new Date(publishedAt) : null);

                currentTrend.setCategoryId(cursor.getInt((cursor.getColumnIndex(TrendsDBHelper.COLUMN_CATEGORY_ID))));
                currentTrend.setRegion(cursor.getString((cursor.getColumnIndex(TrendsDBHelper.COLUMN_REGION))));

                currentTrend.setTrendDifference(cursor.getInt(cursor.getColumnIndex(TrendsDBHelper.COLUMN_TREND_DIFFERENCE)));

                currentTrend.setUrlThumb(cursor.getString(cursor.getColumnIndex(TrendsDBHelper.COLUMN_THUMB_URL)));

                if (currentTrend.getPosition().compareTo("0")!=0)
                    listTrends.add(currentTrend);

            }
            while (cursor.moveToNext());
        }

        return listTrends;
    }

    public int getLastPositionToSetDifference (String id_video, int categoryId, String region) {

        Cursor cursor = mDatabase.query("trends",
                new String[] {TrendsDBHelper.COLUMN_TRIGGER_POSITION},
                TrendsDBHelper.COLUMN_ID + "=?" + " and "  +
                        TrendsDBHelper.COLUMN_CATEGORY_ID + "=?" + " and "  +
                        TrendsDBHelper.COLUMN_REGION + "=?",
                new String[] {id_video, ""+categoryId, region},
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(TrendsDBHelper.COLUMN_TRIGGER_POSITION);
            return cursor.getInt(index);
        }

        return -100;
    }

    public void deletePosition(int categoryId, String region) {
        ContentValues args = new ContentValues();
        args.put(TrendsDBHelper.COLUMN_LAST_POSITION, "0");
        mDatabase.update(TrendsDBHelper.TABLE_TRENDS,
                args,
                TrendsDBHelper.COLUMN_CATEGORY_ID+"=" + categoryId + " and "+TrendsDBHelper.COLUMN_REGION+"='"+region+"'",
                null);
    }


    public void deleteTrends() {
        mDatabase.delete("trends", null, null);
    }


    private static class TrendsDBHelper extends SQLiteOpenHelper {


        public static final String TABLE_TRENDS = "trends";
        public static final String COLUMN_ID = "id_video";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LAST_POSITION = "last_position";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_TREND_DIFFERENCE = "trend_difference";
        public static final String COLUMN_THUMB_URL = "thumb_url";
        public static final String COLUMN_TRIGGER_POSITION = "trigger_position";
        private static final String CREATE_TABLE_TRENDS = "CREATE TABLE "+ TABLE_TRENDS +" ( " +
                COLUMN_ID + " TEXT NOT NULL," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_LAST_POSITION + " INTEGER," +
                COLUMN_DATE + " INTEGER," +
                COLUMN_REGION + " TEXT NOT NULL," +
                COLUMN_CATEGORY_ID + " INTEGER NOT NULL," +
                COLUMN_TREND_DIFFERENCE + " INTEGER," +
                COLUMN_THUMB_URL + " TEXT," +
                COLUMN_TRIGGER_POSITION + " INTEGER," +
                "PRIMARY KEY (" + COLUMN_ID + ", "+ COLUMN_REGION +", "+ COLUMN_CATEGORY_ID +"));";


        private Context mContext;
        private static final String DB_NAME = "youTrends_db";
        private static final int DB_VERSION = 1;


        public TrendsDBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }



        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL(CREATE_TABLE_TRENDS);
            } catch (SQLException e) {

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(" DROP TABLE " + TABLE_TRENDS + " IF EXISTS;");
                onCreate(db);

            } catch (SQLException e) {

            }

        }
    }
}
