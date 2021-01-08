package com.test.demo.controllers;

import com.test.demo.service.CsvCombinerService;
import com.test.demo.service.CsvDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@Configuration
public class CovidController implements WebMvcConfigurer {

    @Autowired
    CsvCombinerService combinerService;
/**
    @GetMapping("/hello/{sickDead}")
    public ResponseEntity<String> getInformation(@PathParam("sickDead")String sickDeadSwitch) throws IOException {

        return ResponseEntity.ok(combinerService.combineDeathSickCsv());

    }
    */

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/public/**")
            .addResourceLocations("classpath:/static")
            .addResourceLocations("file:/static");
}
}
