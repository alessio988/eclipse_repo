package pdm.pkg.provaserverandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


import android.os.Environment;


public class PTTRESActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("via");
        setContentView(tv);
             
        Server server = new Server();
        server.setActivity(this);
        Thread t1 = new Thread (server);
        t1.start();
        /**setContentView(R.lay;out.main);**/
    }
   

    public class Server implements Runnable {
 
       
        private File sd = Environment.getExternalStorageDirectory();
            private File f = new File(sd, "nome_file.txt");
            private int i=0;
            private Activity activity = null;
            private ServerSocket server;
                private Socket connessione;
                private BufferedReader dalClient;
                private PrintStream alClient;
                private String name;

           
            public void setActivity(Activity activity) {
                this.activity = activity;
            }
           
            @Override
            public void run() {
                    byte[] buf = new byte[17];
                    String st = null;
                                       
                try {
                        InetAddress serverAddr = InetAddress.getByName("160.80.154.249");
                        st = serverAddr.toString();
                        server = new ServerSocket(9999,5,serverAddr);
                        write(server.getInetAddress().toString());
                                //System.out.println("Server attivo");
                                connessione = server.accept();
                                dalClient = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
                                alClient = new PrintStream(connessione.getOutputStream());
                                String messaggio = "";
                                BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
                                alClient.println("Simple Hack Chat - Sei connesso al server! Digita /logout");
                while ((!messaggio.equals("/logout")) ) {
                try {
                        messaggio = dalClient.readLine();
                                write(messaggio);
                                if(!messaggio.equals("/logout")) {
                                        messaggio = t.readLine();
                                        alClient.println(name+" scrive: "+messaggio);
                                }

                  } catch (IOException e) {
                    i++;    
                    write("IO!" + i);  
                  }
                }
                } catch (Exception e) {
                         write(e.toString() + "-" + st);};
              }
             
           
             

            public void write (String Data){
            try{
                TextView tv = new TextView(this.activity);
                tv.setText(tv.getText()+ " - "+Data);
                setContentView(tv);
                                   }
                catch (Exception e) {      
                e.printStackTrace();
                }
            }
    }
}
   