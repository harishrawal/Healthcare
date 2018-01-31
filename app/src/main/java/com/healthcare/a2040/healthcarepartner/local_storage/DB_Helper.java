package com.healthcare.a2040.healthcarepartner.local_storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DB_Helper extends SQLiteOpenHelper {


    /// ORM use in android

    private static final String TAG = DB_Helper.class.getSimpleName();

    public static final int DB_Version = 1;
    public static final String DB_Name = "ObeyCabDB";


    public static final String t_Notification = "Notification";

    public DB_Helper(Context context) {
        super(context, DB_Name, null, DB_Version);
//        super(context, (Environment.getExternalStorageDirectory() + "/TMI_Invoices/" + DB_Name), null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        createTablesVersion1(sqLiteDatabase);


        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        onCreate(sqLiteDatabase);
    }


    public HashMap<String, String> getUserDetais() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM table_Name";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("password", cursor.getString(3));
            user.put("phone", cursor.getString(4));
        }

        cursor.close();
        sqLiteDatabase.close();

        Log.d(TAG, "Fetching user from Sqlite :" + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete("table Name", null, null);
        sqLiteDatabase.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    public int getTableRowsCount(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        int count = -1;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        Log.e("table_count", count + "");
        return count;

    }

    public int getTableLastID(String Table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from " + Table + "  order by  id desc limit 1", null);
        int count = -1;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        Log.e("table_count", count + "");
        return count;

    }

    public String getTableLastEntry(String Table, String Colm, @Nullable String whereclause) {
        String result = null;
        Cursor cursor = null;

        SQLiteDatabase db = this.getReadableDatabase();

        if (whereclause != null && !whereclause.equals("null")) {
            cursor = db.rawQuery("select " + Colm + " from " + Table + " where " + whereclause + "", null);
        } else {
            cursor = db.rawQuery("select " + Colm + " from " + Table + "", null);
        }
        if (cursor != null && cursor.moveToLast()) {
            result = cursor.getString(cursor.getColumnIndex(Colm));

        } else {
            result = "null";
        }
        return result;
    }

    public List<String> getSingleColumList(String query) {
        List<String> labels = new ArrayList<String>();
        String selectQuery = query;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return labels;
    }

    public void executeWithoutReturn(String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        Log.e("writeQuery", SQL);
    }

    public Cursor executeWithReturn(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SQL, null);
        Log.e("readQuery", SQL);
        return c;
    }

    public String executeWith_singleReturn(String Table, String Colm, @Nullable String whereclause) {
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + Colm + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + Colm + " from " + Table + "", null);
        }
        if (c != null && c.moveToFirst()) {
            result = c.getString(c.getColumnIndex(Colm));
        } else {
            result = "";
        }
        // db.close();
        return result;
    }

    public String executeWith_LastsingleReturn(String Table, String Colm, @Nullable String whereclause) {
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + Colm + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + Colm + " from " + Table + " order by id desc", null);
        }
        if (c != null && c.moveToFirst()) {
            //  result = c.getString(0);
            result = c.getString(c.getColumnIndex(Colm));
            Log.d("result--", result);
        }
        // db.close();
        return result;
    }

    public String[] get_ThreeColumn_SIngleReturn(String Table, String[] TwoColumn, @Nullable String whereclause) {

        String[] result = new String[3];

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + " from " + Table + "", null);
        }

        if (c != null && c.moveToFirst()) {
            result[0] = c.getString(c.getColumnIndex(TwoColumn[0]));
            result[1] = c.getString(c.getColumnIndex(TwoColumn[1]));
            result[2] = c.getString(c.getColumnIndex(TwoColumn[2]));
        } else {
            result[0] = "Not Found";
            result[1] = "Not Found";
            result[2] = "Not Found";

        }

        return result;

    }

    public String[] get_FourColumn_SIngleReturn(String Table, String[] TwoColumn, @Nullable String whereclause) {

        String[] result = new String[4];

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + " from " + Table + "", null);
        }

        if (c != null && c.moveToFirst()) {
            result[0] = c.getString(c.getColumnIndex(TwoColumn[0]));
            result[1] = c.getString(c.getColumnIndex(TwoColumn[1]));
            result[2] = c.getString(c.getColumnIndex(TwoColumn[2]));
            result[3] = c.getString(c.getColumnIndex(TwoColumn[3]));
        } else {
            result[0] = "Not Found";
            result[1] = "Not Found";
            result[2] = "Not Found";
            result[3] = "Not Found";

        }

        return result;

    }

    public String[] get_FiveColumn_SIngleReturn(String Table, String[] TwoColumn, @Nullable String whereclause) {

        String[] result = new String[5];

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + ", " + TwoColumn[4] + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + ", " + TwoColumn[4] + " from " + Table + "", null);
        }

        if (c != null && c.moveToFirst()) {
            result[0] = c.getString(c.getColumnIndex(TwoColumn[0]));
            result[1] = c.getString(c.getColumnIndex(TwoColumn[1]));
            result[2] = c.getString(c.getColumnIndex(TwoColumn[2]));
            result[3] = c.getString(c.getColumnIndex(TwoColumn[3]));
            result[4] = c.getString(c.getColumnIndex(TwoColumn[4]));
        } else {
            result[0] = "Not Found";
            result[1] = "Not Found";
            result[2] = "Not Found";
            result[3] = "Not Found";
            result[4] = "Not Found";

        }

        return result;

    }

    public String[] get_SIxColumn_SIngleReturn(String Table, String[] TwoColumn, @Nullable String whereclause) {

        String[] result = new String[6];

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        if (whereclause != null && !whereclause.equals("null")) {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + ", " + TwoColumn[4] + ", " + TwoColumn[5] + " from " + Table + " where " + whereclause + "", null);
        } else {
            c = db.rawQuery("select " + TwoColumn[0] + " , " + TwoColumn[1] + ", " + TwoColumn[2] + ", " + TwoColumn[3] + ", " + TwoColumn[4] + ", " + TwoColumn[5] + " from " + Table + "", null);
        }

        if (c != null && c.moveToFirst()) {
            result[0] = c.getString(c.getColumnIndex(TwoColumn[0]));
            result[1] = c.getString(c.getColumnIndex(TwoColumn[1]));
            result[2] = c.getString(c.getColumnIndex(TwoColumn[2]));
            result[3] = c.getString(c.getColumnIndex(TwoColumn[3]));
            result[4] = c.getString(c.getColumnIndex(TwoColumn[4]));
            result[5] = c.getString(c.getColumnIndex(TwoColumn[5]));
        } else {
            result[0] = "Not Found";
            result[1] = "Not Found";
            result[2] = "Not Found";
            result[3] = "Not Found";
            result[4] = "Not Found";
            result[5] = "Not Found";

        }

        return result;

    }

    private void createTablesVersion1(SQLiteDatabase database) {

        database.execSQL("CREATE TABLE " + t_Notification + "  (id INTEGER PRIMARY KEY AUTOINCREMENT, fromId INTEGER, fromName TEXT, title TEXT, msg TEXT, notifDate TEXT)");


    }

}
