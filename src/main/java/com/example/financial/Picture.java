package com.example.financial;

public class Picture {
    private String imgSrc;

    public String getAbsoluteImgSrc() {
        return "src/main/resources/com/example/financial/Icon/newType/" + imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Picture() {
    }

    public Picture(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
