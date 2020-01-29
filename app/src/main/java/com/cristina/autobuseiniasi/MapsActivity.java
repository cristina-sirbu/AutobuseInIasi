package com.cristina.autobuseiniasi;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Bus> listBus = null;
        try {
            listBus=run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Bus b:listBus) {

            if(b.vehicleLat!=null&&b.vehicleLong!=null&&b.vehicleLong!=""&&b.vehicleLat!=""&&!b.vehicleLat.isEmpty()&&!b.vehicleLong.isEmpty()) {
                System.out.println("Vehicle name= "+b.getVehicleName()+"; Vehicle Lat= "+b.getVehicleLat()+"; Vehicle Long= "+b.getVehicleLong());
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(
                                Double.parseDouble(b.vehicleLat),
                                Double.parseDouble(b.vehicleLong))
                ).title(b.getVehicleName() + " " +
                        b.getVehicleDate()));
            }
        }
        // Add a marker Home and move the camera
        LatLng home = new LatLng(47.173946,27.541889);
        mMap.addMarker(new MarkerOptions().position(home).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));

    }

    private final OkHttpClient client = new OkHttpClient();

    public List<Bus> run() throws Exception {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        Request request = new Request.Builder()
                .url("https://gps.sctpiasi.ro/json")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        List<Bus> busList = new ArrayList<>();

        String responseAsString = response.body().string();
        JSONArray myArray = new JSONArray(responseAsString);
        for(int i =0; i<myArray.length();i++) {
            JSONObject bus = myArray.getJSONObject(i);
            busList.add(new Bus(bus.getString("vehicleName"),
                    bus.getString("vehicleLat"),
                    bus.getString("vehicleLong"),
                    bus.getString("vehicleDate")));
        }
        return busList;
    }
}
