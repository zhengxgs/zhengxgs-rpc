package com.zhengxgs.main;

import com.zhengxgs.rpc.client.GodRpcClient;
import com.zhengxgs.rpc.client.RpcClient;
import com.zhengxgs.rpc.conn.BIORpcConnection;
import com.zhengxgs.rpc.conn.RpcConnection;
import com.zhengxgs.rpc.service.IDemoService;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class StartClient {

	public static void main(String[] args) {

		RpcConnection rpcConnection = new BIORpcConnection("127.0.0.1", 8000);
		RpcClient rpcClient = new GodRpcClient(rpcConnection);

		IDemoService demoService = rpcClient.proxy(IDemoService.class);
        System.out.println(demoService.sayHi());
        rpcConnection.close();
	}
}
