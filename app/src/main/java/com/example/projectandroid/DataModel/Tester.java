package com.example.projectandroid.DataModel;

public class Tester extends Employee {
    private long nbBugs;
    public static final double GAIN_FACTOR_BUG = 10;

    public Tester(String nbBugs) {
        this.nbBugs =Long.parseLong(nbBugs);
    }
    public Tester(int nbBugs) {
        this.nbBugs =nbBugs;
    }

    public long getNbBugs() {
        return nbBugs;
    }

    public void setNbBugs(long nbBugs) {
        this.nbBugs = nbBugs;
    }

    @Override
    public String toDisplay() {
        return   "Name: "+getName()+", a tester\n" +
                super.toDisplay()+
                "annual income is "+this.annualIncome()+"\n" +
                "He/She has corrected "+nbBugs+" bugs.";
    }

    @Override
    public double annualIncome() {
        return super.annualIncome()+nbBugs*GAIN_FACTOR_BUG;
    }
}
