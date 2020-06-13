package com.test.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CsvDownloader {


    RestTemplate restTemplate = new RestTemplate();

    private static final String DEATH_CSV_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static final String SICK_CSV_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String RECOVER_CSV_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    public String getDeathsCsv(){
        ResponseEntity<String> deathCsv = restTemplate.getForEntity(DEATH_CSV_URL, String.class);
        return deathCsv.getBody();
    }

    public String getSickCsv() {
        ResponseEntity<String> sickCsv = restTemplate.getForEntity(SICK_CSV_URL, String.class);
        return sickCsv.getBody();
    }

    public String getRecoverCsvUrl() {
        ResponseEntity<String> recoverCsv = restTemplate.getForEntity(RECOVER_CSV_URL, String.class);
        return recoverCsv.getBody();
    }
}
