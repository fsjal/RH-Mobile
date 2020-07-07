package com.project.rhmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.rhmobile.dao.Services;
import com.project.rhmobile.entities.MenuItem;
import com.project.rhmobile.entities.Service;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.project.rhmobile.entities.ServiceType.*;

public final class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1000;

    private FusedLocationProviderClient fusedLocation;
    private GoogleMap map;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private List<Service> points = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();
    private TextView serviceContentText;
    private TextView serviceAddressText;
    private TextView servicePhoneText;
    private ImageView serviceImage;
    private BottomSheetBehavior<View> behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton button = findViewById(R.id.search_button);
        View sheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(sheet);
        button.setOnClickListener(e -> searchServices());
        //MenuItem item = getIntent().getExtras().getParcelable("service");
        MenuItem item = new MenuItem(Hospital, "", 0, "Hôpital", getString(R.string.server_hospital_param));
        TextView serviceText = findViewById(R.id.service_name_text);
        serviceContentText = findViewById(R.id.service_content_text);
        serviceAddressText = findViewById(R.id.service_address_text);
        servicePhoneText = findViewById(R.id.service_phone_text);
        serviceImage = findViewById(R.id.service_image);
        String serviceName = getString(R.string.service) + item.getContent();
        serviceText.setText(serviceName);
    }

    private void searchServices() {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);

        View viewInflated = getLayoutInflater().inflate(R.layout.alert_search, null, false);
        final EditText input = viewInflated.findViewById(R.id.search_input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            int distance = Integer.parseInt(input.getText().toString());
            showServicesByDistance(distance);
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showServicesByDistance(int distance) {
        for (Marker marker : markers) {
            marker.setVisible(false);
            Service service = (Service) marker.getTag();
            if (calculateDistance((float) lastKnownLocation.getLatitude(), (float) lastKnownLocation.getLongitude(),
                    service.getLatitude(), service.getLongitude()) < distance) {
                marker.setVisible(true);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                Log.e("request permission", "we got user's permission");
                updateLocationUI();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("map", "map is ready");
        map = googleMap;
        map.setOnMarkerClickListener(this::onMarkerClick);
        map.setOnMapClickListener(latLng -> behavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
        updateLocationUI();
        getDeviceLocation();
    }

    private boolean onMarkerClick(Marker marker) {
        if (marker.getTag() != null) {
            Service service = (Service) marker.getTag();
            String content = service.getName();
            String address = getString(R.string.address) + service.getAddress();
            String phone = service.getPhone();

            serviceContentText.setText(content);
            serviceAddressText.setText(address);
            servicePhoneText.setText(phone);
            try {
                Picasso.get().load(service.getImageUrl()).fit()
                        .placeholder(R.drawable.ic_service_image_holder).into(serviceImage);
            } catch (IllegalArgumentException ignored) {
                serviceImage.setImageResource(R.drawable.ic_service_image_holder);
            }
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return true;
        }
        return false;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            Log.e("location permission", "we got permission");
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSIONS_REQUEST_LOCATION);
            Log.e("getDeviceLocation", "ask user for permission");
        }
    }

    private void getDeviceLocation() {
        Log.e("getDeviceLocation", "enter");
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocation.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.e("getDeviceLocation", "task is success");

                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        Log.e("getDeviceLocation", "last position " + lastKnownLocation);
                        if (lastKnownLocation != null) {
                            Log.e("getDeviceLocation", "last location is known");
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                                    map.getMinZoomLevel()));
                            new Handler().postDelayed(this::markPlaces, 2000);
                        } else {
                            map.getUiSettings().setMyLocationButtonEnabled(true);
                            Toast.makeText(MainActivity.this, R.string.activate_GPS, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateLocationUI() {
        Log.e("updateLocationUI", "enter");

        if (map == null) {
            Log.e("updateLocationUI", "map is null");
            return;
        }
        try {
            if (locationPermissionGranted) {
                Log.e("updateLocationUI", "we have permission");
                getDeviceLocation();
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Log.e("updateLocationUI", "we dont have permission");
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void markPlaces() {
        markMyPlace();
        fetchServicePoints();
    }

    private void markMyPlace() {
        LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.i_m_here))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,map.getMaxZoomLevel() * 2 / 3), 3000, null);
        map.addMarker(markerOptions);
    }

    private void markServicesPoints() {
        for (Service point : points) {
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(point.getName())
                    .visible(false);
            Marker marker = map.addMarker(markerOptions);
            marker.setTag(point);
            markers.add(marker);
        }
    }

    private void fetchServicePoints() {
        //MenuItem item = getIntent().getExtras().getParcelable("service");
        MenuItem item = new MenuItem(Hospital, "", 0, "Hôpital", getString(R.string.server_hospital_param));
        Log.e("service", item.getServiceType().toString());
        String url = getString(R.string.host) + getString(R.string.server_services) + "?service=" + item.getServerParam();
        Log.e("url", url);
        Services.get(this, url, this::onResponse, this::onError);
    }

    private void onError(VolleyError error) {
        if (error.getMessage() != null) Log.e("volley", error.getMessage());
    }

    private void onResponse(String response) {
        Log.e("response", response);
        try {
            JSONArray json = new JSONArray(response);
            points.clear();
            for (int i = 0; i < json.length() - 1; i++) {
                JSONObject service = json.getJSONObject(i);
                String name = service.getString(getString(R.string.service_param_name));
                String address = service.getString(getString(R.string.service_param_address));
                String imageUrl = service.getString(getString(R.string.service_param_image_url));
                String phone = service.getString(getString(R.string.service_param_phone));
                float latitude = (float) service.getDouble(getString(R.string.service_param_latitude));
                float longitude = (float) service.getDouble(getString(R.string.service_param_longitude));

                points.add(new Service(Hospital, name, address, phone, imageUrl, latitude, longitude));
            }
            markServicesPoints();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int calculateDistance(float lat1, float lng1, float lat2, float lng2) {
        int R = 6371000;
        double dLat =   (lat2-lat1) * Math.PI / 180;
        double dLon =   (lng2-lng1) * Math.PI / 180;
        double a =      Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c =      2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (int) (R * c);
    }
}