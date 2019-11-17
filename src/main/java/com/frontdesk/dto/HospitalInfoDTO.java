package com.frontdesk.dto;

public class HospitalInfoDTO {
	private String hospitalId;
	private String availableday;
	private String bedNo;

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getAvailableday() {
		return availableday;
	}

	public void setAvailableday(String availableday) {
		this.availableday = availableday;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

}
