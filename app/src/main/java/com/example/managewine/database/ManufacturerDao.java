package com.example.managewine.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managewine.model.Manufacturer;
import java.util.List;

@Dao
public interface ManufacturerDao {
    @Insert
    void insertManufacturer(Manufacturer manufacturer);

    @Query("SELECT * FROM manufacturers")
    List<Manufacturer> getAllManufacturers();

    @Update
    void updateManufacturer(Manufacturer manufacturer);

    @Delete
    void deleteManufacturer(Manufacturer manufacturer);
}

