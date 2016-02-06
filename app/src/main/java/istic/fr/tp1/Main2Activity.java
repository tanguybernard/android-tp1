package istic.fr.tp1;

import android.os.Bundle;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.LoaderManager;

import istic.fr.tp1.adapter.MyAdapter;
import istic.fr.tp1.sql.ContactsDb;

public class Main2Activity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private MyAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        displayListView();

        Button add = (Button) findViewById(R.id.button_validate);
        add.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // starts a new Intent to add a Contact
                Intent contactEdit = new Intent(getBaseContext(), ContactEdit.class);
                startActivity(contactEdit);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }

    private void displayListView() {


        // The desired columns to be bound
        String[] columns = new String[] {
                ContactsDb.CONTACT_NAME,
                ContactsDb.CONTACT_LASTNAME,
                ContactsDb.CONTACT_BIRTHDAY,
                ContactsDb.CONTACT_CITY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.display_name,
                R.id.display_lastname,
                R.id.display_birthday,
                R.id.display_city,
        };

        // get reference to the ListView
        ListView listView = (ListView) findViewById(R.id.contactListView);

        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);
    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ContactsDb._ID,
                ContactsDb.CONTACT_NAME,
                ContactsDb.CONTACT_LASTNAME,
                ContactsDb.CONTACT_BIRTHDAY,
                ContactsDb.CONTACT_CITY};
        CursorLoader cursorLoader = new CursorLoader(this,
                ContactContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
