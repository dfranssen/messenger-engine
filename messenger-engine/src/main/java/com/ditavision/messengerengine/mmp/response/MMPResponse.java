/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine.mmp.response;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dirk Franssen
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPResponse {
    @XmlAttribute private String code;
    @XmlAttribute private String description;
    
    private String permanentKey;
    private String password;
    private String replyToInbox;
    
    @XmlElement(name = "message")
    private String messageId;
    
    @XmlElement(name = "statusReport")
    private List<MMPStatusReportDetail> statusReports;
    
    public MMPResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermanentKey() {
        return permanentKey;
    }

    public void setPermanentKey(String permanentKey) {
        this.permanentKey = permanentKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReplyToInbox() {
        return replyToInbox;
    }

    public void setReplyToInbox(String replyToInbox) {
        this.replyToInbox = replyToInbox;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<MMPStatusReportDetail> getStatusReports() {
        return statusReports;
    }

    public void setStatusReports(List<MMPStatusReportDetail> statusReports) {
        this.statusReports = statusReports;
    }
}
