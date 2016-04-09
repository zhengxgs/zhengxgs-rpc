package com.zhengxgs.rpc.server;

import com.zhengxgs.rpc.result.InvokeRequest;
import com.zhengxgs.rpc.result.InvokeResponse;
import com.zhengxgs.rpc.utils.HandlerMapper;
import com.zhengxgs.rpc.utils.ReflectionCache;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class BIORpcServer implements RpcServer {

	private Integer port;
	private Map<String, Object> handlers = new HashMap<>();
	private ServerSocket server;

	private ExecutorService executor = Executors.newCachedThreadPool();

	public BIORpcServer(Integer port, Object... handlers) {
		this.port = port;
		this.handlers = HandlerMapper.getHandlerMap(handlers);
	}

	public void start() {
		try {
			server = new ServerSocket();
			InetSocketAddress socketAddress = new InetSocketAddress(port);
			server.bind(socketAddress);
			while (true) {
				Socket socket = server.accept();
				executor.submit(new BIORpcServiceWorker(socket, handlers));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			executor.shutdown();
		}
	}

	public void stop() {

	}

	class BIORpcServiceWorker implements Runnable {

		public Socket socket;
		private Map<String, Object> handlers;

		public BIORpcServiceWorker(Socket socket, Map<String, Object> handlers) {
			this.socket = socket;
			this.handlers = handlers;
		}

		private Object handle(InvokeRequest request)
				throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
			Method method = ReflectionCache.getMethod(request.getClassName(), request.getMethodName(), request.getParameterTypes());
			Object[] parameters = request.getParameters();
			Object handler = handlers.get(request.getClassName());
			if (handler != null) {
				Object result = method.invoke(handler, parameters);
				return result;
			}
			throw new RuntimeException("no provider");
		}

		public void run() {
			InvokeRequest request = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = new BufferedInputStream(socket.getInputStream());
				outputStream = new BufferedOutputStream(socket.getOutputStream());
				while (socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown()) {
					try {
						request = decode(inputStream);
					} catch (Exception e) {
						break;
					}
					InvokeResponse response = new InvokeResponse(request.getRequestID());
					try {
						Object result = handle(request);
						response.setResult(result);
					} catch (Exception e) {
						response.setException(e);
						System.out.println("handle rpc request fail");
						e.printStackTrace();
					}
					encode(outputStream, response);
					outputStream.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					outputStream.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * decode
		 * @param inputStream
		 */
		public InvokeRequest decode(InputStream inputStream) throws Exception {
			try {
				ObjectInputStream input = new ObjectInputStream(inputStream);
				String requestID = input.readUTF();
				String className = input.readUTF();
				String methodName = input.readUTF();
				String[] parameterTypes = (String[]) input.readObject();
				Object[] parameters = (Object[]) input.readObject();
				return new InvokeRequest(requestID, className, methodName, parameterTypes, parameters);
			} catch (Exception e) {
				throw new Exception("BIORpcServer decode error!");
			}
		}

		/**
		 * encode
		 * @param outputStream
		 * @param result
		 * @throws IOException
		 */
		public void encode(OutputStream outputStream, InvokeResponse result) throws IOException {
			ObjectOutputStream objOut = new ObjectOutputStream(outputStream);
			objOut.writeObject(result);
		}
	}

}
