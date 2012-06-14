package pdm.pkg.mypicture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ClientGame extends Activity {

	MyClient client;
	DrawView drawView;
	EditText answer;
	ImageView send;
	String ipserver;

	final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// Grafica
				Toast.makeText(ClientGame.this, "INDOVINATO ",
						Toast.LENGTH_LONG).show();
				break;
			case 1:
				// Grafica
				Toast.makeText(ClientGame.this, "SBAGLIATO ", Toast.LENGTH_LONG)
						.show();
				break;

			case 2:
				// Grafica
				String rec = msg.getData().getString("NP");
				String[] data = rec.split("##", rec.length());
				drawView.setNewPoint(Float.parseFloat(data[1]),
						Float.parseFloat(data[2]));
				break;
			default:
				break;
			}

		};
	};

	public class MyClient extends Thread {

		private Socket connessione;
		private PrintWriter pw;
		int i = 0;

		public void send(String msg) {
			pw.println(msg);
			pw.flush();
		}

		@Override
		public void run() {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					System.in));

			try {
				connessione = new Socket(ipserver, 9999);
				bf = new BufferedReader(new InputStreamReader(
						connessione.getInputStream()));
				pw = new PrintWriter(connessione.getOutputStream());
				Log.d("connessione", "stabilita");
			} catch (IOException e) {
				e.printStackTrace();
				stop();
			}

			while (true) {

				try {

					String rev = bf.readLine();
					String comp[] = rev.split("##", rev.length());

					if (comp[0].equals("1")) {

						Message msg = handler.obtainMessage(2);
						Bundle b = new Bundle();
						b.putString("NP", rev);
						msg.setData(b);
						handler.sendMessage(msg);

					}

					if (comp[0].equals("0")) {

						if (comp[1].equals("indovinato")) {

							Message m = handler.obtainMessage(0);
							Bundle b = new Bundle();
							b.putString("RCV", rev);
							m.setData(b);
							handler.sendMessage(m);
							break;

						}

						Message m = handler.obtainMessage(1);
						Bundle b = new Bundle();
						b.putString("RCV", rev);
						m.setData(b);
						handler.sendMessage(m);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				connessione.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Intent intent = new Intent(ClientGame.this, MyPictureActivity.class);
			startActivity(intent);

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);

		drawView = (DrawView) findViewById(R.id.draw1);
		answer = (EditText) findViewById(R.id.editText1);
		send = (ImageView) findViewById(R.id.imageView1);

		ipserver = getIntent().getExtras().getString("IPServer");

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Bundle b = new Bundle();
				String s = answer.getText().toString();
				client.send(s);
			}
		});

		client = new MyClient();
		client.start();

	}
}
