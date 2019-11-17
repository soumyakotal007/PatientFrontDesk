package com.frontdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.frontdesk.dao.FrontDeskDAO;
import com.frontdesk.dto.SpecialistDTO;
import com.frontdesk.exception.FrontDeskGeneralException;
import com.frontdesk.exception.SpecialistNotFoundException;

@Service
public class FrontDeskService {

	@Autowired
	FrontDeskDAO frontDeskDAO;

	@Cacheable(value = "specialistList", key = "{#hospitalID , #specialistType}")
	public List<SpecialistDTO> getSpecialistList(String hospitalID, String specialistType)
			throws JsonProcessingException, SpecialistNotFoundException,FrontDeskGeneralException {
		return frontDeskDAO.getSpecialistList(hospitalID, specialistType);
	}
}
