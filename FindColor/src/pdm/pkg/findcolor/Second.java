package pdm.pkg.findcolor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Second extends Activity {
	
	Camera camera;
	Preview preview;
	Button buttonClick;	
	int color;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		//Scelta random del colore
		Random rnd = new Random();
	    color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	    Toast.makeText(getApplicationContext(), color , Toast.LENGTH_LONG).show();
	    
	    //Immagine della fotocamera
		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		buttonClick = (Button) findViewById (R.id.buttonClick);
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				preview.camera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
				//Caricamento immagine su server per confronto
				//Schermata vincitore
			}
		});

		Log.d("Click", "onCreate'd");
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d("Click", "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("Click", "onPictureTaken - raw");
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {
				// write to local sandbox file system
				
				/*outStream =
				 CameraDemo.this.openFileOutput(String.format("%d.jpg",
				 System.currentTimeMillis()), 0);*/
				
				// Or write to sdcard
				outStream = new FileOutputStream(String.format(
						"/sdcard/%d.jpg", System.currentTimeMillis()));
				outStream.write(data);
				outStream.close();
				Log.d("Click", "onPictureTaken - wrote bytes: " + data.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d("Click", "onPictureTaken - jpeg");
		}
	};

}