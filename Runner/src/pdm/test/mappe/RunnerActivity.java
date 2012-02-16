package pdm.test.mappe;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

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

public class RunnerActivity extends MapActivity {
    /** Called when the activity is first created. */
	
	//variabili di istanza
	MapView mapView;
	MyLocationOverlay myLocationOverlay;
	RadiusOverlay overlayTermini;
	RadiusOverlay overlayRepubblica;
	RadiusOverlay overlayColosseo;
	RadiusOverlay overlayRemo;
	LocationManager locationManager;
	PendingIntent mPeddingTermini;
	ProximityBroadcast mProximityBroadcast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //prendo il riferimento ed implemento il pan, lo zoom e la vista satellitare
        mapView = (MapView) findViewById (R.id.MapView);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        
        //visualizza la posizione del terminale
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        
        myLocationOverlay.runOnFirstFix(new Runnable(){
        	public void run(){
        		mapView.getController().animateTo(myLocationOverlay.getMyLocation());
        	}
        });
        
        //inizializzazione overlay
        GeoPoint termini = new GeoPoint(41902022,12500882);
        overlayTermini = new RadiusOverlay(termini, 400, Color.BLUE);
        mapView.getOverlays().add(overlayTermini);
        GeoPoint repubblica = new GeoPoint(41902622,12495482);
        overlayRepubblica = new RadiusOverlay(repubblica, 300, Color.GREEN);
        mapView.getOverlays().add(overlayRepubblica);
        GeoPoint colosseo = new GeoPoint(41890310,12492410);
        overlayColosseo = new RadiusOverlay(colosseo, 500, Color.RED);
        mapView.getOverlays().add(overlayColosseo);
        GeoPoint remo = new GeoPoint(41890492,12484823);
        overlayRemo = new RadiusOverlay(remo, 450, Color.YELLOW);
        mapView.getOverlays().add(overlayRemo);
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.addProximityAlert(termini.getLatitudeE6()*0.000001, termini.getLongitudeE6()*0.000001, 400, -1, mPeddingTermini);
    }
    
    class ProximityBroadcast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("TAG","Proximity allert");
			Toast.makeText(getApplicationContext(),	"Alert di prossimità", Toast.LENGTH_LONG).show();
		}
    	
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
		Log.d("LOG","ENABLE LOCATION");
		Intent intentTermini = new Intent("pdm.test.mappe");
		intentTermini.putExtra("overlay", 1);
		mPeddingTermini = PendingIntent.getBroadcast(this, 1, intentTermini, PendingIntent.FLAG_CANCEL_CURRENT);
		registerReceiver(mProximityBroadcast, new IntentFilter("pdm.test.mappe"));
	}
	
	@Override
	public void onPause(){
		super.onPause();
		myLocationOverlay.disableMyLocation();
		Log.d("LOG","DISABLE LOCATION");
		unregisterReceiver(mProximityBroadcast);
		locationManager.removeProximityAlert(mPeddingTermini);
	}
}