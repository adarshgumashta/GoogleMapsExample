package com.googlemapsexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //OUR VARIABLES
    LocationManager locationManager;
    LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(locationListener);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(MapsActivity.this,"Location:"+latitude,Toast.LENGTH_SHORT).show();
            Toast.makeText(MapsActivity.this,"Longitude:"+longitude,Toast.LENGTH_SHORT).show();

            Geocoder geocoder=new Geocoder(MapsActivity.this);

            try {
                List<Address> addressList=geocoder.getFromLocation(latitude,longitude,10);
                Address first=addressList.get(0);
                String streetAddress=first.getAddressLine(0);
                String locality=first.getLocality();
                String zip=first.getPostalCode();
                String country=first.getCountryName();
                StringBuilder sb=new StringBuilder();
                sb.append(streetAddress+" - "+locality+" - "+country+" - "+zip);
                Toast.makeText(MapsActivity.this,sb,Toast.LENGTH_SHORT).show();



                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("lp"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(13));


                String destination_address="brillio technologies,58th main,jp nagar 3rd phase ,bangalore 560078";
                List<Address> destAddresses=geocoder.getFromLocationName(destination_address,10);
                Address destFirst=destAddresses.get(0);
                double latitude1=destFirst.getLatitude();
                double longitude1=destFirst.getLongitude();

                LatLng office=new LatLng(latitude1,longitude1);
                mMap.addMarker(new MarkerOptions().position(office).title("Office"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(office));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(13));

                mMap.addPolyline(new PolylineOptions().add(sydney).add(office).color(Color.BLUE));
                mMap.addCircle(new CircleOptions().center(sydney).radius(14));
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                //let us find distance between two points
                Location dest_location=new Location("abc");
                dest_location.setLatitude(latitude1);
                dest_location.setLongitude(longitude1);

                int distance= (int) location.distanceTo(dest_location);

                Toast.makeText(MapsActivity.this, "distance = "+distance/1000, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
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
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "permission denied..try again", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,locationListener);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
