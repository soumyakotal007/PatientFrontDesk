package com.frontdesk.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "List")
@XmlAccessorType(XmlAccessType.FIELD)
public class Spcialists {
	@XmlElement(name = "item")
	@JsonProperty("")
	private List<SpecialistDTO> specialistList;

	public List<SpecialistDTO> getSpecialistList() {
		return specialistList;
	}

	public void setSpecialistList(List<SpecialistDTO> specialistList) {
		this.specialistList = specialistList;
	}
}
