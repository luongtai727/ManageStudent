package com.example.managewine.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.managewine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    ProducerFragment producerFragment = new ProducerFragment();
    WineFragment wineFragment = new WineFragment();
    ReportFragment reportFragment = new ReportFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.page_1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, wineFragment)
                        .commit();
                return true;

            case R.id.page_2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, producerFragment)
                        .commit();
                return true;

            case R.id.page_3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, reportFragment)
                        .commit();
                return true;
        }
        return false;
    }
}