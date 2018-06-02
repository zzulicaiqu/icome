package cn.zzuli.cloud.commons.enums;

/**
 * @author hanyu
 * @Description: webApi返回给app的code
 * @date 2017-12-25 13:04:39
 */
public enum ResultEnum {
    OK(200, "成功");


    private Integer code;

    private String desc;// 描述

    private ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getStrCode() {
        return code.toString();
    }
    }
