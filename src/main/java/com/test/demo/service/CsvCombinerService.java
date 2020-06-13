package com.test.demo.service;

import com.test.demo.model.NutchasLine;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


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
                .map( line ->{NutchasLine nutchasLine = new NutchasLine(line.get(0), line.get(1), 0, Integer.parseInt(line.get(line.size()-1)));
                    int latestDeaths = Integer.parseInt(line.get(line.size() - 1));
                    int prevDayDeaths = Integer.parseInt(line.get(line.size() - 2));
                    nutchasLine.setNewDeath(
                            latestDeaths- prevDayDeaths
                    );
                return nutchasLine;
                })
                .forEach(nutchasLine ->
                        countryProvinceLineMap.put(nutchasLine.getCity()+nutchasLine.getCountry(), nutchasLine));



        String sickCsv = csvDownloader.getSickCsv();

        CSVParser sickParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(sickCsv));
        sickParser
                .getRecords()
                .stream()
                .forEach(line -> {
                    String key = line.get(0)+line.get(1);
                    NutchasLine nutchasLine = countryProvinceLineMap.get(key);
                    nutchasLine.setConfirmedCases(
                            Integer.parseInt(line.get(line.size()-1))
                    );
                    int latestCases = Integer.parseInt(line.get(line.size() - 1));
                    int prevDayCases = Integer.parseInt(line.get(line.size() - 2));
                    nutchasLine.setNewCases(
                    latestCases- prevDayCases
                    );
                });



       String recoverCsv = csvDownloader.getRecoverCsvUrl();

        CSVParser recoverParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new StringReader(recoverCsv));
        recoverParser
                .getRecords()
                .stream()
                .forEach(line -> {
                    String key = line.get(0)+line.get(1);
                    NutchasLine nutchasLine = countryProvinceLineMap.get(key);
                   if(nutchasLine == null){
                       nutchasLine = new NutchasLine();
                       countryProvinceLineMap.put(key,nutchasLine);
                   }
                    nutchasLine.setRecovered(
                            Integer.parseInt(line.get(line.size()-1))
                    );
                });


        /**
         * int latestCases = Integer.parseInt(record.get(record.size() - 1));
         *             int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
         *             locationStats.setLatestTotalCases(latestCases);
         *             locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
         */
        // String table = countryProvinceLineMap.values().stream().map(line -> line.toTableLine()).collect(Collectors.joining());
       // return "<table>"+table+"</table>";
    lastUpdate = LocalDate.now();
    cachedMap = countryProvinceLineMap;
        return countryProvinceLineMap;
    }

}
