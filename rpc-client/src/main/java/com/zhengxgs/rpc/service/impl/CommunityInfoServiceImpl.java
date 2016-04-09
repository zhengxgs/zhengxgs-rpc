package com.zhengxgs.rpc.service.impl;

import com.zhengxgs.rpc.service.ICommunityInfoService;

/**
 * Created by zhengxgs on 2016/4/9.
 */
public class CommunityInfoServiceImpl implements ICommunityInfoService {

	@Override
	public void queryCommunityInfo(Integer id) {
		System.out.println("hello world number : " + id);
	}
}
