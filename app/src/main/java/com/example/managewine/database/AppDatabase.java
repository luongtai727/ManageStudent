package com.example.managewine.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.managewine.adapter.ProducerAdapter;
import com.example.managewine.model.Manufacturer;
import com.example.managewine.model.Wine;

@Database(entities = {Manufacturer.class, Wine.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ManufacturerDao manufacturerDao();

    public abstract WineDao wineDao();
}