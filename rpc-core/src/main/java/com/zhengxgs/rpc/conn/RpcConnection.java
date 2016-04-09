package com.zhengxgs.rpc.conn;

import com.zhengxgs.rpc.result.InvokeRequest;
import com.zhengxgs.rpc.result.InvokeResponse;

import java.io.IOException;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public interface RpcConnection {

	void connect() throws IOException;

	void close();

	InvokeResponse sendRequest(InvokeRequest request) throws IOException;

}
