package com.zhengxgs.rpc.result;

import java.io.Serializable;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class InvokeResponse implements Serializable {

	public Object result;
	private String requestID;
	private Throwable exception;

	public InvokeResponse() {
	}

	public InvokeResponse(String requestID) {
		this.requestID = requestID;
	}

	public InvokeResponse(Object result, String requestID, Throwable exception) {
		this.result = result;
		this.requestID = requestID;
		this.exception = exception;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
