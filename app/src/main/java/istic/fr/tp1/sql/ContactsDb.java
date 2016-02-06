package istic.fr.tp1.sql;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by tbernard on 21/01/16.
 */
public class ContactsDb {

    public static final String _ID = "_id";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_LASTNAME = "lastname";
    public static final String CONTACT_BIRTHDAY = "birthday";
    public static final String CONTACT_CITY = "city";

    private static final String LOG_TAG = "ContactsDb";
    public static final  String SQLITE_TABLE = "Contact2";


    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + "( " +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CONTACT_NAME + "," +
                    CONTACT_LASTNAME + "," +
                    CONTACT_BIRTHDAY + "," +
                    CONTACT_CITY + " );";

                    //" UNIQUE (" + KEY_CODE +"));";

    public static void onCreate(SQLiteDatabase db) {

        Log.w(LOG_TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);

        System.out.println("CREATE DATABASE");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }


}
