package com.example.rta_2019_4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rta_2019_4.ListViewProcess.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListFragment extends Fragment {
    ListViewAdapter listViewAdapter;
    ListView listView;
    int limit = 5;
    int offset = 5;


    ArrayList<HashMap<String, String>> hash_Maps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        LoadData loadData = new LoadData(limit, offset, new AsyncReponse() {
            @Override
            public void processDataFinish(ArrayList<HashMap<String, String>> hashMaps) {
                hash_Maps = hashMaps;
                listViewAdapter = new ListViewAdapter(hash_Maps);

                listView = getView().findViewById(R.id.listView);
                listView.setAdapter(listViewAdapter);
            }
        });
        loadData.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        return view;
    }

}