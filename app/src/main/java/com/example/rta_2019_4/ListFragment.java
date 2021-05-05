package com.example.rta_2019_4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.google.gson.reflect.TypeToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rta_2019_4.model.Data_Model;
import com.example.rta_2019_4.recyclerViewProcess.RecycleViewAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ListFragment extends Fragment {
    int limit = 5;
    int offset = 5;
    private RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> hash_Maps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        LoadData loadData = new LoadData(getActivity(),limit, offset, new AsyncReponse() {
            @Override
            public void processDataFinish(ArrayList<HashMap<String, String>> hashMaps){
                hash_Maps = hashMaps;
                saveDB(hashMaps);
                recyclerView = getView().findViewById(R.id.recyclerView);
                RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getContext(),hashMaps);
                recyclerView.setAdapter(recycleViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (! recyclerView.canScrollVertically(1)){
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
        LoadData loadData = new LoadData(getActivity(),limit, offset+5, new AsyncReponse() {
            @Override
            public void processDataFinish(ArrayList<HashMap<String, String>> hashMaps) {
                for (int i=0;i<hashMaps.size();i++){
                    hash_Maps.add(hashMaps.get(i));
                }
                saveDB(hashMaps);
                recycleViewAdapter.notifyDataSetChanged();
            }
        });
        loadData.execute();
    }
    private void saveDB(ArrayList<HashMap<String,String>> hashMaps) {
        ArrayList<Data_Model> data_models = new ArrayList<Data_Model>();
        for (int i=0;i<hashMaps.size();i++){
            data_models.add(new Data_Model(hashMaps.get(i).get("geom_wkt"),hashToString(hashMaps.get(i))));
        }
        Data_Model.AppDatabase database = Room.databaseBuilder(getContext(),
                Data_Model.AppDatabase.class,
                "db")
                .allowMainThreadQueries()
                .build();
        Data_Model.Data_Model_DAO dataModelDao = database.getDAO();
        for (int i=0;i<data_models.size();i++){
            Data_Model tmp = dataModelDao.getItemByLocation(data_models.get(i).getLocation());
            if(tmp==null){
                dataModelDao.insert(data_models.get(i));
            }
        }

    }
    public static String hashToString (HashMap<String, String> hashMap) {
        if (hashMap == null) return null;

        Gson gson = new Gson();
        //import java.lang.reflect.Type;
        //import com.google.gson.reflect.TypeToken;
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();

        return gson.toJson(hashMap, type);
    }

}