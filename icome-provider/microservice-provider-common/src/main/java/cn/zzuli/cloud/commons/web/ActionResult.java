package cn.zzuli.cloud.commons.web;


import java.io.Serializable;

import cn.zzuli.cloud.commons.enums.ResultEnum;

/**
 * @param <T>
 * @author hanyu
 * @Description: ActionResult
 * @date 2017-12-25 13:01:42
 */
public class ActionResult<T> implements Serializable {

    private static final long serialVersionUID = 1772055640075900959L;

    private int code = ResultEnum.OK.getCode();
    private String message = ResultEnum.OK.getDesc();
    private String reqUrl;
    private T data;

    public ActionResult() {
        super();
    }

    public ActionResult(T data) {
        super();
        this.data = data;
    }

    public ActionResult(ResultEnum actionResultEnum) {
        super();
        this.code = actionResultEnum.getCode();
        this.message = actionResultEnum.getDesc();
    }

    public ActionResult(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ActionResult(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ActionResult(ResultEnum actionResultEnum, T data) {
        super();
        this.code = actionResultEnum.getCode();
        this.message = actionResultEnum.getDesc();
        this.data = data;
    }

    public ActionResult(ResultEnum codeEnum, String message, T data) {
        super();
        this.code = codeEnum.getCode();
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }
    
    public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
    public String toString() {
        return "ActionResult [code=" + code + ", message=" + message + ", reqUrl=" + reqUrl + ", result=" + data + "]";
    }
}
