package cn.zzuli.cloud.commons.enums;

public enum ErrorEnum {
	ParamError(401000,"参数错误"),
	
	AppNotFound(401101,"应用不存在"),	AppSetPhotoError(401102,"设置应用图标失败"),
	AppCreateError(401103,"应用创建失败"),	AppNameExist(401104,"应用名称已存在"),
	AppModifyError(401105,"修改应用出错"),AppValidError(401106,"禁用启用应用失败"),
	AppRemoteCreateError(401107,"调用应用中心注册失败"),	AppRemoteModifyError(401108,"调用应用中心修改失败"),
	AppRemoteValidError(401109,"调用应用中心启用失败"),	AppRemoteInvalidError(401110,"调用应用中心禁用失败"),
	AppRemoteSetPhotoError(401111,"调用应用中心设置图标失败"),AppSetRangeError(401112,"设置应用可见范围失败"),
	AppDeleteRangeError(401113,"删除应用可见范围失败"),AppGetRangeError(401114,"获取应用可见范围失败"),
	
	ColumnNotFound(401201,"频道不存在"),	ColumnCreateError(401202,"频道创建失败"),
	ColumnModifyError(401203,"修改频道失败"),ColumnNameExist(401204,"频道名称已存在"),
	ColumnSetRangeError(401205,"设置频道可见范围失败"),ColumnGetRangeError(401206,"删除频道可见范围失败"),
	ColumnSetPublishError(401207,"设置频道发文员失败"),ColumnGetPublishError(401208,"获取频道发文员失败"),
	
	NewsNotFound(401301,"新闻不存在"),	ArticleCreateError(401302,"保存新闻草稿失败"),
	NewsPublishError(401303,"发布新闻失败"),NewsDeleteError(401304,"删除新闻失败"),
	NewsToHtmlError(401305,"新闻转换失败"),
	
	FileUploadError(401401,"文件上传失败"),	ImageUploadError(401402,"图片上传失败"),
	FileNotFound(401403,"文件不存在"),ImageNotFound(401404,"图片不存在");


    private Integer code;

    private String desc;// 描述

    private ErrorEnum(Integer code, String desc) {
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
