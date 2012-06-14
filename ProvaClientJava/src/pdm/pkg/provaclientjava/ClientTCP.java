package pdm.pkg.provaclientjava;

import java.net.*;
import java.io.*;



public class ClientTCP {
        private Socket connessione;
        private BufferedReader dalServer;
        private PrintStream alServer;
        private String name;
       
        public static void main(String[] args) {
                ClientTCP c = new ClientTCP("alessio");
                c.conversazione();

        }
        public ClientTCP(String name) {
                this.name = name;
                BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
                try {
                         
                        System.out.println("Inserire indirizzo server: ");
                        //String indirizzo = t.readLine();
                        connessione = new Socket("160.80.159.153", 9999);
                        dalServer = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
                        alServer = new PrintStream(connessione.getOutputStream());
                }
                catch(IOException e) {
                        System.out.println(e);
                }
        } // Client()
 
        public void conversazione() {
                String messaggio = "";
                BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
                try {
                        while(!messaggio.equals("/logout")) {
                                messaggio = dalServer.readLine();
                                System.out.println(messaggio);
                                if(!messaggio.equals("/logout")) {
                                        messaggio = t.readLine();
                                        alServer.println(name+" scrive: "+messaggio);
                                }
                        } // while
                        connessione.close();
                }
                catch(IOException e) {
                        System.out.println("Conversazione interrotta");
                }
        } // conversazione()
}