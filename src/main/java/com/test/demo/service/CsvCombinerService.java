package com.test.demo.service;

import com.test.demo.model.NutchasLine;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.*;


@Service
public class CsvCombinerService {
    @Autowired
    CsvDownloader csvDownloader;
    LocalDate lastUpdate= LocalDate.now();
    TreeMap< String, NutchasLine> cachedMap = null;

    private boolean shouldUpdate () {
        if(cachedMap == null)
            return true;
        LocalDate today = LocalDate.now();
        if (today.getDayOfYear() > lastUpdate.getDayOfYear() || today.getYear() > lastUpdate.getYear()) {
            return true;
        }
        return false;
    }


    public TreeMap<String, NutchasLine> combineDeathSickCsv() throws IOException {
        if(!shouldUpdate()){
            return cachedMap;
        }

        //Province/State,Country/Region,Lat,Long
        String deathsCsv = csvDownloader.getDeathsCsv();
        CSVParser deathParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(deathsCsv));
        TreeMap<String, NutchasLine> countryProvinceLineMap = new TreeMap<>();

        deathParser.getRecords().stream()
                .map(line -> {
                    NutchasLine nutchasLine = new NutchasLine(line.get(1), 0, Integer.parseInt(line.get(line.size() - 1)));
                    int latestDeaths = Integer.parseInt(line.get(line.size() - 1));
                    int prevDayDeaths = Integer.parseInt(line.get(line.size() - 2));
                    nutchasLine.setNewDeath(
                            latestDeaths - prevDayDeaths
                    );
                    return nutchasLine;
                })
                .forEach(nutchasLine -> {

                    NutchasLine mapLine =  countryProvinceLineMap.get(nutchasLine.getCountry());
                    if (mapLine == null) {
                        countryProvinceLineMap.put(nutchasLine.getCountry(), nutchasLine);
                    }else{
                      sumLine(mapLine, nutchasLine);
                    }


                });




        String sickCsv = csvDownloader.getSickCsv();

        CSVParser sickParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(sickCsv));
        sickParser
                .getRecords()
                .stream()
                .forEach(line -> {
                    String key = line.get(1);
                    NutchasLine nutchasLine = new NutchasLine(key, 0, 0);
                    nutchasLine.setConfirmedCases(
                            Integer.parseInt(line.get(line.size()-1))
                    );
                    int latestCases = Integer.parseInt(line.get(line.size() - 1));
                    int prevDayCases = Integer.parseInt(line.get(line.size() - 2));
                    nutchasLine.setNewCases(
                    latestCases- prevDayCases
                    );
                    NutchasLine mapLine = countryProvinceLineMap.get(key);
                    if (mapLine == null) {
                        countryProvinceLineMap.put(nutchasLine.getCountry(), nutchasLine);
                    }else{
                        sumLine(mapLine, nutchasLine);
                    }
                });




       String recoverCsv = csvDownloader.getRecoverCsvUrl();

        CSVParser recoverParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(recoverCsv));
        recoverParser
                .getRecords()
                .stream()
                .forEach(line -> {
                    String key = line.get(1);
                    NutchasLine nutchasLine = new NutchasLine(key, 0, 0);
                    nutchasLine.setRecovered(
                            Integer.parseInt(line.get(line.size()-1))
                    );
                    NutchasLine mapLine = countryProvinceLineMap.get(key);
                    if (mapLine == null) {
                        countryProvinceLineMap.put(nutchasLine.getCountry(), nutchasLine);
                    }else{
                        sumLine(mapLine, nutchasLine);
                    }
                });

        // String table = countryProvinceLineMap.values().stream().map(line -> line.toTableLine()).collect(Collectors.joining());
       // return "<table>"+table+"</table>";
    lastUpdate = LocalDate.now();
    cachedMap = countryProvinceLineMap;
        return countryProvinceLineMap;
    }

    private void sumLine(NutchasLine mapLine, NutchasLine nutchasLine) {
        mapLine.setNewCases(mapLine.getNewCases()+nutchasLine.getNewCases());
        mapLine.setDeathsRecorded(mapLine.getDeathsRecorded()+nutchasLine.getDeathsRecorded());
        mapLine.setNewDeath(mapLine.getNewDeath()+nutchasLine.getNewDeath());
        mapLine.setConfirmedCases(mapLine.getConfirmedCases()+nutchasLine.getConfirmedCases());
        mapLine.setRecovered(mapLine.getRecovered()+nutchasLine.getRecovered());
    }

}
