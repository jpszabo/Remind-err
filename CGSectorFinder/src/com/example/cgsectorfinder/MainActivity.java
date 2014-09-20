package com.example.cgsectorfinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clearData(View v) {
		EditText lat = (EditText)findViewById(R.id.editText1);
		EditText lon = (EditText)findViewById(R.id.EditText01);
		TextView sect = (TextView)findViewById(R.id.textView3);
		
		lat.setText("");
		lon.setText("");
		sect.setText("(None)");
	}
	
	public void findSector(View v) {
		EditText lat = (EditText)findViewById(R.id.editText1);
		EditText lon = (EditText)findViewById(R.id.EditText01);
		
		Editable latitude = lat.getText();
		Editable longitude = lon.getText();
		
		String latString = latitude.toString();
		String lonString = longitude.toString();
		
		Double latDouble = Double.valueOf(latString);
		Double lonDouble = -Double.valueOf(lonString);
		
		Geocoder my_Location = new Geocoder(getApplicationContext(), Locale.getDefault());   
        try {
			List<Address> myList = my_Location.getFromLocation(latDouble, lonDouble, 1);
			String theState = "POOP";
			if(myList.isEmpty() != true) {
				theState = myList.get(0).getAdminArea();
	//			String city = myList.get(0).getLocality();
	//			String feature = myList.get(0).getFeatureName();
	//			String premises = myList.get(0).getPremises();
	//			String zip = myList.get(0).getPostalCode();
	//			String county = myList.get(0).getSubAdminArea();
	//			String everything = myList.get(0).toString();	
			}
			String sector = calculateSector(theState, latDouble, lonDouble);
			TextView sect = (TextView)findViewById(R.id.textView3);
			sect.setText(sector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String calculateSector(String state, Double lat, Double lon) {
		
		String sector = "(None)";
		// if a valid state is not returned
		if (state == null) {
			sector = "POOP";
			return sector;
		}
		// if a valid state is returned
		else {
			Point point = new Point(lat, lon);
			
			/*
			 * The following section of code will determine if the point is inside Sector Boston
			 */
			Point c = new Point(42.872222, -70.817222);
			Point d = new Point(42.871667, -67.731389);
			Point e = new Point(42.133333, -67.138056);
			Point f = new Point(42.133333, -70.25);
			Point g = new Point(41.916667, -70.55);
			Point h = new Point(42.066667, -71.1);
			Point i = new Point(42.018889, -71.381389);
			Point j = new Point(42.066667, -71.381389);
			if (state.equals("Massachusetts") && lon < -71.381389) {
				return "Boston";
			}
			if (state.equals("Massachusetts") && lat > 42.066666 && lon < -70.25) {
				return "Boston";
			}
			if (pointInTriangle(point, h, i, j)) {
				return "Boston";
			}
			if (pointInTriangle(point, c, f, h)) {
				return "Boston";
			}
			if (pointInTriangle(point, f, g, h)) {
				return "Boston";
			}
			if (pointInTriangle(point, c, e, f)) {
				return "Boston";
			}
			if (pointInTriangle(point, c, d, e)) {
				return "Boston";
			}
			
			/*
			 * The following section of code will determine if the point is inside Sector Northern New England
			 */
			c = new Point(44.999444, -74.65);
			d = new Point(43.6, -74.65);
			e = new Point(43.550778, -73.250278);
			f = new Point(44.999444, -73.250278);
			g = new Point(44.777936, -71.048584);
			h = new Point(44.825439, -66.932144);
			i = new Point(42.872222, -70.817222);
			j = new Point(42.871667, -67.731389);
			if (state.equals("New Hampshire") || state.equals("Vermont") || state.equals("Maine")) {
				return "Northern New England";
			}
			if (pointInTriangle(point, c, d, e)) {
				return "Northern New England";
			}
			if (pointInTriangle(point, c, e, f)) {
				return "Northern New England";
			}
			if (pointInTriangle(point, g, h, i)) {
				return "Northern New England";
			}
			if (pointInTriangle(point, h, i, j)) {
				return "Northern New England";
			}
			
			/*
			 * The following section of code will determine if the point is inside Sector Northern New England
			 */
			c = new Point(38.4125, -67.690556);
			d = new Point(42.133333, -67.138056);
			e = new Point(42.133333, -70.25);
			f = new Point(41.916667, -70.55);
			g = new Point(42.066667, -71.1);
			h = new Point(41.416667, -71.8);
			i = new Point(41.35, -71.808333);
			j = new Point(41.303889, -71.858333);
			Point k = new Point(42.018798, -71.381402);
			if (pointInTriangle(point, c, d, e)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, c, d, f)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, f, g, k)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, f, k, c)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, c, h, k)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, c, h, i)) {
				return "Southeastern New England";
			}
			if (pointInTriangle(point, i, j, c)) {
				return "Southeastern New England";
			}
			if (lat > 41.416667 && state.equals("Rhode Island")) {
				return "Southeastern New England";
			}
			
			/*
			 * The following section of code will determine if the point is inside Sector Long Island Sound
			 */
			c = new Point(41.303889, -71.858333);
			d = new Point(41.35, -71.808333);
			e = new Point(41.4, -71.8);
			f = new Point(42.008056, -71.799167);
			g = new Point(42.049722, -73.4875);
			h = new Point(41.025, -73.666667);
			i = new Point(40.966667, -73.666667);
			j = new Point(40.875, -73.62);
			k = new Point(40.666667, -73.666667);
			Point l = new Point(40.59, -73.776667);
			Point m = new Point(38.466667, -70.183333);
			Point n = new Point(37.947222, -69.304167);
			Point o = new Point(38.4125, -67.690556);
			if (state.equals("Connecticut")) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, m, n, o)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, m, o)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, l, m)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, k, l)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, j, k)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, i, j)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, h, i)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, c, d, g)) {
				return "Sector Long Island Sound";
			}
			if (pointInTriangle(point, d, e, g)) {
				return "Sector Long Island Sound";
			}
			
			/*
			 * The following section of code will determine if the point is inside Sector New York
			 */
			c = new Point(40.59, -73.776667);
			d = new Point(38.466667, -70.183333);
			e = new Point(40.3, -73.977778);
			f = new Point(40.3, -74.508333);
			g = new Point(41.3575, -74.695);
			h = new Point(42, -74.65);
			i = new Point(42, -75.357778);
			j = new Point(43.6, -74.65);
			k = new Point(43.550833, -73.250278);
			l = new Point(41.025, -73.666667);
			m = new Point(40.875, -73.62);
			n = new Point(40.666667, -73.666667);
			o = new Point(41.426253, -75.574951);
			Point p = new Point(41.253032, -72.987671);
			if (pointInTriangle(point, c, d, e)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, e, f, g)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, c, e, g)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, c, g, n)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, m, n)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, l, m)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, k, l) && state.equals("New York")) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, h, k)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, h, j, k)) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, i, j) && state.equals("New York")) {
				return "Sector New York";
			}
			if (pointInTriangle(point, g, i, o) && state.equals("New York")) {
				return "Sector New York";
			}
			if (pointInTriangle(point, k, l, p) && state.equals("New York")) {
				return "Sector New York";
			}
			
			/*
			 * The following section of code will determine if the point is inside Sector Delaware Bay
			 */
			c = new Point(38.466667, -70.183333);
			d = new Point(37.947222, -69.304167);
			e = new Point(37.320556, -71.048333);
			f = new Point(37.320556, -72.220278);
			g = new Point(38.440278, -74.446111);
			h = new Point(38.450833, -75.048611);
			i = new Point(38.460278, -75.693056);
			j = new Point(39.722778, -75.788056);
			k = new Point(39.722778, -79);
			l = new Point(41, -79);
			m = new Point(41, -78.916111);
			n = new Point(42, -78.916111);
			o = new Point(42, -75.357778);
			p = new Point(41.3575, -74.695);
			Point q = new Point(40.3, -74.508333);
			Point r = new Point(40.3, -73.977778);
			if (state.equals("Pennsylvania") && lon >= -78.916111) {
				return "Sector Delaware Bay";
			}
			if (state.equals("Pennsylvania") && lon >= -79 && lat <= 41) {
				return "Sector Delaware Bay";
			}
			if (state.equals("Delaware")) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, p, q, j)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, q, j, i)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, q, h, i)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, q, r, h)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, h, g, r)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, f, g, r)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, e, f, r)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, e, c, r)) {
				return "Sector Delaware Bay";
			}
			if (pointInTriangle(point, e, d, c)) {
				return "Sector Delaware Bay";
			}
			
			
		}
		return sector;
	}
	
	public Double sign(Point p1, Point p2, Point p3) {
		return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
	}
	
	public boolean pointInTriangle(Point pt, Point v1, Point v2, Point v3) {
		boolean b1, b2, b3;
		
		b1 = sign(pt, v1, v2) < 0.0d;
		b2 = sign(pt, v2, v3) < 0.0d;
		b3 = sign(pt, v3, v1) < 0.0d;
		
		return ((b1 == b2) && (b2 == b3));
	}
	
	private class Point {
		public Double x;
		public Double y;
		
		public Point(double d, double e) {
			this.x = d;
			this.y = e;
		}
	}

}
