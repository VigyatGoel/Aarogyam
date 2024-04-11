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



    public int getYogaImage() {
        return yogaImage;
    }



    public String getYogaUrl() {
        return yogaUrl;
    }



    public String getyogaBenefits() {
        return yogaBenefits;
    }


}

