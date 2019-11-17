package com.frontdesk.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontdesk.dto.SpecialistDTO;
import com.frontdesk.exception.FrontDeskGeneralException;
import com.frontdesk.exception.SpecialistNotFoundException;

@Repository
public class FrontDeskDAO {
	@Value("${specialist.list}")
	private String specialistJsonString;

	public List<SpecialistDTO> getSpecialistList(String hospitalID, String specialistType)
			throws JsonProcessingException, SpecialistNotFoundException, FrontDeskGeneralException {
		List<SpecialistDTO> searchedSpecialistList;
		try {
			List<SpecialistDTO> allSpecialistList = new ObjectMapper().readValue(specialistJsonString,
					new TypeReference<List<SpecialistDTO>>() {
					});
			SpecialistDTO searchCrieteria = new SpecialistDTO();
			searchCrieteria.setHospitalId(hospitalID);
			searchCrieteria.setType(specialistType);
			searchedSpecialistList = allSpecialistList.stream().filter(o -> o.equals(searchCrieteria))
					.collect(Collectors.toList());
			if (searchedSpecialistList == null || searchedSpecialistList.isEmpty()) {
				throw new SpecialistNotFoundException("Specialist not found");
			}
			return searchedSpecialistList;
		} catch (Exception ex) {
           throw new FrontDeskGeneralException(ex.getMessage());
		}
	}
}
