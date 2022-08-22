package com.example.projectandroid.DataModel;

public class Motorcycle extends Vehicle{
    private  boolean sidecar;

    public Motorcycle(boolean sidecar) {
        super();
        this.sidecar = sidecar;
        setCategory("bike");
    }

    @Override
    public String toDisplay() {
        return super.toDisplay()+
                "-"+(sidecar?"with":"without")+ " a sidecar\n";
    }

    public boolean isSidecar() {
        return sidecar;
    }

    public void setSidecar(boolean sidecar) {
        this.sidecar = sidecar;
    }
}
