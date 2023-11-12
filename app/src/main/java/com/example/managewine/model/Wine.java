package com.example.managewine.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wines")
public class Wine {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String code;
    private String name;
    private double alcoholContent;
    private int yearsAged;
    private long productionCountry;

    private String image;

    // getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(double alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public int getYearsAged() {
        return yearsAged;
    }

    public void setYearsAged(int yearsAged) {
        this.yearsAged = yearsAged;
    }

    public long getProductionCountry() {
        return productionCountry;
    }

    public void setProductionCountry(long productionCountry) {
        this.productionCountry = productionCountry;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

