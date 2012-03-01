package pdm.pkg.filemap;

import java.sql.Connection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FileMapActivity extends MapActivity {
    /** Called when the activity is first created. */
	
	//variabili di istanza
	MyLocationOverlay myLocationOverlay;
	MapView mapView;
	RadiusOverlay client;
	PendingIntent pending1;
	
	//variabile per connessione XMPP
	Connection connection;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Prendere il riferimento alla mapview e configurarla
        mapView = (MapView) findViewById(R.id.MapView);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
                
        //Visualizzare posizione sulla mappa con l'overlay
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        
        //animare posizione
        myLocationOverlay.runOnFirstFix(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		myLocationOverlay.disableCompass();
	}
}