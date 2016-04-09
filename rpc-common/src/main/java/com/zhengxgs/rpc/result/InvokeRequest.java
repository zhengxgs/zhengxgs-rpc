package com.zhengxgs.rpc.result;

import java.io.Serializable;

/**
 * request obj
 * Created by zhengxgs on 2016/4/8.
 */
public class InvokeRequest implements Serializable {
	private String requestID;
	private String className;
	private String methodName;
	private String[] parameterTypes;
	private Object[] parameters;

	public InvokeRequest() {
	}

	public InvokeRequest(String requestID, String className, String methodName, String[] parameterTypes, Object[] parameters) {
		this.requestID = requestID;
		this.className = className;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
}
