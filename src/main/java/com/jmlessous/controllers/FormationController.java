package com.jmlessous.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jmlessous.entities.Formation;
import com.jmlessous.services.IFormationService;

@RestController
public class FormationController {
	@Autowired
	IFormationService formser;
	
	// http://localhost:8083/JMLessous/retrieve-all-formation
				@GetMapping("/retrieve-all-formation")
				@ResponseBody
				public List<Formation> getFormation() {
				List<Formation> listFormation = formser.retrieveAllFormation();
				return listFormation;
				}
				
	// http://localhost:8083/JMLessous/retrieve-formation/1
				@GetMapping("/retrieve-formation/{formation-id}")
				@ResponseBody
				public Formation retrieveFormation(@PathVariable("fund-id") String formationId) {
				return formser.retrieveFormation(formationId);
				}

	// http://localhost:8083/JMLessous/add-formation
				@PostMapping("/add-formation")
				@ResponseBody
				public Formation addFormation(@RequestBody Formation f)
				{
					Formation formation = formser.addFormation(f);
				return formation; 
				}
				
				
	// http://localhost:8083/JMLessous/remove-formation/3
				@DeleteMapping("/remove-formation/{formation-id}")
				@ResponseBody
				public void removeFund(@PathVariable("fund-id") String formationId) {
					formser.deleteFormation(formationId);
				}
				
    //http://localhost:8083/JMLessous/modify-formation
				@PutMapping("/modify-fund")
				@ResponseBody
				public Formation modifyFormation(@RequestBody Formation formation) {
				return formser.updateFormation(formation);
				}

}
