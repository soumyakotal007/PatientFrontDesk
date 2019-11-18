package com.frontdesk.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontdesk.dto.AppointmentDTO;
import com.frontdesk.dto.HospitalInfoDTO;
import com.frontdesk.dto.PatientInfoDTO;
import com.frontdesk.dto.SpecialistDTO;
import com.frontdesk.exception.FrontDeskGeneralException;
import com.frontdesk.exception.SpecialistNotFoundException;
import com.frontdesk.util.FrontDeskCacheUtil;

@Repository
public class FrontDeskDAO {
	@Value("${specialist.list}")
	private String specialistJsonString;
	
	@Value("${hospital.days}")
	private String hospitalDays;
	
	@Autowired
	FrontDeskCacheUtil util;

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
	
	public synchronized AppointmentDTO getSpecialistList(String specialistName, String appointmentDay , String patientName , String hospitalID) throws FrontDeskGeneralException
	{
		SpecialistDTO specialistDTO = util.getAllSpecialistList().stream().filter(o -> o.getName().equalsIgnoreCase(specialistName)).findAny().orElse(null);
		if(specialistDTO != null && specialistDTO.getAvailableday().contains(appointmentDay)) {
			HospitalInfoDTO hospitalInfoDTO = new HospitalInfoDTO();
			hospitalInfoDTO.setHospitalId(hospitalID);
			hospitalInfoDTO.setAvailableday(appointmentDay);
			int hospitalIndex = util.getAllHospitalList().indexOf(hospitalInfoDTO);
			if(hospitalIndex != -1)
			{
				hospitalInfoDTO = util.getAllHospitalList().get(hospitalIndex);
				setHospitalBed(hospitalInfoDTO);
				if(hospitalInfoDTO.getBedNo() > 0)
				{
					AppointmentDTO appointmentDTO = new AppointmentDTO();
					appointmentDTO.setSpecialistName(specialistName);
					appointmentDTO.setAppointmentTime(specialistDTO.getAvailableTime());
					appointmentDTO.setPatientName(patientName);
					appointmentDTO.setAppointmentDay(appointmentDay);
					
					PatientInfoDTO patientInfoDTO = new PatientInfoDTO();
					patientInfoDTO.setAdmitStatus("BOOKED");
					patientInfoDTO.setAppointmentDay(appointmentDay);
					patientInfoDTO.setAppointmentTime(specialistDTO.getAvailableTime());
					patientInfoDTO.setHospitalID(hospitalID);
					patientInfoDTO.setPatientName(patientName);
					patientInfoDTO.setSpecialistName(specialistName);
					
					util.getAllPatientList().add(patientInfoDTO);
					return appointmentDTO;
				}
				else
				{
					throw new FrontDeskGeneralException("No bed is available in the Appointment date.Please select altername appointment date of the Specialist.");
				}
			}
			else
			{
				throw new FrontDeskGeneralException("No Hospital details is available.");
			}
		}
		else {
			throw new FrontDeskGeneralException("Spacialist is not available in the Appointment Day.Please select a valid appointment day.");
		}
		
	}
	
	public synchronized List<HospitalInfoDTO> getHospitalBedInfo(String hospitalID) throws FrontDeskGeneralException
	{
		List<HospitalInfoDTO> hospitalDetails = new ArrayList<>(util.getAllHospitalList().stream().filter(o -> o.getHospitalId().equals(hospitalID)).collect(Collectors.toList()));
		if(hospitalDetails != null && !hospitalDetails.isEmpty())
		{
			hospitalDetails.stream().forEach(o -> setHospitalBed(o));
			return hospitalDetails;
		}
		else
		{
			throw new FrontDeskGeneralException("No Hospital details is available.Please enter correct hospital Id");
		}
	}
	
	private void setHospitalBed(HospitalInfoDTO dto)
	{
		int occupiedBed = util.getAllPatientList().stream().filter(o -> o.getAppointmentDay().equals(dto.getAvailableday()) && !o.getAdmitStatus().equals("DISCHARGE")).collect(Collectors.toList()).size();
		dto.setBedNo(dto.getBedNo() - occupiedBed);
	}
}
