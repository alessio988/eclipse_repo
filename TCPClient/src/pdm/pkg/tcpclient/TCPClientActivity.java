package pdm.pkg.tcpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TCPClientActivity extends Activity {
    /** Called when the activity is first created. */
	private void runTcpClient() {
		
		int TCP_SERVER_PORT=5050;

	    try {

	        Socket s = new Socket("192.168.0.255", TCP_SERVER_PORT);

	        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

	        //send output msg

	        String outMsg = "TCP connecting to " + TCP_SERVER_PORT + System.getProperty("line.separator"); 

	        out.write(outMsg);

	        out.flush();

	        Log.i("TcpClient", "sent: " + outMsg);

	        //accept server response

	        String inMsg = in.readLine() + System.getProperty("line.separator");
	        
	        textView.append(inMsg);

	        Log.i("TcpClient", "received: " + inMsg);

	        //close connection

	        s.close();

	    } catch (UnknownHostException e) {

	        e.printStackTrace();

	    } catch (IOException e) {

	        e.printStackTrace();

	    } 

	}
	TextView textView;
	EditText editText;
	Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView=(TextView)findViewById(R.id.textView1);
        editText=(EditText)findViewById(R.id.editText1);
        button=(Button)findViewById(R.id.button1);
        runTcpClient();
    }
}