package com.example.ultimetable;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            getLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    private void getLocation() throws IOException {

        final LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        updateToNewLocation(location);

        locationManager.requestLocationUpdates(provider, 10 * 1000, 500, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    updateToNewLocation(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @SuppressLint("MissingPermission")
            @Override
            public void onProviderEnabled(String provider) {
                try {
                    updateToNewLocation(locationManager.getLastKnownLocation(provider));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
                try {
                    updateToNewLocation(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateToNewLocation(Location location) throws IOException {
        LatLng room = null;
        //
        Intent intent=getIntent();
        String loc=intent.getStringExtra("Room");
        assert loc != null;
        if(loc.startsWith("SG")||loc.startsWith("S1")||loc.startsWith("S2")){
            room = new LatLng(52.673277, -8.577844);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("KBG")||loc.startsWith("KB1")||loc.startsWith("KB2")||loc.startsWith("KB3")){
            //room = new LatLng(52.672626, -8.576755);
            room = new LatLng(37.511336, -122.259419);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("CSG")||loc.startsWith("CS1")||loc.startsWith("CS2")||loc.startsWith("CS3")){
            room = new LatLng(52.674012, -8.575569);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("GLG")||loc.startsWith("GL0")||loc.startsWith("GL1")||loc.startsWith("GL2")){
            room = new LatLng(52.673381, -8.573488);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("FB")||loc.startsWith("FG")||loc.startsWith("F1")||loc.startsWith("F2")){
            room = new LatLng(52.674549, -8.573740);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("ERB")||loc.startsWith("ER0")||loc.startsWith("ER1")||loc.startsWith("ER2")){
            room = new LatLng(52.675041, -8.572736);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("LCB")||loc.startsWith("LC0")||loc.startsWith("LC1")||loc.startsWith("LC2")){
            room = new LatLng(52.675500, -8.573420);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("LB")||loc.startsWith("LG")||loc.startsWith("L1")||loc.startsWith("L2")){
            room = new LatLng(52.673896, -8.568845);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("SR1")||loc.startsWith("SR2")||loc.startsWith("SR3")){
            room = new LatLng(52.673841, -8.567418);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("PG")||loc.startsWith("PM")||loc.startsWith("P1")||loc.startsWith("P2")){
            room = new LatLng(52.674656, -8.567627);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("HSG")||loc.startsWith("HS1")||loc.startsWith("HS2")||loc.startsWith("HS3")){
            room = new LatLng(52.677815, -8.568936);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("AD0")||loc.startsWith("AD1")||loc.startsWith("AD2")||loc.startsWith("AD3")){
            room = new LatLng(52.673333, -8.568707);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("IWG")||loc.startsWith("IW1")||loc.startsWith("IW2")){
            room = new LatLng(52.678173, -8.569673);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("GEMS0")||loc.startsWith("GEMS1")||loc.startsWith("GEMS2")||loc.startsWith("GEMS3")){
            room = new LatLng(52.678479, -8.568150);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if(loc.startsWith("A0")||loc.startsWith("AM")||loc.startsWith("A1")||loc.startsWith("A2")||loc.startsWith("A3")
                ||loc.startsWith("B0")||loc.startsWith("BM")||loc.startsWith("B1")||loc.startsWith("B2")||loc.startsWith("B3")
                ||loc.startsWith("CG")||loc.startsWith("C0")||loc.startsWith("CM")||loc.startsWith("C1")||loc.startsWith("C2")
                ||loc.startsWith("DG")||loc.startsWith("D0")||loc.startsWith("DM")||loc.startsWith("D1")||loc.startsWith("D2")
                ||loc.startsWith("EG")||loc.startsWith("E0")||loc.startsWith("EM")||loc.startsWith("E1")||loc.startsWith("E2")
        ){
            room = new LatLng(52.674016, -8.571940);
            mMap.addMarker(new MarkerOptions().position(room).title("Classroom"));
        }
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude= location.getLongitude();
            LatLng pos = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(pos).title("Your Position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            mMap.setMyLocationEnabled(true);
            String url = getDirectionsUrl(pos, room);
            googleMapRouteTask task = new googleMapRouteTask(url);
            task.execute();

        }
    }

    /**
     *
     *
     * @param origin
     * @param dest
     * @return url
     */
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + ","
                + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Travelling Mode
        String mode = "mode=driving";

        String parameters = null;
        // Building the parameters to the web service
        parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "xml";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;
        System.out.println("getDerectionsURL--->: " + url);
        return url;
    }


    /**
     *
     *
     * @author Administrator
     *
     */
    private class googleMapRouteTask extends
            AsyncTask<String, Void, List<LatLng>> {
        HttpClient client;
        String url;
        List<LatLng> routes = null;
        public googleMapRouteTask(String url) {
            this.url = url;
        }
        @Override
        protected List<LatLng> doInBackground(String... params) {
            HttpGet get = new HttpGet(url);
            try {
                HttpResponse response = client.execute(get);
                int statusecode = response.getStatusLine().getStatusCode();
                System.out.println("response:" + response + "      statuscode:"
                        + statusecode);
                if (statusecode == 200) {
                    String responseString = EntityUtils.toString(response
                            .getEntity());
                    int status = responseString.indexOf("<status>OK</status>");
                    System.out.println("status:" + status);
                    if (-1 != status) {
                        int pos = responseString.indexOf("<overview_polyline>");
                        pos = responseString.indexOf("<points>", pos + 1);
                        int pos2 = responseString.indexOf("</points>", pos);
                        responseString = responseString
                                .substring(pos + 8, pos2);
                        routes = decodePoly(responseString);
                    } else {

                        return null;
                    }
                } else {

                    return null;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("doInBackground:"+routes);
            return routes;
        }

        @Override
        protected void onPreExecute() {
            client = new DefaultHttpClient();
            client.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    15000);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(List<LatLng> routes) {
            super.onPostExecute(routes);
            if (routes == null) {

                //Toast.makeText(getApplicationContext(), "no route", Toast.LENGTH_LONG).show();
            }
            else{

                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.addAll(routes);
                lineOptions.width(3);
                lineOptions.color(Color.BLUE);
                mMap.addPolyline(lineOptions);

                mMap.animateCamera(CameraUpdateFactory.newLatLng(routes.get(0)));
            }
        }

        /**
         *
         *
         * @param encoded
         * @return List<LatLng>
         */
        private List<LatLng> decodePoly(String encoded) {
            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;
            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;
                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;
                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }
    }
}
