package pdm.pkg.project1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	//Variabili******************************************************************************
	ServerSocket providerSocket;
	Socket requestSocket = null;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message,secret;
	
	//Funzioni*******************************************************************************
		void sendMessage(String msg)
		{
			try{
				out.writeObject(msg);
				out.flush();
				Log.d("MESSAGE SEND",msg);
				
			}
			catch(IOException ioException){
				Log.d("ERRORE","sendMessage");
				ioException.printStackTrace();
			}
		}
		
		public void runServer(){
			try{
			
		    connection = providerSocket.accept();
			providerSocket.setSoTimeout(10000);
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			message = (String)in.readObject();
			
			}catch(IOException e){
				Log.d("ERRORE connessione","SERVER");
				closeSC();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Log.d("ERRORE message","SERVER");
				closeSC();
				e.printStackTrace();
			}
		}
		
		public void closeSC(){
			try{
			in.close();
			out.close();
			providerSocket.close();
			}catch(IOException e){
				Log.d("ERRORE chiusura", "SERVER/CLIENT");
			}		
		}
		
    //OnCreate******************************************************************************
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
        //************************ SERVER *************************************************
        if(getIntent().getExtras().getBoolean("SERVER")==true){
        	Boolean b = getIntent().getExtras().getBoolean("SERVER");
        	secret="pippo";
        	//activity per disegnare
        	setContentView(new SingleTouchEventView(this, null));
        	try{
        		providerSocket = new ServerSocket(9999,10);
        		while(true){
        		runServer();
					//caso di vittoria
					if(message==secret){
						Toast.makeText(getApplicationContext(), "INDOVINATO" , Toast.LENGTH_LONG).show();
						sendMessage("INDOVINATO");
						Log.d("INDOVINATO",secret);
						closeSC();
						Intent intent = new Intent(GameActivity.this,Project1Activity.class);
						startActivity(intent);
					}else{
						//qualsiasi altro caso
						Toast.makeText(getApplicationContext(), "SBAGLIATO" , Toast.LENGTH_LONG).show();
						Log.d("SBAGLIATO",message);
						sendMessage("SBAGLIATO");
					}
							
				}
        	}catch(IOException ioException){
        		Log.d("ERRORE socket","SERVER");
        		closeSC();
    			ioException.printStackTrace();
    		}finally{
    			closeSC();
				Intent intent = new Intent(GameActivity.this,Project1Activity.class);
				startActivity(intent);
    		}
        	
        }
        
        //******************************* CLIENT ******************************************
        if(getIntent().getExtras().getBoolean("CLIENT")==true){
        	 setContentView(R.layout.game_activity);
        	 final EditText edt = (EditText) findViewById(R.id.editText1);
             //ImageView im = (ImageView) findViewById(R.id.imageView1);
             Button btn = (Button) findViewById(R.id.button1);
        	try {
				requestSocket = new Socket(getIntent().getExtras().getString("IPPLAYER2"),9999);
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(requestSocket.getInputStream());
				
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						message = edt.getText().toString();
						sendMessage(message);
					}
				});
				
				try{
					message = (String)in.readObject();
					if(message=="INDOVINATO"){
						Toast.makeText(getApplicationContext(), "INDOVINATO" , Toast.LENGTH_LONG).show();
						in.close();
						out.close();
						providerSocket.close();
						Intent intent = new Intent(GameActivity.this,Project1Activity.class);
						startActivity(intent);
					}else{
					
						Toast.makeText(getApplicationContext(), "SBAGLIATO" , Toast.LENGTH_LONG).show();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					Log.d("ERRORE message","CLIENT");
					e.printStackTrace();
				}
				finally{
					in.close();
					out.close();
				    providerSocket.close();
				    Intent intent = new Intent(GameActivity.this,Project1Activity.class);
					startActivity(intent);
				}
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				Log.d("ERRORE socket","CLIENT");
				closeSC();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("ERRORE socket","CLIENT");
				closeSC();
				e.printStackTrace();
			}
        }
      
	}
	
}
