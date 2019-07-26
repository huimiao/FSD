package com.huimiao.fsd;

/**
 * @ClassName: Report
 * @Description: TODO
 * @author: huimiao
 * @date: 7/26/2019 11:31 AM
 * @version: 1.0
 */
public class Report {
    private int year=1;
    private float startingSalary=0;
    private int numOfInc=0;
    private int numOfDed=0;
    private float incPercent=0;
    private float dedPercent=0;
    private float amountOfInc=0;
    private float amountOfDed=0;
    private float salaryGrowth=0;
    private float yearEndSalary=0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getStartingSalary() {
        return startingSalary;
    }

    public void setStartingSalary(float startingSalary) {
        this.startingSalary = startingSalary;
    }

    public int getNumOfInc() {
        return numOfInc;
    }

    public void setNumOfInc(int numOfInc) {
        this.numOfInc = numOfInc;
    }

    public int getNumOfDed() {
        return numOfDed;
    }

    public void setNumOfDed(int numOfDed) {
        this.numOfDed = numOfDed;
    }

    public float getIncPercent() {
        return incPercent;
    }

    public void setIncPercent(float incPercent) {
        this.incPercent = incPercent;
    }

    public float getDedPercent() {
        return dedPercent;
    }

    public void setDedPercent(float dedPercent) {
        this.dedPercent = dedPercent;
    }

    public float getAmountOfInc() {
        return amountOfInc;
    }

    public void setAmountOfInc(float amountOfInc) {
        this.amountOfInc = amountOfInc;
    }

    public float getAmountOfDed() {
        return amountOfDed;
    }

    public void setAmountOfDed(float amountOfDed) {
        this.amountOfDed = amountOfDed;
    }

    public float getSalaryGrowth() {
        return yearEndSalary - startingSalary;
    }


    public float getYearEndSalary() {
        return yearEndSalary;
    }

    public void setYearEndSalary(float yearEndSalary) {
        this.yearEndSalary = yearEndSalary;
    }

    @Override
    public String toString() {
        return "Report{" +
                "year=" + year +
                ", startingSalary=" + startingSalary +
                ", numOfInc=" + numOfInc +
                ", numOfDed=" + numOfDed +
                ", incPercent=" + incPercent +
                ", dedPercent=" + dedPercent +
                ", amountOfInc=" + amountOfInc +
                ", amountOfDed=" + amountOfDed +
                ", salaryGrowth=" + salaryGrowth +
                ", yearEndSalary=" + yearEndSalary +
                '}';
    }
}
