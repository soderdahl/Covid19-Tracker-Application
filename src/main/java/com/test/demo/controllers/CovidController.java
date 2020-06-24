package com.test.demo.controllers;

import com.test.demo.service.CsvCombinerService;
import com.test.demo.service.CsvDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
public class CovidController {

    @Autowired
    CsvCombinerService combinerService;
/**
    @GetMapping("/hello/{sickDead}")
    public ResponseEntity<String> getInformation(@PathParam("sickDead")String sickDeadSwitch) throws IOException {

        return ResponseEntity.ok(combinerService.combineDeathSickCsv());

    }
    */

}
