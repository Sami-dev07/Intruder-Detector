package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String timestamp;
}
