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