package pdm.pkg.mypicture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ServerGame extends Activity {
	 
	MyServer server;
	String key;
	
	final Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 0:
				//Grafica
				Toast.makeText(ServerGame.this, "SBAGLIATO", Toast.LENGTH_LONG).show();
				break;
			case 1:
				//Grafica
				Toast.makeText(ServerGame.this, "INDOVINATO", Toast.LENGTH_LONG).show();
				break;
				
			case 2:
				//Grafica
				Toast.makeText(ServerGame.this, "CONNESSIONE STABILITA", Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
    		
    		
    	};
    };
	
	public class MyServer extends Thread {
		
		private ServerSocket ss;
		private Socket connessione;
		private BufferedReader fb;
		private PrintWriter pw;
		
                
		public void send(String msg) {
    		pw.println(msg);
    		pw.flush();
		}
		
		
		@Override
		public void run() {
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
			try {
				
				ss = new ServerSocket(9999);
				connessione = ss.accept();
				
				bf = new BufferedReader(new InputStreamReader(
						connessione.getInputStream()));
				pw = new PrintWriter(connessione.getOutputStream());
				
				Message m3 = handler.obtainMessage(2);
				handler.sendMessage(m3);
				
				while(true){
					
					String rev = bf.readLine();
					
                        if(rev.equals(key)){
						
						server.send("0##indovinato");
						Message m2 = handler.obtainMessage(1);
						Bundle b2 =  new Bundle();
						b2.putString("RCV", rev);
						m2.setData(b2);
						handler.sendMessage(m2);
						break;
						
					}
                        
                    server.send("0##sbagliato");
					Message m = handler.obtainMessage(0);
					Bundle b =  new Bundle();
					b.putString("RCV", rev);
					m.setData(b);
					handler.sendMessage(m);
					
					
					
				}
				
				connessione.close();
				Intent intent = new Intent(ServerGame.this,MyPictureActivity.class);
				startActivity(intent);
				
			}
			catch (Exception e) {
					e.printStackTrace();
				}
		

			}

	}
	
	public class SingleTouchEventView extends View {
		
		private Paint paint = new Paint();
		private Path path = new Path();

		public SingleTouchEventView(Context context, AttributeSet attrs) {
			super(context, attrs);

			paint.setAntiAlias(true);
			paint.setStrokeWidth(6f);
			paint.setColor(Color.WHITE);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			
			server.send("1##"+eventX+"##"+eventY);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(eventX, eventY);
				return true;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(eventX, eventY);
				break;
			case MotionEvent.ACTION_UP:
				// nothing to do
				break;
			default:
				return false;
			}

			// Schedules a repaint.
			invalidate();
			return true;
		}}
	
	@Override
 	public void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(new SingleTouchEventView(this, null));
 		
 		key=getIntent().getExtras().getString("KEY");
 		server = new MyServer();
 		server.start();

 	}
	
}
