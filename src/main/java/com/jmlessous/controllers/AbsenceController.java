package com.jmlessous.controllers;

import com.jmlessous.entities.Absence;
import com.jmlessous.services.IAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Absence")
public class AbsenceController {
    @Autowired
    IAbsenceService abs;
    //http://localhost:8083//JMLessous/Absence/retrieve-all-Absence
    @GetMapping("/retrieve-all-Absence")
    @ResponseBody
    public List<Absence> getAbsence() {
        List<Absence> listcom = abs.retrieveAllAbsences();
        return listcom;
    }
    //http://localhost:8083/JMLessous/Absence/add-Absence/{client-id}
    @PostMapping("/add-Absence")
    @ResponseBody
    public Absence addComplaint(@RequestBody Absence c )
    {
        Absence absence = abs.addOffre(c);
        return absence;
    }
    //http://localhost:8083/JMLessous/Absence/retrieve-all-Absence/{client-id}
    @GetMapping("/retrieve-all-Absence/{client-id}")
    @ResponseBody
    public List<Absence> getAbsencebyuser(@PathVariable("client-id") Long clientid) {
        List<Absence> listabs = abs.retrieveAbsenceByUser(clientid);
        return listabs;
    }

    //http://localhost:8083/JMLessous/Absence/update-Absence/{client-id}
    @PutMapping("/update-Absence/{client-id}")
    @ResponseBody
    public Absence updateComplaint(@RequestBody Absence c ,@PathVariable("client-id") Long clientid)
    {
        Absence absence = abs.updateAbsence(c,clientid);
        return absence;
    }















}
