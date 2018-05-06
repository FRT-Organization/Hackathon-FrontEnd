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
                    mMap.addMarker(new MarkerOptions().position(village).title(repos.get(i).getName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(village));

                    //Draw graph
                    if (i>0) {
                        mMap.addPolyline(new PolylineOptions()
                                .add(village, new LatLng(Double.parseDouble(repos.get(i - 1).getLatitude()), Double.parseDouble(repos.get(i - 1).getLongitude())))
                                .width(5)
                                .color(Color.BLACK));
                    }
                }



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
