package com.example.rta_2019_4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rta_2019_4.recyclerViewProcess.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListFragment extends Fragment {
    ListView listView;
    int limit = 5;
    int offset = 5;
    private final MutableLiveData<ArrayList<HashMap<String,String>>> data =
            new MutableLiveData<ArrayList<HashMap<String, String>>>();
    private RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> hash_Maps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        LoadData loadData = new LoadData(getActivity(),limit, offset, new AsyncReponse() {
            @Override
            public void processDataFinish(ArrayList<HashMap<String, String>> hashMaps) {
                hash_Maps = hashMaps;
                recyclerView = getView().findViewById(R.id.recyclerView);
                RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getContext(),hashMaps);
                recyclerView.setAdapter(recycleViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (! recyclerView.canScrollVertically(1)){ //1 for down
                            loadMore(recycleViewAdapter);
                        }
                    }
                });
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
    private void loadMore(RecycleViewAdapter recycleViewAdapter){
        LoadData loadData = new LoadData(getActivity(),limit, offset+100, new AsyncReponse() {
            @Override
            public void processDataFinish(ArrayList<HashMap<String, String>> hashMaps) {
                for (int i=0;i<hashMaps.size();i++){
                    hash_Maps.add(hashMaps.get(i));
                }
                recycleViewAdapter.notifyDataSetChanged();

            }
        });
        loadData.execute();
    }


    public void update(HashMap<String,String> item) {
        data.setValue(hash_Maps);
    }
    public MutableLiveData<ArrayList<HashMap<String,String>>> getData(){
        return data;
    }

}