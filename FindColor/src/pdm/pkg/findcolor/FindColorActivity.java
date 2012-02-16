package pdm.pkg.findcolor;

import java.sql.Connection;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class FindColorActivity extends Activity {
    /** Called when the activity is first created. */
	Connection connection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Variables
        final ImageButton button = (ImageButton) findViewById(R.id.imageButton1);
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		//Menu
        		registerForContextMenu(button);
        		//Connection
        		 try{
        	        	ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        	        	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        	        	connection = new XMPPConnection(config);
        	        	connection.connect();
        	        	//i dati di log-in saranno inseriti tramite context menu
        	        	connection.login("marangon","marangon");
        	        	Intent intent = new Intent(FindColorActivity.this, Second.class);
                		Log.d("LOG","START GAME");
                		startActivity(intent);
                		
        	        }catch (XmlPullParserException e) {
        	        	e.printStackTrace();
        	        }
        		}
        });
        
    }
    
    @Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
     menu.setHeaderTitle("Context Menu");  
     menu.add(0, v.getId(), 0, "Nickname");  
     menu.add(0, v.getId(), 0, "Password");  
    }  
    
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        if(item.getTitle()=="Nickname"){function1(item.getItemId());}  
        else if(item.getTitle()=="Password"){function2(item.getItemId());}  
        else {return false;}  
    return true;  
    }  
  
    public void function1(int id){  
        Toast.makeText(this, "now set nick", Toast.LENGTH_SHORT).show();  
    }  
    public void function2(int id){  
        Toast.makeText(this, "now set password", Toast.LENGTH_SHORT).show();  
    }  
}