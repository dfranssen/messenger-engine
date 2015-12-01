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
