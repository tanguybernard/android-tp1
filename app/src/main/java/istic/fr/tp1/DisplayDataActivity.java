package istic.fr.tp1;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.app.LoaderManager;

import istic.fr.tp1.adapter.MyAdapter;
import istic.fr.tp1.sql.ContactsDb;

public class DisplayDataActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    //private SimpleCursorAdapter dataAdapter;
    private CursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        displayListView();

        Button add = (Button) findViewById(R.id.button_new_client);
        add.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // starts a new Intent to add a Country
                Intent countryEdit = new Intent(getBaseContext(), ContactEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");
                countryEdit.putExtras(bundle);
                startActivity(countryEdit);
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
                R.id.display_city
        };

        // create an adapter from the SimpleCursorAdapter


        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.listviewitem,
                null,
                columns,
                to,
                0);

        System.out.println(dataAdapter.getCount());

        // get reference to the ListView
        ListView listView = (ListView) findViewById(R.id.contactListView);
        // Assign adapter to ListView

        listView.setAdapter(dataAdapter);

        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);

/*
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // display the selected country
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.CONTACT_NAME));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb._ID));

                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent countryEdit = new Intent(getBaseContext(), ContactEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", rowId);
                countryEdit.putExtras(bundle);
                startActivity(countryEdit);

            }
        });
*/
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
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        dataAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}