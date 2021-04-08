package com.probit.parkapp.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    private MapsViewModel mViewModel;
    private GoogleMap googleMap;
//    UiSettings uiSettings = googleMap.getUiSettings();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        mViewModel.getParkingsLiveData().observe(getViewLifecycleOwner(), this::addMarkersAndMove);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

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
            uiSettings.setMapToolbarEnabled(false);

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

            String permiso1 = Manifest.permission.ACCESS_FINE_LOCATION;
            String permiso2 = Manifest.permission.ACCESS_COARSE_LOCATION;
            List<String> permissionlist = new ArrayList<>();
            permissionlist.add(permiso1);
            permissionlist.add(permiso2);

            ActivityCompat.requestPermissions(getActivity(), permissionlist.toArray(new String[permissionlist.size()]), LOCATION_PERMISSION_REQUEST_CODE);

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
            Toast.makeText(getContext(), pk.getAddress(), Toast.LENGTH_SHORT).show();


            // BottomSheetModalFragment

            return false;
        }

    };

    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(getContext(), "ENTRO A RESERVAR" , Toast.LENGTH_SHORT).show();
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    googleMap.setMyLocationEnabled(true);
                    enableLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "No se aceptó permisos", Toast.LENGTH_LONG).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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