package pdm.pkg.andraw;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends Activity interface MessageReceiver {
	
	Connection connection;
	String draw="CANE";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
    connection = new ConnectionManager(getIntent().getExtras().getString("nickname"),nomeavversario, this);
      	
    enum Stato {
    	WAIT_FOR_START,WAIT_FOR_START_ACK
    	}
    
    Timer timer = new Timer();
    TimerTask sendStart = new TimerTask() {
    @Override
    public void run() {
    // TODO Auto-generated method stub
    if (statoCorrente == Stato.WAIT_FOR_START_ACK) {
    connection.send("START");
    } else {
    Log.d(TAG, "Sending START but the state is " + statoCorrente);
    }
    }
    };
    
    if(nomeavversario.hashCode()<getIntent().getExtras().getString("nickname").hashCode()){
    	// Inizio io
    	timer.schedule(sendStart, 1000, 5000);
    	statoCorrente=Stato.WAIT_FOR_START_ACK;
    	} else{
    	// Inizia lui
    	//Io aspetto il pacchetto;
    	statoCorrente=Stato.WAIT_FOR_START;
    	}
    
    final Handler handler = new Handler() {
    	@Override
    	public void handleMessage(android.os.Message msg) {
    	switch (msg.what) {
    	case Second.SHOW_TOAST:
    	Toast.makeText(Second.this,msg.getData().getString("toast"),
    	Toast.LENGTH_LONG).show();
    	break;
    	default:
    	super.handleMessage(msg);
    	}
    	}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void flush() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void publish(LogRecord record) {
			// TODO Auto-generated method stub
			
		}
    	};
    	
    	if (body.equals("START")) {
    		if (statoCorrente == Stato.WAIT_FOR_START) {
    		// Mando l'ack indietro
    		connection.send("STARTACK");
    		Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
    		Bundle b = new Bundle();
    		b.putString("toast", "Disegna");
    		osmsg.setData(b);
    		handler.sendMessage(osmsg);
    		statoCorrente=Stato.USER_SELECTING;
    		} else {
    		Log.e(TAG, "Ricevuto START ma lo stato è " + statoCorrente);
    		}
    		}


   


	final EditText edt = (EditText) findViewById(R.id.editText1);
    final TextView tv = (TextView) findViewById(R.id.textView1);      
    Button btn = (Button) findViewById(R.id.button1);
    
    //FASE DI DISEGNO
    
   
    tv.setMovementMethod(new ScrollingMovementMethod());
    
    btn.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		
    		tv.append("ME: "+edt.getText().toString()+"\n");
    		Message msg = new Message();
    		msg.setTo("loreti@ppl.eln.uniroma2.it");
    		msg.setBody(edt.getText().toString());
    		((XMPPConnection) connection).sendPacket(msg);
    		if(msg.getBody()==draw){
    			Toast.makeText(getApplicationContext(), "INDOVINATO: "+draw, Toast.LENGTH_LONG).show();
    		}
    	}
    });
    
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
