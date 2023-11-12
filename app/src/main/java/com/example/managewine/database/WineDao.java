package com.example.managewine.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managewine.model.Wine;

import java.util.List;

@Dao
public interface WineDao {

    @Insert
    void insertWine(Wine wine);

    @Query("SELECT * FROM wines")
    List<Wine> getAllWines();

    @Query("SELECT * FROM wines WHERE id = :wineId")
    Wine getWineById(long wineId);

    @Update
    void updateWine(Wine wine);

    @Delete
    void deleteWine(Wine wine);
}
