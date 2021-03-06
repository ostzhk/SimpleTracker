package net.simple_tracker.simpletracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {
    //DB settings
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbo";

    //Tables cells
    private static final String OUTLAY_INFO = "outlay";
    private static final String ID = "id";
    private static final String COUNT = "count";
    private static final String DATE = "date";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String CATEGORY_TABLE = "category_table";
    private static final String ICON = "icon";
    Context context;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OUTLAY_INFO = "CREATE TABLE " + OUTLAY_INFO + "("
                + ID + " INTEGER PRIMARY KEY," + COUNT + " INTEGER NOT NULL,"
                + CATEGORY_NAME + " TEXT," + DATE + " DATE)";

        String CREATE_CATEGORY = "CREATE TABLE " + CATEGORY_TABLE + "("
                + ID + " INTEGER PRIMARY KEY," + CATEGORY_NAME + " TEXT NOT NULL," + ICON + " INTEGER NOT NULL)";

        String addTransport = "INSERT INTO " + CATEGORY_TABLE + " (" + CATEGORY_NAME + ", " + ICON + ") " +
                "VALUES ('Транспорт', " + R.drawable.ic_category_transport + ")";
        String addCar = "INSERT INTO " + CATEGORY_TABLE + " (" + CATEGORY_NAME + ", " + ICON + ") " +
                "VALUES ('Машина', " + R.drawable.ic_category_car + ")";
        String addEat = "INSERT INTO " + CATEGORY_TABLE + " (" + CATEGORY_NAME + ", " + ICON + ") " +
                "VALUES ('Еда', " + R.drawable.ic_category_eat + ")";
        String addHealth = "INSERT INTO " + CATEGORY_TABLE + " (" + CATEGORY_NAME + ", " + ICON + ") " +
                "VALUES ('Здоровье', " + R.drawable.ic_category_health + ")";
        String addShop = "INSERT INTO " + CATEGORY_TABLE + " (" + CATEGORY_NAME + ", " + ICON + ") " +
                "VALUES ('Магазин', " + R.drawable.ic_category_shop + ")";
        db.execSQL(CREATE_OUTLAY_INFO);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(addTransport);
        db.execSQL(addCar);
        db.execSQL(addEat);
        db.execSQL(addHealth);
        db.execSQL(addShop);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void addOutlay(Outlay outlay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, outlay.getCategoryName());
        values.put(COUNT, outlay.getCount());
        values.put(DATE, outlay.getDate());
        db.insert(OUTLAY_INFO, null, values);
        db.close();
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getCategoryName());
        values.put(ICON, category.getIcon());
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }



    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                category.setId(Integer.parseInt(cursor.getString(0)));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        return categoryList;
    }

    public Map<String, Outlay> getSumOfCategory(String date, String date2) {
        Map<String, Outlay> outlayList = new HashMap<>();
        String selectQuery = "SELECT CATEGORY_NAME, SUM(COUNT), DATE FROM " + OUTLAY_INFO + " WHERE DATE BETWEEN '" + date + "' " +
                "AND " + "'" + date2 + "' GROUP BY CATEGORY_NAME ORDER BY COUNT DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(0), cursor.getString(2), Integer.parseInt(cursor.getString(1)));
                outlayList.put(cursor.getString(0), outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }

    public void deleteThisOutlay(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + OUTLAY_INFO + " WHERE " + ID + " = " + id;
        db.execSQL(query);
    }

    public void deleteThisCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CATEGORY_TABLE + " WHERE " + ID + " = " + id;
        db.execSQL(query);
    }

    public void deleteOutlays() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OUTLAY_INFO, null, null);
    }

    public Outlay selectOutlay(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, DATE, CATEGORY_NAME, COUNT FROM " + OUTLAY_INFO + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Outlay outlay = null;
        if (cursor.moveToFirst()) {
            do {
                outlay = new Outlay(cursor.getString(2), cursor.getString(1), Integer.parseInt(cursor.getString(3)));
                outlay.setId(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        return outlay;
    }

    public void changeOutlay(Outlay outlay) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + OUTLAY_INFO + " SET DATE='" + outlay.getDate() + "', CATEGORY_NAME='" +
                outlay.getCategoryName() + "', COUNT=" + outlay.getCount() + " WHERE " + ID + " = " + outlay.getId();
        db.execSQL(query);
    }

    public void changeCategory(int id, String categoryName, int iconId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + CATEGORY_TABLE + " SET CATEGORY_NAME='" + categoryName + "', ICON=" +
                iconId +  " WHERE " + ID + " = " + id;
        db.execSQL(query);
    }

    public String getCategoryName(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT CATEGORY_NAME FROM " + CATEGORY_TABLE + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }return null;
    }

    public ArrayList<String> getDates() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT distinct DATE FROM " + OUTLAY_INFO;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> dates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                dates.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return dates;
    }

    public List<Outlay> getAllOutlaysByDate(String date) {
        List<Outlay> outlayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + OUTLAY_INFO + " WHERE DATE = '" + date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(1)));
                outlay.setId(Integer.parseInt(cursor.getString(0)));
                outlayList.add(outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }

    public List<Outlay> getAllOutlaysByCategory(String category) {
        List<Outlay> outlayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + OUTLAY_INFO + " WHERE CATEGORY_NAME = '" + category + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(1)));
                outlay.setId(Integer.parseInt(cursor.getString(0)));
                outlayList.add(outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }
}