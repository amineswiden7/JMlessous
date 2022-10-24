package com.jmlessous.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Market")
public class PortfeuilleController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllMarkets")
    public Object getMarkets() {

        RestTemplate restTemplate = new RestTemplate();
        Object markets = restTemplate.getForObject("http://bvmt.com.tn/rest_api/rest/market/groups/11,12,13,99", Object.class);
        return markets;
    }
}
