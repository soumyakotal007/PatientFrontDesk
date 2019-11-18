package com.frontdesk.dto;

public class HospitalInfoDTO {
	private String hospitalId;
	private String availableday;
	private int bedNo;

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

	

	public int getBedNo() {
		return bedNo;
	}

	public void setBedNo(int bedNo) {
		this.bedNo = bedNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availableday == null) ? 0 : availableday.hashCode());
		result = prime * result + ((hospitalId == null) ? 0 : hospitalId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospitalInfoDTO other = (HospitalInfoDTO) obj;
		if (availableday == null) {
			if (other.availableday != null)
				return false;
		} else if (!availableday.equals(other.availableday))
			return false;
		if (hospitalId == null) {
			if (other.hospitalId != null)
				return false;
		} else if (!hospitalId.equals(other.hospitalId))
			return false;
		return true;
	}

}
