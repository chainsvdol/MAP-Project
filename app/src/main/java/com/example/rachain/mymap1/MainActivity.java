package com.example.rachain.mymap1;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
///XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.textservice.TextInfo;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
public class MainActivity extends AppCompatActivity {

   //Explicit
    private TextView showLatTextView, showLongTextView;
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean GPSABoolean, networkABoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidget();

        openServiceGetLocation();

    }// Main onCreate

    public void findGps(View view) {

        String strLat = showLatTextView.getText().toString();
        String strLng = showLongTextView.getText().toString();
        Intent objIntent = new Intent(MainActivity.this, MapsActivity.class);
        // Sent lat,Lng to MapActivity
        objIntent.putExtra("Lat", strLat);
        objIntent.putExtra("Lng", strLng);
        //Toast.makeText(MainActivity.this,strLat,Toast.LENGTH_LONG).show();
        startActivity(objIntent);
    }

    public void findGps52(View view){

        String strLat = showLatTextView.getText().toString();
        String strLng = showLongTextView.getText().toString();
        Intent objIntent = new Intent(MainActivity.this, MapsActivity.class);
        // Sent lat,Lng to MapActivity
        objIntent.putExtra("Lat", strLat);
        objIntent.putExtra("Lng", strLng);
        //Toast.makeText(MainActivity.this,strLat,Toast.LENGTH_LONG).show();
        startActivity(objIntent);
    }

    public void findChain (View view){
      Intent objIntent = new Intent(Intent.ACTION_VIEW);

        objIntent.setData(Uri.parse("http://www.dol-maptech.com/"));
        startActivity(objIntent);

    }


    /// xxxxxxx
    private void bindWidget() {

        showLatTextView = (TextView) findViewById(R.id.txtShowLat);
        showLongTextView = (TextView) findViewById(R.id.txtShowLong);
    }
    @Override
    protected void onStart() {
        super.onStart();

        GPSABoolean = objLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSABoolean) {

            // No GPS
            networkABoolean = objLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!networkABoolean) {
                // No Net

                Toast.makeText(MainActivity.this, "Stant Alone", Toast.LENGTH_SHORT).show();

            }//if2
        } // if1


    }  // onStart

    @Override
    protected void onResume() {
        super.onResume();
        setupForRestart();

    }

    private void setupForRestart() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        objLocationManager.removeUpdates(objLocationListener);
        String strLat = "กำลังรอสัณญาณดาวเทียม";
        String strLng = "กำลังรอสัณญาณดาวเทียม";

        Location networkLocation = requestLocation(LocationManager.NETWORK_PROVIDER, "Internet Not Connected");

        if (networkLocation != null) {
            strLat = String.format("%.7f", networkLocation.getLatitude());
            strLng = String.format("%.7f", networkLocation.getLongitude());

        } // if

        Location GPSLocation = requestLocation(LocationManager.GPS_PROVIDER, "GPS false");
        if (GPSLocation != null) {

            strLat = String.format("%.7f", GPSLocation.getLatitude());
            strLng = String.format("%.7f", GPSLocation.getLongitude());


        } // if
        showLatTextView.setText(strLat);
        showLongTextView.setText(strLng);

    } // SetupForRestart

    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        objLocationManager.removeUpdates(objLocationListener);

    }

    public Location requestLocation(String strProvider, String strError) {

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strProvider)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {
            Toast.makeText(MainActivity.this, strError, Toast.LENGTH_SHORT).show();

        }

        return objLocation;
    }

    // Create Class for Find Location

    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLatTextView.setText(String.format("%.7f", location.getLatitude()));
            showLongTextView.setText(String.format("%.7f", location.getLongitude()));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }; // End of Class


    private void openServiceGetLocation() {
        objLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);

    }  // OpenServiceGetLocation

} //  Main Class
