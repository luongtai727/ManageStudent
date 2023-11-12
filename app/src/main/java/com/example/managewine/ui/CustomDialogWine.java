package com.example.managewine.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.managewine.MyApp;
import com.example.managewine.R;
import com.example.managewine.database.AppDatabase;
import com.example.managewine.database.ManufacturerDao;
import com.example.managewine.model.Manufacturer;
import com.example.managewine.model.Wine;

public class CustomDialogWine extends Dialog {

    private static final int PICK_IMAGE_REQUEST = 1;

    interface WineListener {
        public void addWine(Wine wine);
        public void updateWine(Wine wine);
        public void openfile(ImageView imageView);
    }

    public Context context;
    public TextView textViewName;
    public Spinner spinner;
    public TextView textViewNongDo;
    public TextView textViewSX;
    public ImageView imgWine;
    public ImageView imgOption;
    private Button buttonOK;
    private Button buttonCancel, btnUpdate;
    AppDatabase database = MyApp.getDatabase();
    // Lấy DAO
    ManufacturerDao manufacturerDao = database.manufacturerDao();
    private Wine wine;
    private CustomDialogWine.WineListener listener;
    private Manufacturer manufacturer;

    public CustomDialogWine(Wine wine, Context context, CustomDialogWine.WineListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.wine = wine;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_wine);

        this.textViewName = (EditText) findViewById(R.id.name_wine);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.textViewNongDo = (EditText) findViewById(R.id.edt_nong_do);
        this.textViewSX = (EditText) findViewById(R.id.edt_sx);

        this.buttonOK = (Button) findViewById(R.id.btn_add_dialog);
        this.buttonCancel  = (Button) findViewById(R.id.btn_cancel_dialog);
        this.btnUpdate = findViewById(R.id.btn_update_dialog);
        this.imgWine = findViewById(R.id.img_wine_dialog);

        if (wine == null){
            this.btnUpdate.setVisibility(View.GONE);
            this.buttonOK.setVisibility(View.VISIBLE);

        }else{
            this.btnUpdate.setVisibility(View.VISIBLE);
            this.buttonOK.setVisibility(View.GONE);
            textViewName.setText(wine.getName());
            textViewNongDo.setText(wine.getAlcoholContent()+"");
            textViewSX.setText(wine.getYearsAged()+"");

            Glide.with(context)
                    .load(wine.getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.baseline_downloading_24)) // Ảnh thay thế khi đang tải
                    .into(imgWine);
        }

        ArrayAdapter<Manufacturer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, manufacturerDao.getAllManufacturers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner.setAdapter(adapter);

        imgWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openfile(imgWine);
            }
        });

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });

        this.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonUpdateClick();
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });

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

    }

    // User click "OK" button.
    private void buttonOKClick()  {
        String name = this.textViewName.getText().toString();
       // String nsx = this.textViewNSX.getText().toString();
        String year = this.textViewSX.getText().toString();
        String nongdo = this.textViewNongDo.getText().toString();

        if (name.isEmpty() || year.isEmpty() || nongdo.isEmpty()){
            Toast.makeText(context, "Not empty", Toast.LENGTH_SHORT).show();
            return;
        }

        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            Wine wine = new Wine();
            wine.setName(name);
            wine.setYearsAged(Integer.parseInt(year));
            wine.setProductionCountry(manufacturer.getId());
            wine.setImage("https://firebasestorage.googleapis.com/v0/b/managewine.appspot.com/o/images%2Fno.jpeg?alt=media&token=a2b1c94c-ff73-48d4-8129-24622d2b9cce");
           // wine.setProductionCountry(nsx);
            wine.setAlcoholContent(Double.parseDouble(nongdo));
            this.listener.addWine(wine);
        }
    }

    private void buttonUpdateClick()  {
        String name = this.textViewName.getText().toString();
        String year = this.textViewSX.getText().toString();
        String nongdo = this.textViewNongDo.getText().toString();

        if (name.isEmpty() || year.isEmpty() || nongdo.isEmpty()){
            Toast.makeText(context, "Not empty", Toast.LENGTH_SHORT).show();
            return;
        }

        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            this.wine.setName(name);
            this.wine.setYearsAged(Integer.parseInt(year));
            this.wine.setProductionCountry(manufacturer.getId());
            //this.wine.setImage("https://firebasestorage.googleapis.com/v0/b/managewine.appspot.com/o/images%2Fno.jpeg?alt=media&token=a2b1c94c-ff73-48d4-8129-24622d2b9cce");
            this.wine.setAlcoholContent(Double.parseDouble(nongdo));
            this.listener.updateWine(wine);

        }
    }

    // User click "Cancel" button.
    private void buttonCancelClick()  {
        this.dismiss();
    }
}