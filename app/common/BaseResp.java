package common;

import com.google.gson.annotations.SerializedName;

public class BaseResp {

	@SerializedName(value = "code")
	private int code;

	@SerializedName(value = "msg")
	private String msg;
	
	public BaseResp() {
	}
	
	public BaseResp(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResp(RetCode retCode) {
		setRetCode(retCode);
	}
	
	public void setRetCode(RetCode retCode) {
		this.code = retCode.getCode();
		this.msg = retCode.getMsg();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
