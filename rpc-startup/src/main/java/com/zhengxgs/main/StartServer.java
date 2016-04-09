package com.zhengxgs.main;

import com.zhengxgs.rpc.server.BIORpcServer;
import com.zhengxgs.rpc.server.RpcServer;
import com.zhengxgs.rpc.service.IDemoService;
import com.zhengxgs.rpc.service.impl.DemoServiceImpl;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class StartServer {

	public static void main(String[] args) {

		// service....
		IDemoService demo = new DemoServiceImpl();
		RpcServer rpcServer = new BIORpcServer(8000, new Object[] { demo });
        rpcServer.start();
    }
}
