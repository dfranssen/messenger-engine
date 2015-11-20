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
@XmlRootElement(name = "statusReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPStatusReportDetail {
    @XmlAttribute private String messageId;
    
    @XmlElement(name = "recipient")
    private List<MMPRecipientStatus> recipients;

    public MMPStatusReportDetail() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<MMPRecipientStatus> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<MMPRecipientStatus> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "MMPStatusReportDetail{" + "messageId=" + messageId + ", recipients=" + recipients + '}';
    }
}