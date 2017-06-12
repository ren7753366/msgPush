package push.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import push.util.HttpUtils;
import push.util.StrUtil;

public class WXPushBean {
	
//	private String url = "http://10.1.9.89:8066/Enterprise/SendMsgAPI/sendMsg.do";
	
	private String touser;
	private String toparty;
	private String totag;
	private String msgtype;
	private String agentid;
	private String safe;
	private String text;
	
	public String getParam(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Field[] fields = this.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			String fieldName = field.getName();
			try {
				field.setAccessible(true);
				String value = (String) field.get(this);
				if(value==null){
					value = "";
				}
				if(fieldName.equals("text")){
					sb.append("\"").append(fieldName).append("\"").append(":").append(value);
				}else{
					sb.append("\"").append(fieldName).append("\"").append(":").append("\"").append(value).append("\"");
				}
				
				if(i<fields.length-1){
					sb.append(",");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	

	public static void main(String[] args) {
		WXPushBean wxp = new WXPushBean();
		wxp.setTouser("renmansheng");
		wxp.setAgentid("21");
		wxp.setMsgtype("text");
		wxp.setText("{\"content\":\"12313\"}");
		wxp.setSafe("0");
		String param = "inputData="+wxp.getParam();
		
		System.out.println(param);
		
		try {
			String ret = HttpUtils.post("http://10.1.9.89:8066/Enterprise/SendMsgAPI/sendMsg.do", param);
			System.out.println(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
