package com.example.rta_2019_4.model;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity(tableName = "db")
public class Data_Model {
    @PrimaryKey
    @NonNull private String location;
    private String hashMap;


    public Data_Model(String location,String hashMap){
        this.location = location;
        this.hashMap = hashMap;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public String getHashMap() {
        return hashMap;
    }

    public void setHashMap(String hashMap) {
        this.hashMap = hashMap;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @Dao
    public interface Data_Model_DAO {
        @Query("SELECT * FROM db")
        List<Data_Model> getAll();

        @Insert
        void insert(Data_Model data_model);

        @Query("SELECT * FROM db WHERE location = :location")
        public Data_Model getItemByLocation(String location);

        @Query("DELETE FROM db")
        void delete();
    }
    @Database(entities = {Data_Model.class}, version = 1)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract Data_Model.Data_Model_DAO getDAO();
    }
}
