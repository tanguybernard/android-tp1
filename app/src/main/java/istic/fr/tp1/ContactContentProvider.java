package istic.fr.tp1;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import istic.fr.tp1.sql.ContactsDb;
import istic.fr.tp1.sql.MyDatabaseHelper;


public class ContactContentProvider extends ContentProvider{

    private MyDatabaseHelper dbHelper;

    static final String PROVIDER_NAME = "istic.fr.tp1.ContactContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);


    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
    }


    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";

            /**
             * Get a particular student
             */
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println(3333);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert(ContactsDb.SQLITE_TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ContactsDb.SQLITE_TABLE);

            switch (uriMatcher.match(uri)) {
                case STUDENTS:
                    //do nothing
                    break;
                case STUDENT_ID:
                    String id = uri.getPathSegments().get(1);
                    queryBuilder.appendWhere(ContactsDb._ID + "=" + id);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }

            Cursor cursor = queryBuilder.query(db, projection, selection,
                    selectionArgs, null, null, sortOrder);
            return cursor;


    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                //do nothing
                break;
            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                selection = ContactsDb._ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(ContactsDb.SQLITE_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                //do nothing
                break;
            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                selection = ContactsDb._ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(ContactsDb.SQLITE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}