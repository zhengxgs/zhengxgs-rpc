package com.zhengxgs.rpc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class HandlerMapper {

	/**
	 * key: interfaceName, value: handler
	 * @param handlers
	 * @return
	 */
	public static Map<String, Object> getHandlerMap(Object... handlers) {
		if (null == handlers || handlers.length == 0) {
			throw new RuntimeException("handle is null");
		}
		Map<String, Object> handlerMap = new HashMap<String, Object>();
		for (Object handler : handlers) {
			Class<?>[] interfaces = handler.getClass().getInterfaces();
			for (Class<?> aClass : interfaces) {
				String interfaceName = aClass.getName();
				if (null != handlerMap.put(interfaceName, handler)) {
					throw new RuntimeException("interface:" + interfaceName + "have multiple implementations");
				}
			}
		}
		return handlerMap;
	}
}
