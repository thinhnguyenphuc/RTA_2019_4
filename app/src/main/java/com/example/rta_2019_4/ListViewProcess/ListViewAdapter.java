package com.example.rta_2019_4.ListViewProcess;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rta_2019_4.Model.Data_Model;
import com.example.rta_2019_4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> hashMaps;

    public ListViewAdapter(ArrayList<HashMap<String,String>> hashMaps){
        this.hashMaps = hashMaps;
    }
    @Override
    public int getCount() {
        return hashMaps.size();
    }

    @Override
    public Object getItem(int position) {
        return hashMaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewFile;
        if (convertView == null) {
            viewFile = View.inflate(parent.getContext(), R.layout.item, null);
        } else viewFile = convertView;
        HashMap<String,String> item = (HashMap<String, String>) getItem(position);
        for (String key: item.keySet()) {
            String value = item.get(key);
            String tmp = (String) ((TextView) viewFile.findViewById(R.id.textView)).getText();
            ((TextView) viewFile.findViewById(R.id.textView)).setText(tmp+key+"-"+value+"\n");
        }



        return viewFile;
    }
}
