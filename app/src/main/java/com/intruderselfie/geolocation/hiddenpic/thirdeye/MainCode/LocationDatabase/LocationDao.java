package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface  LocationDao {

    @Insert
    void insert(LocationEntity note);

    @Query("SELECT * FROM LocationEntity")
    List<LocationEntity> getAllLocations();

}
