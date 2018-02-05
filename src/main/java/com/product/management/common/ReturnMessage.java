package com.product.management.common;

public class ReturnMessage {

	private int msgCode;
	private String msgDetail;

	public ReturnMessage(int msgCode, String msgDetail) {
		this.msgCode = msgCode;
		this.msgDetail = msgDetail;
	}

	public int getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgDetail() {
		return msgDetail;
	}

	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}
	
	
}
