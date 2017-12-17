package itac.yzu.bmi6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // DEFINE THE DATABASE AND TABLE
    private String DB_Table = "Profile";
    private static final String DB_NAME = "User.db";
    public static final int VERSION = 1;

    // DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String NAME_COL = "Name";
    private static final String GENDER_COL = "Gender";
    private static final String HEIGHT_COL = "Height";
    private static final String WEIGHT_COL = "Weight";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + DB_Table + " (" +
                NAME_COL + " TEXT PRIMARY KEY, " +
                GENDER_COL + " TEXT, " +
                HEIGHT_COL + " DOUBLE, " +
                WEIGHT_COL + " DOUBLE " + ")";

        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        onCreate(db);
    }

    // INSERT ONE RECORD TO DATABASE
    public void addUserProfile(UserProfile up) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, up.getName());
        values.put(GENDER_COL, up.getGender());
        values.put(HEIGHT_COL, up.getHeight());
        values.put(WEIGHT_COL, up.getWeight());

        db.insert(DB_Table, null, values);
        db.close();
    }

    // CHECK IF DATA EXISTS
    public boolean UserProfileExists(String name) {

        // Make Query
        String ifExistsQuery = "SELECT " + NAME_COL + " FROM " + DB_Table
                + " WHERE " + NAME_COL + " = '" + name + "' LIMIT 1";
        Log.d("YEE","IFEXIST: "+ifExistsQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(ifExistsQuery, null);

        // Check if exists
        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        db.close();

        return exists;
    }
    
    // UPDATE ONE RECORD
    public void updateUserProfile(UserProfile up) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GENDER_COL, up.getGender());
        values.put(HEIGHT_COL, up.getHeight());
        values.put(WEIGHT_COL, up.getWeight());

        db.update(DB_Table, values, NAME_COL+"=?",
                new String[] { up.getName() } );
        db.close();
    }

    // GET ALL RECORDS FROM DATABASE
    public ArrayList<UserProfile> getAllUserProfile() {
        ArrayList<UserProfile> UserProfileList = new ArrayList<>();
        String getAllQuery = "SELECT * FROM " + DB_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String gender = cursor.getString(1);
                Double height = cursor.getDouble(2);
                Double weight = cursor.getDouble(3);
                UserProfile up = new UserProfile(name, gender, height, weight);
                UserProfileList.add(up);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return UserProfileList;
    }

    // CLEAR ALL
    public void clearDB() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        this.onCreate(db);
    }
}
