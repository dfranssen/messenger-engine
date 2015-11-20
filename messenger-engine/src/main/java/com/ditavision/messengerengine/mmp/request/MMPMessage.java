/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine.mmp.request;

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
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class MMPMessage {
    @XmlAttribute private final String type = "SMS";
    @XmlAttribute private final String originatorType = "replytophone";
    
    private String text;
    
    @XmlElementWrapper(name = "recipients")
    @XmlElement(name = "recipient")
    private List<MMPRecipient> recipients;

    public MMPMessage() {
    }

    public MMPMessage(String text, List<MMPRecipient> recipients) {
        this.text = text;
        this.recipients = recipients;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MMPRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<MMPRecipient> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "MMPMessage{" + "type=" + type + ", originatorType=" + originatorType + ", text=" + text + ", recipients=" + recipients + '}';
    }
}
