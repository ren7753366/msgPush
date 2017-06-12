package push;

import push.exception.PushBean;
import push.exception.WXPushThread;

public class WPush {

	private WPush(){
	}
	
	public static void pushError(PushBean pushBean){
		WXPushThread.offer(pushBean);
	}
	public static void init(){
		WXPushThread.init();
	}
	/**
	 * millis时间内发送count次
	 * @param count
	 * @param millis
	 */
	public static void init(int count,int millis){
		WXPushThread.init(count, millis);
	}
}


