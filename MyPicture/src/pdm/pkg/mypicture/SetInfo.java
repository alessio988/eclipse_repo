package pdm.pkg.mypicture;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetInfo extends Activity {

	TextView tv;
	EditText et;
	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String rev = getIntent().getExtras().getString("ext");
		if (rev.equals("server")) {
			setContentView(R.layout.infos);

			tv = (TextView) findViewById(R.id.textView2);
			et = (EditText) findViewById(R.id.editText10);
			btn = (Button) findViewById(R.id.button1);

			tv.setText("IP: "+getLocalIpAddress());

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SetInfo.this, ServerGame.class);
					intent.putExtra("KEY", et.getText().toString());
					startActivity(intent);
				}
			});

		} else {
			setContentView(R.layout.infoc);
			et = (EditText) findViewById(R.id.editText9);
			btn = (Button) findViewById(R.id.button5);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SetInfo.this, ClientGame.class);
					intent.putExtra("IPServer", et.getText().toString());
					startActivity(intent);
				}
			});

		}

	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("LOG-TAG", ex.toString());
		}
		return null;
	}

}
