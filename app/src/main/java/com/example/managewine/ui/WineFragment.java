package com.example.managewine.ui;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.managewine.MyApp;
import com.example.managewine.R;
import com.example.managewine.adapter.WineAdapter;
import com.example.managewine.database.AppDatabase;
import com.example.managewine.database.ManufacturerDao;
import com.example.managewine.database.WineDao;
import com.example.managewine.model.Manufacturer;
import com.example.managewine.model.Wine;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.UUID;

public class WineFragment extends Fragment implements CustomDialogWine.WineListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    private RecyclerView recyclerView;
    private List<Wine> list;
    private WineAdapter wineAdapter;
    private Button btnAdd;
    private ProgressBar progressBar;
    Uri selectedImageUri;
    AppDatabase database = MyApp.getDatabase();

    // Lấy DAO
    ManufacturerDao manufacturerDao = database.manufacturerDao();
    // Lấy DAO
    WineDao wineDao = database.wineDao();
    ImageView imageViewWine;
    public WineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rcv_wine);
        btnAdd = view.findViewById(R.id.button_wine);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progress);

        wineAdapter = new WineAdapter(list, manufacturerDao.getAllManufacturers() ,getContext(), false ,new WineAdapter.CLicklistener() {
            @Override
            public void onClickUpdate(Wine wine) {
                showDialog(wine);

            }

            @Override
            public void onClickDelete(Wine wine) {
                wineDao.deleteWine(wine);
                loadData();
            }
        });

        recyclerView.setAdapter(wineAdapter);
        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });
    }

    private void showDialog(Wine wine) {
        final CustomDialogWine dialog = new CustomDialogWine(wine, getContext(), this);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy Uri của hình ảnh được chọn
            Uri selectedImageUri = data.getData();
            this.selectedImageUri = selectedImageUri;
            // Chuyển đổi Uri thành Bitmap và hiển thị nó
            Bitmap bitmap = getBitmapFromUri(selectedImageUri);
            this.imageViewWine.setImageBitmap(bitmap);
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // Sử dụng ContentResolver để đọc dữ liệu từ Uri và chuyển đổi thành Bitmap
            return BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadData() {
        list = wineDao.getAllWines();
        wineAdapter.setData(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wine, container, false);
    }

    @Override
    public void addWine(Wine wine) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());

        if (this.selectedImageUri != null){
            storageReference.putFile(this.selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // You can get the download URL using taskSnapshot.getDownloadUrl()
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            wine.setImage(downloadUrl);
                            wine.setCode("");
                            wineDao.insertWine(wine);
                            progressBar.setVisibility(View.GONE);
                            loadData();
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                    });
        }else{
            wine.setCode("");
            wineDao.insertWine(wine);
            progressBar.setVisibility(View.GONE);
            loadData();
        }
    }


    @Override
    public void updateWine(Wine wine) {
        if (this.selectedImageUri != null){
            progressBar.setVisibility(View.VISIBLE);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());

            storageReference.putFile(this.selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // You can get the download URL using taskSnapshot.getDownloadUrl()
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            wine.setImage(downloadUrl);
                            wine.setCode("");
                            progressBar.setVisibility(View.GONE);
                            wineDao.updateWine(wine);
                            loadData();
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                    });
        }else{
            wineDao.updateWine(wine);
            loadData();
        }
    }

    @Override
    public void openfile(ImageView imageView) {
        this.imageViewWine = imageView;
        openImageChooser();
    }
}