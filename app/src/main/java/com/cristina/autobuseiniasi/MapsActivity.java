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
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        System.out.println("Hello World");
//        HttpGet request = new HttpGet("https://gps.sctpiasi.ro/json");
//        CloseableHttpResponse response = httpClient.execute(request);
//        System.out.println(response.getStatusLine().getStatusCode());
//        HttpEntity entity = response.getEntity();
//        String responseString = EntityUtils.toString(entity);
//        System.out.println(responseString);

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng somewhere = new LatLng(47.9,27.35);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(somewhere).title("MyPoint"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(somewhere));
        List<Bus> listBus = null;
        try {
            listBus=run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Bus b:listBus) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(b.getVehicleLat(),b.vehicleLong)).title(b.getVehicleName()+" "+b.getVehicleDate()));
        }
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
                    bus.getDouble("vehicleLat"),
                    bus.getDouble("vehicleLong"),
                    bus.getString("vehicleDate")));
        }
        return busList;
    }
}
