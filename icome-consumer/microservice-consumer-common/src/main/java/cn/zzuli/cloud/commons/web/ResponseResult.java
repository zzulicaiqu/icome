package cn.zzuli.cloud.commons.web;

/**
 * 返回信息基类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class ResponseResult {

    private boolean success;
    private String errMsg;
    private Object data;
    private long totalCount;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the totalCount
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public ResponseResult() {
        this.success = false;
        this.totalCount = 0;
        this.setData(null);
    }
}
