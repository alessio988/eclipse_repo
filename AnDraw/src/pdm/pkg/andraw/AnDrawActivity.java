package pdm.pkg.andraw;

import java.sql.Connection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnDrawActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText nickName;
	Connection connection;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        nickName = (EditText) findViewById(R.id.editText1);
        final Button startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		  String ID = nickName.getText().toString();
        	 try{
        		ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
				config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
				connection = (Connection) new XMPPConnection(config);
   	        	((XMPPConnection) connection).connect();
				((org.jivesoftware.smack.Connection) connection).login(ID, null);
				Intent intent = new Intent(AnDrawActivity.this,Second.class);
				intent.putExtra("nickname", ID);
				startActivity(intent);
        		  }
        	 catch(XMPPException e){
        		    Toast.makeText(getApplicationContext(), "Connessione Fallita", Toast.LENGTH_LONG);
      	        	Intent intent = new Intent(AnDrawActivity.this,AnDrawActivity.class);
      	        	startActivity(intent);
        		  }
        	                		
        	}
        });
        	        
    }
}