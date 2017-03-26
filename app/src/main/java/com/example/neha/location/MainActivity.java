package com.example.neha.location;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Geocoder geocoder;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // geocoder = new Geocoder(this);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria();
    }

    private void setInfo(String info) {

        ((TextView)findViewById(R.id.BtnOkay)).setText(info);
    }


    //findViewById(R.id.BtnOkay).setOnClickListener(v -> forwardGeocoding(((EditText) findViewById(R.id.Location)).getText().toString()));

    // findViewById(R.id.btnGetLoc).setOnClickListener(v -> reverseGeocoding(getLat(), getLng()));

    private void listenForLocations(){


        manager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER), 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setInfo("Lat -"+location.getLatitude()+"Lng - "+location.getLongitude());

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
    }



    private void allProviders(){
        List<String> providers = manager.getAllProviders();
        StringBuilder builder = new StringBuilder();
        for (String provider : providers) {
            builder.append("\n Provider - ").append(provider);
        }

        setInfo(builder.toString());

    }


    private void criteria(){
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

       setInfo("Best Provider is - "+manager.getBestProvider(criteria,false));
    }

    private void forwardGeocoding(String add) {

        try {

            List<Address> addresses = geocoder.getFromLocationName(add, 5);
            StringBuilder builder = new StringBuilder();
            for (Address address : addresses) {

                builder.append("\n country").append(address.getCountryName())
                        .append("\n cc").append(address.getCountryCode())
                        .append("\n aa").append(address.getAdminArea())
                        .append("\n Lat").append(address.getLatitude())
                        .append("\n Lon").append(address.getLongitude());
            }

            ((TextView) findViewById(R.id.Location)).setText(builder.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reverseGeocoding(double lat, double lng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
            StringBuilder builder = new StringBuilder();
            for (Address address : addresses) {

                builder.append("\n country").append(address.getCountryName())
                        .append("\n cc").append(address.getCountryCode())
                        .append("\n aa").append(address.getAdminArea())
                        .append("\n Address Line-1").append(address.getAddressLine(0))
                        .append("\n --------");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private double getLat() {
        return Double.parseDouble(((EditText)findViewById(R.id.edtLat)).getText().toString());
    }

    private double getLng() {
        return Double.parseDouble(((EditText)findViewById(R.id.edtLng)).getText().toString());
    }

}
