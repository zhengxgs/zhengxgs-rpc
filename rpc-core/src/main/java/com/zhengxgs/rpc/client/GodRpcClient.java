package com.zhengxgs.rpc.client;

import com.zhengxgs.rpc.conn.RpcConnection;
import com.zhengxgs.rpc.result.InvokeRequest;
import com.zhengxgs.rpc.result.InvokeResponse;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhengxgs on 2016/4/9.
 */
public class GodRpcClient implements RpcClient {

	private RpcConnection connection;

	public GodRpcClient(RpcConnection connection) {
		this.connection = connection;
	}

	private RpcConnection getConnection() throws IOException {
		if (null != connection) {
			connection.connect();
			return connection;
		}
		return connection;
	}

	@Override
	public <T> T proxy(Class<T> interfaces) {
		return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class<?>[] { interfaces }, new InstanceHandler());
	}

	class InstanceHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String className = method.getDeclaringClass().getName();
			List<String> parameterTypes = new LinkedList<>();
			for (Class<?> parameterType : method.getParameterTypes()) {
				parameterTypes.add(parameterType.getName());
			}
			String requestID = String.valueOf(new Random().nextInt(10000));
			InvokeRequest request = new InvokeRequest(requestID, className, method.getName(), parameterTypes.toArray(new String[0]), args);
			RpcConnection connection = null;
			InvokeResponse response = null;
			try {
				connection = getConnection();
				response = connection.sendRequest(request);
			} catch (Throwable t) {
				System.out.println("send rpc request fail");
				throw new RuntimeException(t);
			} finally {
				if (null != connection) {
					// connection.close();
				}
			}
			if (response.getException() != null) {
				throw response.getException();
			} else {
				return response.getResult();
			}
		}
	}
}
