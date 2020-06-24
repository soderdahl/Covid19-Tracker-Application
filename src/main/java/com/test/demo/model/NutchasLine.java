package com.test.demo.model;

import java.text.DecimalFormat;

public class NutchasLine {

    public NutchasLine( String country,  int confirmedCases, int deathsRecorded){
        this.country = country;
        this.confirmedCases = confirmedCases;
        this.deathsRecorded = deathsRecorded;
    }

    public NutchasLine() {
    }

    private String country;
    private int confirmedCases;
    private int deathsRecorded;
    private int recovered;
    private int newCases;
    private int newDeath;

    public int getRecovered() {
        return recovered;
    }

    public int getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public int getDeathsRecorded() {
        return deathsRecorded;
    }

    public void setDeathsRecorded(int deathsRecorded) {
        this.deathsRecorded = deathsRecorded;
    }


    public static String formatNumber( int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String str = decimalFormat.format(number);
        return str;
    }


}
