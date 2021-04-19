package com.probit.parkapp.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.ui.SignupFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FloatingActionButton waze;
    private MapsViewModel mViewModel;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        mViewModel.getParkingsLiveData().observe(getViewLifecycleOwner(), this::addMarkersAndMove);
        waze =  root.findViewById(R.id.waze_button);
        searchView  = (SearchView) root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Mover el botón de "Mi Ubicación" hacia abajo y colocarlo encima de los controles de zoom
        View location_button = root.findViewWithTag("GoogleMapMyLocationButton");
        View zoom_in_button = root.findViewWithTag("GoogleMapZoomInButton");
        View zoom_layout = (View) zoom_in_button.getParent();
        RelativeLayout.LayoutParams location_layout = (RelativeLayout.LayoutParams) location_button.getLayoutParams();
        location_layout.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        location_layout.addRule(RelativeLayout.ABOVE, zoom_layout.getId());


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        enableLocation();

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }

    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap _googleMap) {
            googleMap = _googleMap;
            googleMap.setOnMarkerClickListener(onMarkerClickListener);
            googleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setMapToolbarEnabled(true);

            mViewModel.onMapReady();

            enableLocation();
        }
    };

    private void addMarkersAndMove(ArrayList<Parking> parkings){
        float zoom = 13;
        LatLng montevideo = new LatLng(-34.90328, -56.18816);
        for (Parking park : parkings) {
            float latitud = Float.parseFloat(park.getLatitude());
            float longitud = Float.parseFloat(park.getLongitude());
            LatLng parking = new LatLng(latitud, longitud);
            ;
            googleMap.addMarker(new MarkerOptions().position(parking).title(park.getName())).setTag(parkings.indexOf(park));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(montevideo, zoom));
    }

    private void enableLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
            }

        }
    }

    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            int position = (int) (marker.getTag());
            Parking pk = mViewModel.getParkings().get(position);

            ReservaFragment bottomSheet = new ReservaFragment(pk);
            bottomSheet.show(getActivity().getSupportFragmentManager(), "InformacionParking");



            waze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        // Launch Waze to look for selected parking:
                        String url = "https://waze.com/ul?ll=" + pk.getLatitude() + "," + pk.getLongitude() + "&z=10";
                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                        startActivity( intent );
                    }
                    catch ( ActivityNotFoundException ex  )
                    {
                        // If Waze is not installed, open it in Google Play:
                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                        startActivity(intent);
                    }
                }
            });



            return false;
        }

    };

    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

        }
    };


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    enableLocation();
                } else {
                    // Permission Denied
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

//    @SuppressLint("MissingPermission")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.i("OnRequestPermissions", "ENTRA AL METODO");
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
//                    Log.i("PERMISOS CONCEDIDOS", "PermisosOK");
//                    enableLocation();
//                } else {
//                    // Permission Denied
//                    Toast.makeText(getActivity(), "No se aceptó permisos", Toast.LENGTH_LONG).show();
//                }
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
//        Log.i("i/MapsFragment", "OnPause");
    }


    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }
    

    @Override
    public void onStop() {
        super.onStop();
        mapFragment.onStop();
    }
}