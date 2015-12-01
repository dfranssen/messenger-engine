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