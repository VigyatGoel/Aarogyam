package com.vigyat.fitnessappprototype;

public class YogaListModalClass {

    private String yogaName;

    private int yogaImage;

    private String yogaUrl;

    private String yogaBenefits;

    public YogaListModalClass(String yogaName, int yogaImage, String yogaUrl, String yogaBenefits) {
        this.yogaName = yogaName;
        this.yogaImage = yogaImage;
        this.yogaUrl = yogaUrl;
        this.yogaBenefits = yogaBenefits;
    }

    public YogaListModalClass() {
    }

    public String getYogaName() {
        return yogaName;
    }

    public void setYogaName(String yogaName) {
        this.yogaName = yogaName;
    }

    public int getYogaImage() {
        return yogaImage;
    }

    public void setYogaImage(int yogaImage) {
        this.yogaImage = yogaImage;
    }

    public String getYogaUrl() {
        return yogaUrl;
    }

    public void setUrl(String url) {
        this.yogaUrl = yogaUrl;
    }

    public String getyogaBenefits() {
        return yogaBenefits;
    }

    public void setyogaBenefits(String yogaBenefits) {
        this.yogaBenefits = yogaBenefits;
    }
}

