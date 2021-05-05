package com.example.rta_2019_4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rta_2019_4.model.Data_Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment {
    private GoogleMap googleMap;
    MapView mapView;
    private ArrayList<HashMap<String,String>> hashMaps;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    private ArrayList<HashMap<String,String>> loadDB(){
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
        Data_Model.AppDatabase database = Room.databaseBuilder(getContext(),
                Data_Model.AppDatabase.class,
                "db")
                .allowMainThreadQueries()
                .build();
        Data_Model.Data_Model_DAO dataModelDao = database.getDAO();
        List<Data_Model> tmp = dataModelDao.getAll();
        for (int i =0;i<tmp.size();i++){
            HashMap<String,String> hashMap = stringToHash(tmp.get(i).getHashMap());
            result.add(hashMap);
        }

        return result;
    }
    private static HashMap<String,String> stringToHash (String json) {
        if (json == null) return null;

        Gson gson = new Gson();
        //import java.lang.reflect.Type;
        //import com.google.gson.reflect.TypeToken;
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();

        return gson.fromJson(json, type);
    }
    private void update(){
        hashMaps = loadDB();
        for (int i=0;i<hashMaps.size();i++){
            HashMap<String,String> item = hashMaps.get(i);
            LatLng tmp = new LatLng(Double.parseDouble(item.get("latitude"))
                    ,Double.parseDouble(item.get("longitude")));
            googleMap.addMarker(new MarkerOptions().position(tmp).title(item.get("country_id")).snippet(item.get("source_article")));
        }
    }
}