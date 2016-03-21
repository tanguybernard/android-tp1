package istic.fr.tp1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import istic.fr.tp1.model.User;
import istic.fr.tp1.sql.ContactsDb;

public class ContactEdit extends Activity{

    private Spinner continentList;

    private EditText lastname, name, birthday, city;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);


        // get references to the buttons and attach listeners
        name = (EditText) findViewById(R.id.edit_name);
        lastname = (EditText) findViewById(R.id.edit_lastname);
        birthday = (EditText) findViewById(R.id.edit_date);
        city = (EditText) findViewById(R.id.edit_city);

        Bundle bundle = this.getIntent().getExtras();
        loadCOntactInfo();


    }

    public void validateForm(View v) {


        String myLastName = lastname.getText().toString();
        String myName = name.getText().toString();
        String myCity = city.getText().toString();
        String myBirthday = birthday.getText().toString();


        ContentValues values = new ContentValues();

        values.put(ContactsDb.CONTACT_NAME, myName);
        values.put(ContactsDb.CONTACT_LASTNAME, myLastName);
        values.put(ContactsDb.CONTACT_BIRTHDAY, myBirthday);
        values.put(ContactsDb.CONTACT_CITY, myCity);
        getContentResolver().insert(ContactContentProvider.CONTENT_URI, values);






        // check for blanks
        if(myName.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER name", Toast.LENGTH_LONG).show();
            return;
        }



        Intent intent = new Intent(getApplicationContext(),DisplayDataActivity.class);

        User user = new User(myName,myLastName,myBirthday,myCity);


        // ajout de données supplémentaires dans l'intent
        intent.putExtra("parcel", user);
        startActivity(intent);


    }


    // based on the rowId get all information from the Content Provider
    private void loadCOntactInfo(){

        String[] projection = {
                ContactsDb._ID,
                ContactsDb.CONTACT_NAME,
                ContactsDb.CONTACT_LASTNAME,
                ContactsDb.CONTACT_BIRTHDAY,
                ContactsDb.CONTACT_CITY
        };

        /*Uri uri = Uri.parse(ContactContentProvider.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String myName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.CONTACT_NAME));
            String myLastName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.CONTACT_LASTNAME));
            String myBirthday = cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.CONTACT_BIRTHDAY));
            String myCity = cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.CONTACT_CITY));
            lastname.setText(myLastName);
            name.setText(myName);
            birthday.setText(myBirthday);
            city.setText(myCity);

        }*/


    }

    // this sets the spinner selection based on the value
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }


    public void validateForm2(View view) {
        TextView textName= (TextView)this.findViewById(R.id.edit_name);
        String name = textName.getText().toString();
        TextView textLastName= (TextView)this.findViewById(R.id.edit_lastname);
        String lastname = textLastName.getText().toString();
        TextView textCity= (TextView)this.findViewById(R.id.edit_city);
        String city = textCity.getText().toString();
        TextView textBirthday= (TextView)this.findViewById(R.id.edit_date);
        String birthday = textBirthday.getText().toString();

        /*Toast.makeText(getApplicationContext(),
                elem, Toast.LENGTH_SHORT).show();*/

        // creation d'un intent pour appeler une autre activité (SecondaryActivity)
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);

        User user = new User(name,lastname,birthday,city);


        // ajout de données supplémentaires dans l'intent
        intent.putExtra("parcel",user);
        startActivity(intent);

    }



}




