package com.jmlessous.controllers;

import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.services.IPortfeuilleService;
import com.jmlessous.services.IPortfeuilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
//@RequestMapping("/Market")
public class PortfeuilleController {
    @Autowired
    IPortfeuilleService service;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/Market/getAllMarkets")
    public Object getMarkets() {
        RestTemplate restTemplate = new RestTemplate();
        Object markets = restTemplate.getForObject("http://bvmt.com.tn/rest_api/rest/market/groups/11,12,13,99", Object.class);
        return markets;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/Market/getActionLimits/{isin}")
    public Object getLimits(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/limits/"+isin;
        Object limits = restTemplate.getForObject(url, Object.class);
        return limits;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/Market/getActionIntradays/{isin}")
    public Object getIntradays(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/intraday/"+isin;
        Object intradays = restTemplate.getForObject(url, Object.class);
        return intradays;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/Market/getMarket/{isin}")
    public Object getMarket(@PathVariable("isin") String isin) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://bvmt.com.tn/rest_api/rest/market/"+isin;
        Object market = restTemplate.getForObject(url, Object.class);
        return market;
    }



    @GetMapping("getPortfeuille/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Portfeuille getPortfeuilleById(@PathVariable("id") Long id){
        return service.retrievePortfeuille(id);
    }

    @GetMapping("getPortfeuilleByUser/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Portfeuille getPortfeuilleByUser(@PathVariable("id") Long id){
        return service.retrievePortfeuillebyUser(id);
    }

    @PostMapping("addPortfeuille/{solde}/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Portfeuille addPortfeuille(@PathVariable("solde") float solde,@PathVariable("id") Long id){
        return service.addPortfeuille(solde,id);
    }

    @GetMapping("getProduits/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Set<ProduitFinancier> getProduitsByPortfeuille(@PathVariable("id") Long id){
        return service.rerieveProduits(id);
    }
   /* @PostMapping("addProduits/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void addProduitToPortfeuille(@RequestBody ProduitFinancier produit,@PathVariable("id") Long id){
         service.addProduitToPortfeuille(id,produit);
    }*/

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/Market/getYearHistoryBySymbol/{symbol}")
    public Object getHistoryByCode(@PathVariable("symbol") String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.ilboursa.com/api/charting/GetTicksEOD?symbol="+symbol+"&length=365&period=0&guid=4QGJkkSWRC8zaK_2x5U0QlsfzhyPz0Q3s_NRb_y78kc";
        Object history = restTemplate.getForObject(url, Object.class);
        return history;
    }
}
