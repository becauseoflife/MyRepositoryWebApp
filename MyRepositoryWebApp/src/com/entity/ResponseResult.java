package com.entity;

/**
 * 
 * @Title: 
 * @Package 
 * @Description: �Զ�����Ӧ���ݽṹ
 * 				��������ṩ���Ż���ios����׿��΢���̳��õ�
 * 				�Ż����ܴ������ݺ���Ҫʹ�ñ���ķ���ת���ɶ��ڵ��������͸�ʽ���࣬����list��
 * 				�������д���
 * 				200����ʾ�ɹ�
 * 				500����ʾ���󣬴�����Ϣ��msg�ֶ���
 * 				501��bean��֤���󣬲��ܶ��ٸ�������map��ʽ����
 * 				502�����������ص��û�token����
 * 				555���쳣�׳���Ϣ
 * @author 
 * @date 
 * @version 
 */
public class ResponseResult {

    // ��Ӧҵ��״̬
    private Integer status;

    // ��Ӧ��Ϣ
    private String msg;

    // ��Ӧ�е�����
    private Object data;
	
    public static ResponseResult build(Integer status, String msg, Object data) {
        return new ResponseResult(status, msg, data);
    }

    public static ResponseResult ok(String msg) {
    	return new ResponseResult(msg);
    }
    
    public static ResponseResult ok(String msg, Object data) {
        return new ResponseResult(msg, data);
    }

    public static ResponseResult ok() {
        return new ResponseResult(null);
    }
    
/*
 * public static JSONResult okAddData(String msg, Object data, Object addData) {
 * return new JSONResult(msg, data, addData); }
 */
    
    public static ResponseResult errorMsg(String msg) {
        return new ResponseResult(500, msg, null);
    }
    
    public static ResponseResult errorMap(Object data) {
        return new ResponseResult(501, "error", data);
    }
    
    public static ResponseResult errorTokenMsg(String msg) {
        return new ResponseResult(502, msg, null);
    }
    
    public static ResponseResult errorException(String msg) {
        return new ResponseResult(555, msg, null);
    }

    public ResponseResult() {

    }

    public ResponseResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(String msg) {
    	this.status = 200;
        this.msg = msg;
        this.data = null;
    }
    

    public ResponseResult(String msg ,Object data) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
