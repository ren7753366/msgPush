package push;

import push.exception.PushBean;

public class Test {
	public static void main(String[] args) {
		
		WPush.init(1,1000);
		PushBean pushBean = new PushBean();
		pushBean.setCode(1);
		pushBean.setMsg("mark redis error");
		WPush.pushError(pushBean);
		
	}
}
