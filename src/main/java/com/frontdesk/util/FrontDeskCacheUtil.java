package com.frontdesk.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontdesk.dto.HospitalInfoDTO;
import com.frontdesk.dto.PatientInfoDTO;
import com.frontdesk.dto.SpecialistDTO;

@Component
public class FrontDeskCacheUtil {

	@Value("${hospital.details}")
	private String hospitalDetail;

	@Value("${patient.details}")
	private String patientDetail;

	@Value("${specialist.list}")
	private String specialistJsonString;

	@Value("${specialist.list.file.location}")
	private String specialistFileLocation;

	ConcurrentHashMap<String, List> cacheData;

	private final String ALL_SPECIALIST_KEY = "allSpecialistList";
	private final String ALL_HOSPITAL_KEY = "allHospitalList";
	private final String ALL_PATIENT_KEY = "allPatientList";

	@PostConstruct
	public void init() throws JsonProcessingException {
		cacheData = new ConcurrentHashMap<String, List>();
		List<SpecialistDTO> allSpecialistList = null;
		List<HospitalInfoDTO> allHospitalList = null;
		List<PatientInfoDTO> allPatientList = null;

		if (StringUtils.isNotBlank(specialistJsonString)) {
			allSpecialistList = new ObjectMapper().readValue(specialistJsonString,
					new TypeReference<List<SpecialistDTO>>() {
					});
			cacheData.put(ALL_SPECIALIST_KEY, allSpecialistList);
		}

		if (StringUtils.isNotBlank(hospitalDetail)) {
			allHospitalList = new ObjectMapper().readValue(hospitalDetail, new TypeReference<List<HospitalInfoDTO>>() {
			});
			cacheData.put(ALL_HOSPITAL_KEY, allHospitalList);
		}

		if (StringUtils.isNotBlank(patientDetail)) {
			allPatientList = new ObjectMapper().readValue(patientDetail, new TypeReference<List<PatientInfoDTO>>() {
			});
			cacheData.put(ALL_PATIENT_KEY, allPatientList);
		}
	}

	public List<SpecialistDTO> getAllSpecialistList() {
		return cacheData.get(ALL_SPECIALIST_KEY);
	}

	public List<HospitalInfoDTO> getAllHospitalList() {
		return cacheData.get(ALL_HOSPITAL_KEY);
	}

	public List<PatientInfoDTO> getAllPatientList() {
		return cacheData.get(ALL_PATIENT_KEY);
	}

	@PreDestroy
	public void saveDataInProperties() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PropertiesConfiguration config = new PropertiesConfiguration(specialistFileLocation);
		List<HospitalInfoDTO> allHospitalList = getAllHospitalList();
		List<PatientInfoDTO> allPatientList = getAllPatientList();
		if(allHospitalList != null && !allHospitalList.isEmpty())
		{
		  config.setProperty("hospital.details", mapper.writeValueAsString(allHospitalList) );
		}
		if(allPatientList!= null && !allPatientList.isEmpty())
		{
		  config.setProperty("patient.details", mapper.writeValueAsString(allPatientList));
		}
		config.save();
	}
}
