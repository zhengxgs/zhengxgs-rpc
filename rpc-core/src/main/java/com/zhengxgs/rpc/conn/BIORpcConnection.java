package com.zhengxgs.rpc.conn;

import com.zhengxgs.rpc.result.InvokeRequest;
import com.zhengxgs.rpc.result.InvokeResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by zhengxgs on 2016/4/9.
 */
public class BIORpcConnection implements RpcConnection {

	private InetSocketAddress inetAddr;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private boolean connected;

	public BIORpcConnection(String host, Integer port) {
		this.inetAddr = new InetSocketAddress(host, port);
	}

	public void connect() throws IOException {
		if (connected) {
			return;
		}
		socket = new Socket();
		socket.connect(inetAddr);
		in = new BufferedInputStream(socket.getInputStream());
		out = new BufferedOutputStream(socket.getOutputStream());
		connected = true;
	}

	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InvokeResponse sendRequest(InvokeRequest request) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(16384);
		ObjectOutputStream objOut = new ObjectOutputStream(baos);
		objOut.writeUTF(request.getRequestID());
		objOut.writeUTF(request.getClassName());
		objOut.writeUTF(request.getMethodName());
		objOut.writeObject(request.getParameterTypes());
		objOut.writeObject(request.getParameters());
		byte[] bytes = baos.toByteArray();
		out.write(bytes);
		out.flush();
		try {
			ObjectInputStream input = new ObjectInputStream(in);
			return (InvokeResponse) input.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
