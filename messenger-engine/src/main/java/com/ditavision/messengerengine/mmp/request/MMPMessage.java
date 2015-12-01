/*
 * Copyright 2015 Dirk Franssen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
