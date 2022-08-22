package com.example.projectandroid.DataModel;



public class Vehicle {
    private String make,plate,color,category;

    public Vehicle(){}

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toDisplay() {
        return "Employee has a "+(category.equals("car")?"car":"motorcycle")+"\n" +
                "-Model: "+make+"\n" +
                "-Plate: "+plate+"\n" +
                "-Color: "+color+"\n";
    }
}
