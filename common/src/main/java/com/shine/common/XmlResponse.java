package com.shine.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.shine.common.constant.XmlErrorInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-23 00:16
 * fileName：XmlResponse
 * Use：Get请求 错误响应，多用于读取文件
 */
@JacksonXmlRootElement(localName = "ERROR")
public class XmlResponse {

    @JacksonXmlProperty(localName = "INFO")
    public XmlErrorInfo xmlErrorInfo;

    public XmlResponse(XmlErrorInfo xmlErrorInfo) {
        this.xmlErrorInfo = xmlErrorInfo;
    }

    public XmlErrorInfo getXmlErrorInfo() {
        return xmlErrorInfo;
    }

    public void setXmlErrorInfo(XmlErrorInfo xmlErrorInfo) {
        this.xmlErrorInfo = xmlErrorInfo;
    }
}
