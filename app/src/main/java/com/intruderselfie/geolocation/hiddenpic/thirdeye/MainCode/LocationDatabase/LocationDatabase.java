package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LocationEntity.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {

    public abstract LocationDao locationDao();
    public static LocationDatabase INSTANCE;

    public static LocationDatabase getDatabase(Context context) {

        if (INSTANCE == null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, "location_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
