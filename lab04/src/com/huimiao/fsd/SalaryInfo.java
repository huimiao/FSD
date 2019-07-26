package com.huimiao.fsd;

/**
 * @ClassName: SalaryInfo
 * @Description: TODO
 * @author: huimiao
 * @date: 7/25/2019 9:04 PM
 * @version: 1.0
 */
public class SalaryInfo {
    private float baseSalary;
    private int incFrequency;
    private int dedFrequency;
    private float incPercent;
    private float dedPercent;
    private int year;

    public float getBaseSalary() {
        return baseSalary;
    }

    public int getIncFrequency() {
        return incFrequency;
    }

    public int getDedFrequency() {
        return dedFrequency;
    }

    public float getIncPercent() {
        return incPercent;
    }

    public float getDedPercent() {
        return dedPercent;
    }

    public int getYear() {
        return year;
    }

    public SalaryInfo(float baseSalary, int incFrequency, int dedFrequency, float incPercent, float dedPercent, int year) {
        this.baseSalary = baseSalary;
        this.incFrequency = incFrequency;
        this.dedFrequency = dedFrequency;
        this.incPercent = incPercent;
        this.dedPercent = dedPercent;
        this.year = year;
    }

    @Override
    public String toString() {
        return "SalaryInfo{" +
                "baseSalary=" + baseSalary +
                ", incFrequency=" + incFrequency +
                ", dedFrequency=" + dedFrequency +
                ", incPercent=" + incPercent +
                ", dedPercent=" + dedPercent +
                ", year=" + year +
                '}';
    }

}
