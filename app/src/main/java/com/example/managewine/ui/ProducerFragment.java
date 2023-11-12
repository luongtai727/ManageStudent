package com.example.managewine.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.managewine.MyApp;
import com.example.managewine.R;
import com.example.managewine.adapter.ProducerAdapter;
import com.example.managewine.database.AppDatabase;
import com.example.managewine.database.ManufacturerDao;
import com.example.managewine.model.Manufacturer;

import java.util.List;

public class ProducerFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Manufacturer> list;
    private ProducerAdapter producerAdapter;
    private Button btnAdd;
    AppDatabase database = MyApp.getDatabase();
    // Lấy DAO
    ManufacturerDao manufacturerDao = database.manufacturerDao();
    public ProducerFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rcv_producer);
        btnAdd = view.findViewById(R.id.button);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        producerAdapter = new ProducerAdapter(list, getContext(), new ProducerAdapter.CLicklistener() {
            @Override
            public void onClickUpdateProducer(Manufacturer manufacturer) {
                showDialog(manufacturer);
            }

            @Override
            public void onClickDeleteProducer(Manufacturer manufacturer) {

                manufacturerDao.deleteManufacturer(manufacturer);
                loadData();
            }
        });

        recyclerView.setAdapter(producerAdapter);
        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });
    }

    private void showDialog(Manufacturer manufacturer) {
        CustomDialog.ManufacturerListener listener = new CustomDialog.ManufacturerListener() {
            @Override
            public void addManufacturer(Manufacturer manufacturer) {
                manufacturerDao.insertManufacturer(manufacturer);
                loadData();
            }

            @Override
            public void aupdateManufacturer(Manufacturer manufacturer) {
                manufacturerDao.updateManufacturer(manufacturer);
                loadData();
            }
        };

        final CustomDialog dialog = new CustomDialog(manufacturer, getContext(), listener);

        dialog.show();
    }

    private void loadData() {
        list = manufacturerDao.getAllManufacturers();
        producerAdapter.setData(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producer, container, false);
    }
}