package com.probit.parkapp.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.probit.parkapp.repositories.ParkingsRepository;
import com.probit.parkapp.ui.dashboard.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    private MapsViewModel mViewModel;
    boolean tienePermiso = false;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        private final int LOCATION_PERMISSION_REQUEST_CODE = 1;



        @Override
        public void onMapReady(GoogleMap googleMap) {

            googleMap.setOnMarkerClickListener(onMarkerClickListener);
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);

            ParkingsRepository.getParkings(data -> {
                ArrayList<Parking> parkings = (ArrayList<Parking>) data;
                int i = 0;
                for (Parking park : parkings) {
                    float latitud = Float.parseFloat(park.getLatitude()) ;
                    float longitud = Float.parseFloat(park.getLongitude());
                    float zoom = 13;


                    LatLng montevideo = new LatLng(latitud, longitud);
                    googleMap.addMarker(new MarkerOptions().position(montevideo).title(park.getName())).setTag(i);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(montevideo, zoom));
                    i = i + 1;

                    }

                });


            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String permiso1 = Manifest.permission.ACCESS_FINE_LOCATION;
                String permiso2 = Manifest.permission.ACCESS_COARSE_LOCATION;
                List<String> permissionlist = new ArrayList<>();
                permissionlist.add(permiso1);
                permissionlist.add(permiso2);

                ActivityCompat.requestPermissions(getActivity(), permissionlist.toArray(new String[permissionlist.size()]), LOCATION_PERMISSION_REQUEST_CODE);

            }
            else {
                googleMap.setMyLocationEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
            }
        }
    };




    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
//            int position = (int) (marker.getTag());
//            Toast.makeText(getContext(), "Mando mensaje click" + position, Toast.LENGTH_LONG).show();


            return false;
        }

    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "No se aceptó permisos", Toast.LENGTH_LONG).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);

       /* mViewModel =
                new ViewModelProvider(this).get(MapsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        return root;*/

    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        // TODO: Use the ViewModel
    }*/

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

    @Override
    public boolean onMarkerClick(Marker marker) {
        int position = (int) (marker.getTag());
        Toast.makeText(getContext(), "Mando mensaje click" + position, Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}