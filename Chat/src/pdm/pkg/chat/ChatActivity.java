package pdm.pkg.chat;

import java.sql.Connection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity {
    /** Called when the activity is first created. */
	
	 Connection connection;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Variabili inizializzazioni
        final EditText edt = (EditText) findViewById(R.id.editText1);
        final TextView tv = (TextView) findViewById(R.id.textView1);      
        Button btn = (Button) findViewById(R.id.button1);
        
        //Scrolling
        tv.setMovementMethod(new ScrollingMovementMethod());
                
        btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		
        		tv.append("ME: "+edt.getText().toString()+"\n");
        		Message msg = new Message();
        		msg.setTo("loreti@ppl.eln.uniroma2.it");
        		msg.setBody(edt.getText().toString());
        		((XMPPConnection) connection).sendPacket(msg);
        	}
        });
        
        try{
        	ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        	connection = (Connection) new XMPPConnection(config);
        	((XMPPConnection) connection).connect();
        	((org.jivesoftware.smack.Connection) connection).login("marangon","marangon");
        }catch (XMPPException e) {
        	e.printStackTrace();
        }
        
        ((org.jivesoftware.smack.Connection) connection).addPacketListener(new PacketListener() {
			
			@Override
			public void processPacket(Packet pkt) {
				// TODO Auto-generated method stub
				Message msg = (Message) pkt;
				String from = msg.getFrom();
				String body = msg.getBody();
				tv.append(from+" : "+body+"\n");
			}
			},new MessageTypeFilter(Message.Type.normal));
        
    }
}
