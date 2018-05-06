package solutions.frt.trashman;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import solutions.frt.trashman.Models.Village;
import solutions.frt.trashman.Services.VillageClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String BASE_URL = "http://83.212.108.171:8080/";
    private GoogleMap mMap;
    List<Village> repos;
    List<LatLng> listLatLong = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        VillageClient client = retrofit.create(VillageClient.class);
        Call<List<Village>> call = client.fetchVillages();

        call.enqueue(new Callback<List<Village>>() {
            @Override
            public void onResponse(Call<List<Village>> call, Response<List<Village>> response) {
                repos = response.body();

                //Place markers on the map
                for (int i = 0; i < repos.size(); i++) {

                    LatLng village = new LatLng(Double.parseDouble(repos.get(i).getLatitude()), Double.parseDouble(repos.get(i).getLongitude()));
                    listLatLong.add(village);
                    mMap.addMarker(new MarkerOptions().position(village).title(repos.get(i).getName()));
                    if (i == 0) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(village, 10));
                    }
                }

                PolylineOptions polylineOptions1 = new PolylineOptions().add(listLatLong.get(0)).add(listLatLong.get(1));
                PolylineOptions polylineOptions2 = new PolylineOptions().add(listLatLong.get(0)).add(listLatLong.get(4));
                PolylineOptions polylineOptions3 = new PolylineOptions().add(listLatLong.get(1)).add(listLatLong.get(2));
                PolylineOptions polylineOptions4 = new PolylineOptions().add(listLatLong.get(4)).add(listLatLong.get(2));
                PolylineOptions polylineOptions5 = new PolylineOptions().add(listLatLong.get(4)).add(listLatLong.get(3));
                PolylineOptions polylineOptions6 = new PolylineOptions().add(listLatLong.get(4)).add(listLatLong.get(5));
                PolylineOptions polylineOptions7 = new PolylineOptions().add(listLatLong.get(6)).add(listLatLong.get(3));
                PolylineOptions polylineOptions8 = new PolylineOptions().add(listLatLong.get(6)).add(listLatLong.get(8));
                PolylineOptions polylineOptions9 = new PolylineOptions().add(listLatLong.get(6)).add(listLatLong.get(9));
                PolylineOptions polylineOptions10 = new PolylineOptions().add(listLatLong.get(2)).add(listLatLong.get(5));
                PolylineOptions polylineOptions11 = new PolylineOptions().add(listLatLong.get(3)).add(listLatLong.get(5));
                PolylineOptions polylineOptions12 = new PolylineOptions().add(listLatLong.get(5)).add(listLatLong.get(7));
                PolylineOptions polylineOptions13 = new PolylineOptions().add(listLatLong.get(9)).add(listLatLong.get(7));
                PolylineOptions polylineOptions14 = new PolylineOptions().add(listLatLong.get(9)).add(listLatLong.get(8));
                PolylineOptions polylineOptions15 = new PolylineOptions().add(listLatLong.get(0)).add(listLatLong.get(6));

                mMap.addPolyline(polylineOptions1);
                mMap.addPolyline(polylineOptions2);
                mMap.addPolyline(polylineOptions3);
                mMap.addPolyline(polylineOptions4);
                mMap.addPolyline(polylineOptions5);
                mMap.addPolyline(polylineOptions6);
                mMap.addPolyline(polylineOptions7);
                mMap.addPolyline(polylineOptions8);
                mMap.addPolyline(polylineOptions9);
                mMap.addPolyline(polylineOptions10);
                mMap.addPolyline(polylineOptions11);
                mMap.addPolyline(polylineOptions12);
                mMap.addPolyline(polylineOptions13);
                mMap.addPolyline(polylineOptions14);
                mMap.addPolyline(polylineOptions15);
            }

            @Override
            public void onFailure(Call<List<Village>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Could not retrieve villages.", Toast.LENGTH_LONG);
            }
        });

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
    }
}
