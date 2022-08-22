package com.example.projectandroid.DataModel;

public class Programmer extends Employee {
    private long nbProject;
    public static final double GAIN_FACTOR_PROJECT = 200;

    public Programmer(String bugs) {
        this.nbProject = Long.parseLong(bugs);
    }
    public Programmer(int bugs) {
        this.nbProject = bugs;
    }

    public long getNbProject() {
        return nbProject;
    }

    public void setNbProject(long nbProject) {
        this.nbProject = nbProject;
    }

    @Override
    public String toDisplay() {
        return   "Name: "+getName()+", a programmer\n" +
                super.toDisplay()+
                "annual income is "+this.annualIncome()+"\n" +
                "He/She has completed "+nbProject+" projects.";
    }
    @Override
    public double annualIncome() {
        return super.annualIncome()+nbProject*GAIN_FACTOR_PROJECT;
    }
}
