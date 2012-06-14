package pdm.pkg.tcpserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TCPServerActivity extends Activity {
    /** Called when the activity is first created. */
	TextView txt1;
	private void runTcpServer() {

	    ServerSocket ss = null;
	    int TCP_SERVER_PORT=5050;

	    try {

	        ss = new ServerSocket(TCP_SERVER_PORT);

	        //ss.setSoTimeout(10000);

	        //accept connections

	        Socket s = ss.accept();

	        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

	        //receive a message

	        String incomingMsg = in.readLine() + System.getProperty("line.separator");

	        Log.i("TcpServer", "received: " + incomingMsg);

	        txt1.append("received: " + incomingMsg);

	        //send a message

	        String outgoingMsg = "goodbye from port " + TCP_SERVER_PORT + System.getProperty("line.separator");

	        out.write(outgoingMsg);

	        out.flush();

	        Log.i("TcpServer", "sent: " + outgoingMsg);

	        txt1.append("sent: " + outgoingMsg);

	        //SystemClock.sleep(5000);

	        s.close();

	    } catch (InterruptedIOException e) {

	        //if timeout occurs

	        e.printStackTrace();

	    } catch (IOException e) {

	        e.printStackTrace();

	    } finally {

	        if (ss != null) {

	            try {

	                ss.close();

	            } catch (IOException e) {

	                e.printStackTrace();

	            }

	        }

	    }

	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txt1 = (TextView) findViewById(R.id.textView1);
        runTcpServer();
    }
}