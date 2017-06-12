package push.exception;

public class PushBean {
	private int code;	
	private String msg;
	private int relcount=1;//实际发生次数
	
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
	
	public void addRelount(){
		relcount = relcount+1;
	}
	
	public int getRelcount() {
		return relcount;
	}
}
