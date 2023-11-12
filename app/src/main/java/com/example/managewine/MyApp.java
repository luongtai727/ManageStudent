package com.example.managewine;

import android.app.Application;
import androidx.room.Room;
import com.example.managewine.database.AppDatabase;
import com.google.firebase.FirebaseApp;

public class MyApp extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_database")
                .allowMainThreadQueries().build();

    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
