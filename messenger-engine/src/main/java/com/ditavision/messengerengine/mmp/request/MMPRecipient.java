/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine.mmp.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Dirk Franssen
 */
@XmlRootElement(name = "recipient")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPRecipient {
    @XmlAttribute private final String type = "to";
    @XmlAttribute private final String addressType = "msisdn";
    
    @XmlValue
    private String msisdn;

    public MMPRecipient() {
    }

    public MMPRecipient(String msisdn) {
        this.setMsisdn(msisdn);
    }

    public String getMsisdn() {
        return msisdn;
    }

    public final void setMsisdn(String msisdn) {
        if (msisdn != null && msisdn.startsWith("+")) {
            //specs define it should strip the "+"
            this.msisdn = msisdn.substring(1);
        } else {
            this.msisdn = msisdn;
        }
    }

    @Override
    public String toString() {
        return "MMPRecipient{" + "type=" + type + ", addressType=" + addressType + ", msisdn=" + msisdn + '}';
    }
}