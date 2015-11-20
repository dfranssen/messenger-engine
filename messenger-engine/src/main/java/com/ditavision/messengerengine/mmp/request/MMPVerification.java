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

/**
 *
 * @author Dirk Franssen
 */
@XmlRootElement(name = "verifyRegistration")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPVerification {
    @XmlAttribute private String msisdn;
    @XmlAttribute private String pincode;

    public MMPVerification() {
    }

    public MMPVerification(String msisdn, String pincode) {
        this.msisdn = msisdn;
        this.pincode = pincode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "MMPVerification{" + "msisdn=" + msisdn + ", pincode=" + pincode + '}';
    }
}
