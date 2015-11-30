/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine.mmp.request;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dirk Franssen
 */
@XmlRootElement(name = "cp")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPRequest {
    
    @XmlAttribute private final String version = "3.0";
    @XmlAttribute private final String locale = "nl_BE";
    @XmlAttribute private final String timezone;
    @XmlAttribute private final String clientVersion = "1.1";
    @XmlAttribute private final String clientProduct = "eapi";
    
    @XmlElement(name = "startRegistration")
    private MMPRegistration registration;
    
    @XmlElement(name = "verifyRegistration")
    private MMPVerification verification;
    
    private MMPAuthentication authentication;
    
    @XmlElementWrapper(name = "sendMessage")
    @XmlElement(name = "message")
    private List<MMPMessage> messages;
    
    @XmlElementWrapper(name = "statusReport")
    @XmlElement(name = "message")
    private List<MMPMessageId> messageIds;

    public MMPRequest() {
        timezone = getCurrentTimezone();
    }
    
    public MMPRequest(String timezone) {
        this.timezone = timezone;
    }
    
    public void setRegistration(MMPRegistration registration) {
        this.registration = registration;
    }

    public MMPRegistration getRegistration() {
        return registration;
    }
    
    public void setVerification(MMPVerification verification) {
        this.verification = verification;
    }

    public MMPVerification getVerification() {
        return verification;
    }

    public void setAuthentication(MMPAuthentication authentication) {
        this.authentication = authentication;
    }

    public MMPAuthentication getAuthentication() {
        return authentication;
    }
    
    public void setMessage(MMPMessage message) {
        //It seemes that only 1 message can be send at the time, so misusing a collection to have the 'sendMessage' wrapper tag instead of creating a seperate class to achieve this
        this.messages = new ArrayList<>();
        messages.add(message);
    }

    public MMPMessage getMessage() {
        return (this.messages == null || this.messages.isEmpty()) ? null : this.messages.get(0);
    }
    
    public List<MMPMessageId> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<MMPMessageId> messageIds) {
        this.messageIds = messageIds;
    }
    
    public String getTimezone() {
        return this.timezone;
    }

    @Override
    public String toString() {
        return "MMPRequest{" + "version=" + version + ", locale=" + locale + ", timezone=" + timezone + ", clientVersion=" + clientVersion + ", clientProduct=" + clientProduct + ", registration=" + registration + ", verification=" + verification + ", authentication=" + authentication + ", messages=" + messages + ", messageIds=" + messageIds + '}';
    }

    protected String getCurrentTimezone() {
        String offset = ZonedDateTime.now().getOffset().getId();
        String result = "UTC";
        if (offset != null && offset.length() > 2) {
            result += offset.substring(0, 1);
            result += offset.substring(2, 3);
        }
        return result;
    }
}