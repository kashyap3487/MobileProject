package com.example.projectandroid.DataModel;

public class Employee {
    private  String name;
    private int age, birthYear;
    private  double rate,monthlySalary;
    private  long ID;

    public Employee(){}

    private  Vehicle vehicle;

    public  double annualIncome() {
        return 12*monthlySalary*rate;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if(rate>100)rate=100;
        else if(rate<10)rate=10;

        this.rate = rate;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String toDisplay(){
        return  "Birth Year: "+birthYear+"\n" +
                "Age: "+age+"\n" +
                vehicle.toDisplay() +
                "Occupation Rate: "+getRate()+"\n";
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
