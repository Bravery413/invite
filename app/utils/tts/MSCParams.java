package utils.tts;

public class MSCParams {

	private String content;//播报内容
	
	private String speaker;//播报人，请参阅：http://www.xfyun.cn/services/online_tts?tab_index=1
	
	private String speed;//播报语速
	
	private String volume;//播报音量
	
	public MSCParams(String content,String speaker,String speed,String volume){
		this.content = content;
		this.speaker = speaker;
		this.speed = speed;
		this.volume = volume;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

}
