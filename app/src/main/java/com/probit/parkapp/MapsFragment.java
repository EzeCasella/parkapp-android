package com.probit.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {


    SupportMapFragment mapFragment;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            float zoom = 11;

            // Add a marker in Montevideo and move the camera
            LatLng montevideo = new LatLng(-34.90328, -56.18816);
            googleMap.addMarker(new MarkerOptions().position(montevideo).title("Montevideo parkings"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(montevideo, zoom));

            // Colocar un marcador en la misma posición
            //googleMap.addMarker(new MarkerOptions().position(montevideo));
            // Más opciones para el marcador en:
            // https://developers.google.com/maps/documentation/android/marker

            // Otras configuraciones pueden realizarse a través de UiSettings
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);


//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapFragment.onPause();
        Log.i("i/MapsFragment", "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("i/MapsFragment", "OnResume");
//        mapFragment.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mapFragment.onStop();
        Log.i("i/MapsFragment", "OnStop");
    }
}