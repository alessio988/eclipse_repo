package pdm.pkg.androidserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidClient extends Activity {

EditText textOut;
TextView textIn;

 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.main);
  
     textOut = (EditText)findViewById(R.id.textout);
     Button buttonSend = (Button)findViewById(R.id.send);
     textIn = (TextView)findViewById(R.id.textin);
     buttonSend.setOnClickListener(buttonSendOnClickListener);
 }

 Button.OnClickListener buttonSendOnClickListener = new Button.OnClickListener(){

@Override
public void onClick(View arg0) {
 // TODO Auto-generated method stub
 Socket socket = null;
 DataOutputStream dataOutputStream = null;
 DataInputStream dataInputStream = null;

 try {
  socket = new Socket("localhost", 2004);
  dataOutputStream = new DataOutputStream(socket.getOutputStream());
  dataOutputStream.flush();
  dataInputStream = new DataInputStream(socket.getInputStream());
  dataOutputStream.writeUTF(textOut.getText().toString());
  textIn.setText(dataInputStream.readUTF());
 } catch (UnknownHostException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 } catch (IOException e) {
  // TODO Auto-generated catch block
	 Toast.makeText(getApplicationContext(), "socket connection failed" , Toast.LENGTH_LONG).show();
	 e.printStackTrace();
 }
 finally{
	 if (socket != null){
		 try {
			 socket.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
		 }
	 if (dataOutputStream != null){
		 try {
			 dataOutputStream.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
		 }
	 if (dataInputStream != null){
		 try {
			 dataInputStream.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
		 }
	 }
 }};
 
 //Get my localhost
 /*
 public String getLocalIpAddress() {
	 try {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
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
*/

}