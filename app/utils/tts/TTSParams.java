package utils.tts;

import com.google.gson.annotations.SerializedName;

public class TTSParams {

	@SerializedName("hospital_id")
	private Long hospitalId;
	
	@SerializedName("room_code")
	private String room_code;
	
	@SerializedName("patient_name")
	private String patientName;

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
}
