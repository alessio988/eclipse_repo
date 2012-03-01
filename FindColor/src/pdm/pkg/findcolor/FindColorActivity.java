package pdm.pkg.findcolor;

import java.sql.Connection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FindColorActivity extends Activity{
    /** Called when the activity is first created. */
	Connection connection;
	EditText nick;
	EditText pass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Variables
        final ImageButton button = (ImageButton) findViewById(R.id.imageButton1);
        nick = (EditText) findViewById(R.id.editText1);
		pass = (EditText) findViewById(R.id.editText2);
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {       		
        	/*	    ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
					config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
					connection = (Connection) new XMPPConnection(config);
					try {
						((XMPPConnection) connection).connect();
					} catch (XMPPException e1) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), "Contact Service Provider" , Toast.LENGTH_LONG).show();
						e1.printStackTrace();
					}
					try {
						((org.jivesoftware.smack.Connection) connection).login(nick.getText().toString(),pass.getText().toString());
						//Intent che passa alla seconda Activity
						Intent intent = new Intent(FindColorActivity.this, Second.class);
						Log.d("LOG","START GAME");
						startActivity(intent);
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), "Insert Nick and Password to connect on server" , Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}					
			*/
        		Intent intent = new Intent(FindColorActivity.this, Second.class);
				startActivity(intent);
        		}
        });
        
    }
}