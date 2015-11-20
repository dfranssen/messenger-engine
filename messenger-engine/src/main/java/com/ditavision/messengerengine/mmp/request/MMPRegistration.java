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
@XmlRootElement(name = "startRegistration")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPRegistration {
    @XmlAttribute private String msisdn;
    @XmlAttribute private String email;

    public MMPRegistration() {
    }

    public MMPRegistration(String msisdn, String email) {
        this.msisdn = msisdn;
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MMPRegistration{" + "msisdn=" + msisdn + ", email=" + email + '}';
    }
}