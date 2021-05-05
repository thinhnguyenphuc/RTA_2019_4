package com.example.rta_2019_4;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public interface AsyncReponse {
    void processDataFinish(ArrayList<HashMap<String,String>> hashMaps);
}
