package com.vigyat.fitnessappprototype;

public class GovtHelpLineModal {

    int image;
    String helplineName;
    String url;

    public GovtHelpLineModal(int image, String helplineName, String url) {
        this.image = image;
        this.helplineName = helplineName;
        this.url = url;
    }

    public int getImage() {
        return image;
    }


    public String getHelplineName() {
        return helplineName;
    }


    public String getUrl() {
        return url;
    }

}
