package com.example.managewine.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.managewine.R;
import com.example.managewine.model.Manufacturer;

public class CustomDialog extends Dialog {

    interface ManufacturerListener {
        public void addManufacturer(Manufacturer manufacturer);
        public void aupdateManufacturer(Manufacturer manufacturer);
    }
    public Context context;
    private EditText editTextFullName;
    private EditText editTextDescreption;
    private Button buttonOK;
    private Button buttonCancel, btnUpdate;

    private Manufacturer manufacturer;

    private CustomDialog.ManufacturerListener listener;

    public CustomDialog(Manufacturer manufacturer, Context context, CustomDialog.ManufacturerListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.manufacturer = manufacturer;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);

        this.editTextFullName = (EditText) findViewById(R.id.name);
        this.editTextDescreption = (EditText) findViewById(R.id.description);
        this.buttonOK = (Button) findViewById(R.id.btn_add);
        this.buttonCancel  = (Button) findViewById(R.id.btn_cancel);
        this.btnUpdate = findViewById(R.id.btn_update);

        if (manufacturer == null){
            this.btnUpdate.setVisibility(View.GONE);
            this.buttonOK.setVisibility(View.VISIBLE);

        }else{
            this.btnUpdate.setVisibility(View.VISIBLE);
            this.buttonOK.setVisibility(View.GONE);
            editTextFullName.setText(manufacturer.getName());
            editTextDescreption.setText(manufacturer.getDescription());
        }

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
    }

    // User click "OK" button.
    private void buttonOKClick()  {
        String name = this.editTextFullName.getText().toString();
        String description = this.editTextFullName.getText().toString();

        if(name == null || description.isEmpty())  {
            Toast.makeText(this.context, "Not emplty!", Toast.LENGTH_LONG).show();
            return;
        }

        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            Manufacturer manu = new Manufacturer();
            manu.setName(name);
            manu.setDescription(description);

            this.listener.addManufacturer(manu);
        }
    }

    private void buttonUpdateClick()  {
        String name = this.editTextFullName.getText().toString();
        String description = this.editTextDescreption.getText().toString();

        if(name == null || description.isEmpty())  {
            Toast.makeText(this.context, "Not emplty!", Toast.LENGTH_LONG).show();
            return;
        }

        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            this.manufacturer.setName(name);
            this.manufacturer.setDescription(description);
            this.listener.aupdateManufacturer(this.manufacturer);
        }
    }

    // User click "Cancel" button.
    private void buttonCancelClick()  {
        this.dismiss();
    }
}