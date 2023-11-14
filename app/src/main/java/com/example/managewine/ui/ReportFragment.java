package com.example.managewine.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.managewine.MyApp;
import com.example.managewine.R;
import com.example.managewine.adapter.WineAdapter;
import com.example.managewine.database.AppDatabase;
import com.example.managewine.database.ManufacturerDao;
import com.example.managewine.database.WineDao;
import com.example.managewine.model.Manufacturer;
import com.example.managewine.model.Wine;

import java.util.List;
import java.util.stream.Collectors;


public class ReportFragment extends Fragment {
    private RecyclerView recyclerView;

    private List<Wine> list;

    private WineAdapter wineAdapter;

    private EditText edtNSX;

    private Button btnSortByAlco;
    AppDatabase database = MyApp.getDatabase();
    ManufacturerDao manufacturerDao = database.manufacturerDao();

    WineDao wineDao = database.wineDao();

    Spinner spinner;

    Manufacturer manufacturer;
    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rcv_wine_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnSortByAlco = view.findViewById(R.id.btn_sort_by_alcohol);
        edtNSX = view.findViewById(R.id.edt_nsx);
        spinner = view.findViewById(R.id.spn_producer);

        ArrayAdapter<Manufacturer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, manufacturerDao.getAllManufacturers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                manufacturer = (Manufacturer) parentView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có mục nào được chọn
            }
        });


        btnSortByAlco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNSX.getText().toString().isEmpty()){
                    list = wineDao.getAllWines();
                    list.clear();
                }else {
                    list = wineDao.getAllWines()
                            .stream()
                            .filter(it -> it.getProductionCountry() == manufacturer.getId()
                                    && it.getAlcoholContent() >= Double.parseDouble(edtNSX.getText().toString()))
                            .collect(Collectors.toList());
                }
                wineAdapter.setData(list);
            }
        });

        wineAdapter = new WineAdapter(list, manufacturerDao.getAllManufacturers() ,getContext(), true ,new WineAdapter.CLicklistener() {
            @Override
            public void onClickUpdate(Wine wine) {

            }

            @Override
            public void onClickDelete(Wine wine) {

            }
        });

        recyclerView.setAdapter(wineAdapter);
        loadData();
    }

    private void loadData() {
        list = wineDao.getAllWines();
        wineAdapter.setData(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }
}