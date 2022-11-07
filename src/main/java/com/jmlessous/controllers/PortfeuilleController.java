package com.jmlessous.controllers;

import org.springframework.web.bind.annotation.*;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getActionLimits/{isin}")
    public Object getLimits(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/limits/"+isin;
        Object limits = restTemplate.getForObject(url, Object.class);
        return limits;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getActionIntradays/{isin}")
    public Object getIntradays(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/intraday/"+isin;
        Object intradays = restTemplate.getForObject(url, Object.class);
        return intradays;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getMarket/{isin}")
    public Object getMarket(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/market/"+isin;
        Object market = restTemplate.getForObject(url, Object.class);
        return market;
    }
}
