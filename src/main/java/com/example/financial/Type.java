package com.example.financial;

public class Type {
    private String type;
    private long money;
    private String imageSource;
    private String buttonColor;

    public Type() {
    }

    public Type(String type, long money, String imageSource, String buttonColor) {
        this.type = type;
        this.money = money;
        this.imageSource = imageSource;
        this.buttonColor = buttonColor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getImageSource() {
        return "src/main/resources/com/example/financial/Icon/" + imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }
}
