/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine.mmp.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dirk Franssen
 */
@XmlRootElement(name = "recipient")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPRecipientStatus {
    @XmlAttribute private String msisdn;
    @XmlAttribute private String status;
    @XmlAttribute private String statusId;

    public MMPRecipientStatus() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "MMPRecipientStatus{" + "msisdn=" + msisdn + ", status=" + status + ", statusId=" + statusId + '}';
    }
}
