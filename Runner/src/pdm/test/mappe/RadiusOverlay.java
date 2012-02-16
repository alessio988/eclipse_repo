package pdm.test.mappe;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RadiusOverlay extends Overlay {

	private GeoPoint mPoint;
	private int mRadiusInMeters;
	private Paint mPaint1, mPaint2;	

	/**
	 * Costruttore per la classe RadiusOverlay. Rappresenta un area circolare di raggio e colore specificato 
	 * @param point  {@link GeoPoint} che rappresenta il centro dell'area
	 * @param radiusInMeters il raggio in metri
	 * @param color il colore del raggio, usare le costanti della classe {@link android.graphics.Color}
	 */
	public RadiusOverlay(GeoPoint point, int radiusInMeters, int color) {
		this.mPoint = point;
		this.mRadiusInMeters = radiusInMeters;
		
		mPaint1 = new Paint();
		mPaint1.setColor(color);
		mPaint1.setAlpha(128);
		
		mPaint1.setStrokeWidth(2);
		mPaint1.setStrokeCap(Paint.Cap.ROUND);
		mPaint1.setAntiAlias(true);
		mPaint1.setDither(false);
		mPaint1.setStyle(Paint.Style.STROKE);

		mPaint2 = new Paint();
		mPaint2.setColor(color);
		mPaint2.setAlpha(64);

	}
	
	/**
	 * Imposta il colore dell'area
	 * @param color il colore del cerchio, usare le costanti della classe {@link android.graphics.Color}
	 */
	public void setColor(int color){
		mPaint1.setColor(color);
		mPaint2.setAlpha(128);
		mPaint2.setColor(color);
		mPaint2.setAlpha(64);
	}
	
	/**
	 * Ritorna il centro dell'area
	 * @return un {@link GeoPoint} che rappresenta il centro dell'area
	 */
	public GeoPoint getCenter(){
		return mPoint;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		Projection projection = mapView.getProjection();
		Point center = projection.toPixels(mPoint, null);
		float radius = projection.metersToEquatorPixels(mRadiusInMeters);

		if (radius < canvas.getHeight() / 40) {
			radius = canvas.getHeight() / 40;
		}

		canvas.drawCircle(center.x, center.y, radius, mPaint2);
		canvas.drawCircle(center.x, center.y, radius, mPaint1);

	}

}