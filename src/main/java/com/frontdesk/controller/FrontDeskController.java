package com.frontdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.frontdesk.dto.Spcialists;
import com.frontdesk.exception.FrontDeskGeneralException;
import com.frontdesk.exception.SpecialistNotFoundException;
import com.frontdesk.service.FrontDeskService;

@RestController
public class FrontDeskController {

	@Autowired
	FrontDeskService frontDeskService;

	/**
	 * http://localhost:7778/getSpecialist?hospitalId=946&specialistType=Dentist
	 * 
	 * @param hospitalName
	 * @param specialistType
	 * @return
	 * @throws JsonProcessingException
	 * @throws SpecialistNotFoundException
	 */
	@GetMapping(path = "${specialist.list.get.url}", headers = "Accept=*/*", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Spcialists getSpecialistList(@RequestParam("hospitalId") String hospitalName,
			@RequestParam("specialistType") String specialistType)
			throws JsonProcessingException, SpecialistNotFoundException, FrontDeskGeneralException {
		try {
			Spcialists specialists = new Spcialists();
			specialists.setSpecialistList(frontDeskService.getSpecialistList(hospitalName, specialistType));
			return specialists;
		} catch (Exception ex) {
			throw new FrontDeskGeneralException(ex.getMessage());
		}
	}
}
