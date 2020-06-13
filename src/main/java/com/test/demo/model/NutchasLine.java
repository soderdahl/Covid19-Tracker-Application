package com.test.demo.model;

import java.text.DecimalFormat;

public class NutchasLine {

    public NutchasLine(String city, String country,  int confirmedCases, int deathsRecorded){
        this.city = city;
        this.country = country;
        this.confirmedCases = confirmedCases;
        this.deathsRecorded = deathsRecorded;
    }

    public NutchasLine() {
    }

    private String country;
    private String city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    public String toTableLine(){
        return String.format("<tr><th>%s</th><th>%s</th><th>%d</th><th>%d</th></tr>",getCountry(),getCity(),getConfirmedCases(),getDeathsRecorded());
    }


    public static String formatNumber( int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String str = decimalFormat.format(number);
        return str;
    }


}
