package com.example.financial;

public class Type {
    private String type;
    private int money;
    private String imageSource;
    private String buttonColor;

    public Type() {
    }

    public Type(String type, int money, String imageSource, String buttonColor) {
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAbsoluteImageSource() {
        return "src/main/resources/com/example/financial/Icon/newType/" + imageSource;
    }

    public String getImageSource() {
        return imageSource;
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

    public boolean equal(Type type) {
        return this.type.equals(type.getType())
                && this.buttonColor.equals(type.getButtonColor())
                && this.money == type.getMoney()
                && this.imageSource.equals(type.getImageSource());
    }
}
