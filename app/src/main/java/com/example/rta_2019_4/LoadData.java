package com.example.rta_2019_4;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadData extends AsyncTask<Integer, Integer, ArrayList<HashMap<String,String>>> {
    private int limit;
    private int offset;
    public AsyncReponse delegate = null;
    private Activity activity;

    public LoadData(Activity activity, int limit, int offset, AsyncReponse asyncReponse){
        this.limit = limit;
        this.offset = offset;
        this.delegate = asyncReponse;
        this.activity = activity;
    }

    @Override
    protected ArrayList<HashMap<String,String>> doInBackground(Integer... integers) {
        ArrayList<HashMap<String,String>>  result = new ArrayList<HashMap<String, String>>();
        onProgressUpdate(1);
        try {
            result = getFileFromURL("https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit="+limit+"&offset="+offset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show();
            }
        });
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
        delegate.processDataFinish(hashMaps);
    }

    public static ArrayList<HashMap<String, String>> getFileFromURL(String urlString) throws IOException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        String data="";
        String allLine = br.readLine();
        ArrayList<HashMap<String,String>>  result = new ArrayList<HashMap<String, String>>();

        while (allLine != null) {
            allLine = allLine.substring(2,allLine.length()-2);
            String[] line = allLine.split("\\},\\{");
            for (int k = 0 ;k<line.length;k++){
                HashMap<String,String> hashMap = new HashMap<>();
                data = line[k];
                data = data.substring(1,data.length()-1);
                String[] tmp1 = data.split("\",\"");
                for(int i=0;i<tmp1.length;i++){
                    String[] tmp2 = tmp1[i].split("\":\"");
                    if(tmp2.length==1){
                        tmp2 =new String[] {tmp2[0],""};
                    }
                    hashMap.put(tmp2[0],tmp2[1]);
                }
                result.add(hashMap);
            }
            allLine = br.readLine();

        }
        br.close();
        urlConnection.disconnect();

        return result;
    }
}
