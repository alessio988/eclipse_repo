package pdm.pkg.indovinailnumero;

import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Main extends MessageReceiver {
	
	Connection connection;
	String nome_proprio;
	String nome_avversario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        nome_proprio=getIntent().getExtras().getString("nome proprio");
        nome_avversario=getIntent().getExtras().getString("nome avversario");
        connection = new ConnectionManager(nome_proprio, nome_avversario, this);
        
        //enum che contiene gli stati
        enum Stato {
        	WAIT_FOR_START,WAIT_FOR_START_ACK
        	}
        
        //definire un timer
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



    }

}

//sono arrivato al punto 7