package com.example.projectandroid.DataModel;

public class Manager extends Employee{
    private long nbClients;
    public static final double GAIN_FACTOR_CLIENT = 500;

    public Manager(String nbClients) {
        super();
        this.nbClients = Long.parseLong(nbClients);
    }

    public Manager(int nbClients) {
        super();
        this.nbClients = nbClients;
    }

    @Override
    public String toDisplay() {
        return   "Name: "+getName()+", a manager\n" +
                 super.toDisplay()+
                "annual income is "+this.annualIncome()+"\n" +
                "He/She has brought "+nbClients+" new clients.";
    }

    public long getNbClients() {
        return nbClients;
    }

    public void setNbClients(long nbClients) {
        this.nbClients = nbClients;
    }

    public  double annualIncome() {
        return super.annualIncome()+nbClients*GAIN_FACTOR_CLIENT;
    }
}
