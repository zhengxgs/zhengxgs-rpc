package com.zhengxgs.rpc.client;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public interface RpcClient {

    public <T> T proxy(Class<T> interfaceClass);

}
