package push.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import push.bean.WXPushBean;
import push.util.HttpUtils;

public class WXPushThread implements Runnable {

	private int count = 50;//默认值
	private long millis = 5*60*1000l;//默认值
	private Logger pushLog = Logger.getLogger("push");
	private static boolean inited = false;
	
	public static void init(){
		new Thread(new WXPushThread()).start();
		inited = true;
	}
	
	public static void init(int count,long millis){
		new Thread(new WXPushThread(count, millis)).start();
		inited = true;
	}
	
	private WXPushThread(){
	}
	private WXPushThread(int count,long millis){
		this.count = count;
		this.millis = millis;
	}
	
	private static Map<Integer,PushBean> map = new HashMap<Integer,PushBean>();
	public static void offer(PushBean pushBean){
		if(!inited){//判断是否初始化过了
			init();
		}
		int code = pushBean.getCode();
		if(map.containsKey(code)){//判断是否包含
			pushBean.addRelount();
			map.put(code, pushBean);
		}else{
			map.put(code, pushBean);
		}
	}

	public void run() {
		pushLog.info("-----------------------push thread start-------------------");
		while(true){
			try {
				Thread.sleep(millis);//5分钟执行一次，判断是否超过一定次数
				for(Integer key:map.keySet()){
					PushBean pushBean = map.remove(key);
					pushLog.info("recv error times:"+pushBean.getRelcount()+",push limit:"+this.count);
					if(pushBean.getRelcount()>=this.count){//5分钟报警50次，发送报警异常
						//推送异常给公众号
						WXPushBean wxp = new WXPushBean();
						wxp.setTouser("renmansheng");
						wxp.setAgentid("21");
						wxp.setMsgtype("text");
						
						String cont = "";
						if(millis>=1000*60){
							cont+=millis/1000/60+"分钟内发生";
						}else{
							cont+=millis/1000+"秒内发生";
						}
						wxp.setText("{\"content\":\""+pushBean.getMsg()+":"+cont+pushBean.getRelcount()+"次异常\"}");
						wxp.setSafe("0");
						String param = "inputData="+wxp.getParam();
						String ret = HttpUtils.post("http://10.1.9.89:8066/Enterprise/SendMsgAPI/sendMsg.do", param);
						pushLog.info("push end param:"+param+",ret:"+ret);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
