package com.example.projectandroid.DataModel;

public class Car extends Vehicle{
    private  String type;
    public Car(String type){
        this.type=type;
        setCategory("car");
    }

    @Override
    public String toDisplay() {
        return super.toDisplay()+
                "Type: "+type+"\n";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
