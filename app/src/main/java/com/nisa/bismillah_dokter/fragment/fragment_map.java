package com.nisa.bismillah_dokter.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nisa.bismillah_dokter.R;
import com.nisa.bismillah_dokter.helper.GPStrack;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_map extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private GPStrack gpStrack;
    private double lat;
    private double lon;
    private String name_location;
    private LatLng lokasiku;
    private Intent intent;
    EditText edtseacrh;
    Button btnlocation;
    public fragment_map() {
        // Required empty public constructor
    }

    public static fragment_map newInstance() {
        fragment_map fragment = new fragment_map();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=        inflater.inflate(R.layout.fragment_fragment_map, container, false);
        btnlocation=(Button)v.findViewById(R.id.btnlocation);
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                akseslokasiku();

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        edtseacrh = (EditText)v.findViewById(R.id.edtseacrh);
// untuk mengecek gps di hp ada atau nggak
        edtseacrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setCountry("ID") // set filter negara

                        .build();


                Intent intent = null;
                try {
                    intent = new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, 1);

            }
        });
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
            Toast.makeText(getActivity(), "Gps already enabled", Toast.LENGTH_SHORT).show();
// finish();
        }
// Todo Location Already on ... end

        if (!hasGPSDevice(getActivity())) {
            Toast.makeText(getActivity(), "Gps not Supported", Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
            Toast.makeText(getActivity(), "Gps not enabled", Toast.LENGTH_SHORT).show();
            enableLoc();

        }

        
        // Inflate the layout for this fragment
        return v;
    }

    private void akseslokasiku() {
        gpStrack = new GPStrack(getActivity());
        if (gpStrack.canGetLocation() && mMap != null) {
            lat = gpStrack.getLatitude();
            lon = gpStrack.getLongitude();
            mMap.clear();
            name_location = convertname(lat, lon);
            Toast.makeText(getActivity(), "lat " + lat + "\nlon " + lon, Toast.LENGTH_SHORT).show();
            lokasiku = new LatLng(lat, lon);
//add marker
            mMap.addMarker(new MarkerOptions().position(lokasiku).title(name_location)).setIcon(
                    BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_foreground)
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 21));

        }
    }

    private String convertname(double lat, double lon) {

        name_location = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> list = geocoder.getFromLocation(lat, lon, 1);
            if (list != null && list.size() > 0) {
                name_location = list.get(0).getAddressLine(0) + "" + list.get(0).getCountryName();

//fetch data from addresses
            } else {
                Toast.makeText(getActivity(), "kosong", Toast.LENGTH_SHORT).show();
//display Toast message
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name_location;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.596882, 106.805992);//latitude itu yang depannya ada - (min) & langtitude belakakngnya
        mMap.addMarker(new MarkerOptions().position(sydney).title("in Bogor,Indonesia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
// Show the dialog by calling startResolutionForResult(),
// and check the result in onActivityResult().
                                //untuk navigasi program
                                //request location nya merah alt & enter dan pilih constan field
                                status.startResolutionForResult(getActivity(), REQUEST_LOCATION);

                                getActivity().finish();
                            } catch (IntentSender.SendIntentException e) {
// Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place = PlaceAutocomplete.getPlace(getActivity(), data);
//getlat dan get lon
        //ketika respon berhasil dia menggunakan result ok
        if (requestCode == 1 && resultCode == RESULT_OK) {
            lat = place.getLatLng().latitude;
            lon = place.getLatLng().longitude;
            name_location = place.getName().toString();
            edtseacrh.setText(name_location);
            //map.clear = untuk mengahapus tampilan map sebelumnya
            mMap.clear();
            //pindah berdasaarkan atitude
            //gunanya untuk mengarahkan point yang kita tuju
            addmarker(lat, lon);
        }
    }

    private void addmarker(double lat, double lon) {
        lokasiku = new LatLng(lat, lon);
        name_location = convertname(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 21));// v itu untuk radius jangan salah loh
        //tambah besar angkanya tambah jauh jaraknya
        mMap.addMarker(new MarkerOptions().position(lokasiku).title(name_location));
    }
}
