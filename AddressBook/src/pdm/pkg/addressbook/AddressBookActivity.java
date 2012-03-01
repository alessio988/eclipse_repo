package pdm.pkg.addressbook;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;


public class AddressBookActivity extends Activity {
    /** Called when the activity is first created. */
	
	//Variabili per contatti
	String rawContactId;
	String phoneNumber;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Inserimento dei valori nella rubrica
        ContentValues values = new ContentValues();
    	values.put(Phone.RAW_CONTACT_ID, rawContactId);
    	values.put(Phone.NUMBER, phoneNumber);
    	values.put(Phone.TYPE, Phone.TYPE_MOBILE);
    	Uri uri = getContentResolver().insert(Phone.CONTENT_URI, values);
    	
    	Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        Uri res = ContactsContract.Contacts.lookupContact(getContentResolver(), lookupUri);

    }
}