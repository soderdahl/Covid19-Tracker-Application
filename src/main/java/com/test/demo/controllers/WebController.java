package com.test.demo.controllers;

import com.test.demo.model.NutchasLine;
import com.test.demo.service.CsvCombinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.TreeMap;

@Controller
public class WebController {

    @Autowired
    CsvCombinerService csvCombinerService;
    @GetMapping("/")
    public String home2(Model model) throws IOException {
        TreeMap<String, NutchasLine> data = csvCombinerService.combineDeathSickCsv();
        System.out.println(data);
        int totalConfirmedCases = data.values().stream().mapToInt(confirmed -> confirmed.getConfirmedCases()).sum();
        int totalDeath = data.values().stream().mapToInt(death -> death.getDeathsRecorded()).sum();
        int totalRecover = data.values().stream().mapToInt(recover -> recover.getRecovered()).sum();


        model.addAttribute("data", data);
        model.addAttribute("totalConfirmedCases",NutchasLine.formatNumber(totalConfirmedCases));
        model.addAttribute("totalDeath",NutchasLine.formatNumber(totalDeath));
        model.addAttribute("totalRecover", NutchasLine.formatNumber(totalRecover));

        return "index";
    }

}
