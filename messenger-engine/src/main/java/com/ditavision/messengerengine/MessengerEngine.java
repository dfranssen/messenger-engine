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
package com.ditavision.messengerengine;

import com.ditavision.messengerengine.mmp.request.MMPAuthentication;
import com.ditavision.messengerengine.mmp.request.MMPMessage;
import com.ditavision.messengerengine.mmp.request.MMPMessageId;
import com.ditavision.messengerengine.mmp.request.MMPRequest;
import com.ditavision.messengerengine.mmp.request.MMPRecipient;
import com.ditavision.messengerengine.mmp.request.MMPRegistration;
import com.ditavision.messengerengine.mmp.response.MMPResponse;
import com.ditavision.messengerengine.mmp.request.MMPVerification;
import com.ditavision.messengerengine.mmp.response.MMPStatusReportDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Messenger Engine responsible for interacting with an MMP server.
 * @see Configuration
 * 
 * @author Dirk Franssen
 */
public class MessengerEngine {
    
    private Logger LOGGER = Logger.getLogger(MessengerEngine.class.getName());
    
    Configuration config;
    Client client = ClientBuilder.newBuilder().build();
    private static final String MMP_SUCCESSFUL_RESPONSE_CODE = "100";

    public MessengerEngine() {
        client.register(MediaTypeCorrectionResponseFilter.class);
        config = new Configuration();
    }
    
    /**
     * Initiate a registration with an MMP server.
     * 
     * @param mmpUrl the url of the MMP server
     * @param senderPhoneNbr the msisdn of the user for which the registration is intended
     * @param senderEmail  the email address of the user for which the registration is intended. (Future use when support for 'replytoinbox')
     */
    public void startRegistration(String mmpUrl, String senderPhoneNbr, String senderEmail) {
        MMPRequest request = new MMPRequest();
        request.setRegistration(new MMPRegistration(senderPhoneNbr, senderEmail));
        sendToMMP(mmpUrl, request);
    }
    
    /**
     * Verify the received pincode with the MMP server to complete the registration.
     * 
     * @param mmpUrl the url of the MMP server
     * @param senderPhoneNbr the msisdn of the user for which the registration was intended
     * @param pinCode the pincode received by SMS for the msisdn that was used during the registration initialization.
     * @return the password that should be used for authenticating with the MMP server in subsequent calls
     */
    public String verifyRegistration(String mmpUrl, String senderPhoneNbr, String pinCode) {
        MMPRequest request = new MMPRequest();
        request.setVerification(new MMPVerification(senderPhoneNbr, pinCode));
        MMPResponse response = sendToMMP(mmpUrl, request);
        return response.getPassword();
    }
    
    /**
     * Send a message to a list of recipients with the settings of {@link Configuration}.
     * 
     * @see #sendMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List) 
     * @param message the message to send
     * @param recipients list of recipients to whom the message should be send
     * @return the message id which can be used to request a status report
     */
    public String sendMessage(String message, List<String> recipients) {
        return sendMessage(config.getUrl(), config.getMsisdn(), config.getPassword(), message, recipients);
    }
    
    /**
     * Send a message to a list of recipients.
     * 
     * @param mmpUrl the url of the MMP server
     * @param senderPhoneNbr the msisdn of the registered user
     * @param password the password received during registration
     * @param message the message to send
     * @param recipients list of recipients to whom the message should be send
     * @return the message id which can be used to request a status report 
     */
    public String sendMessage(String mmpUrl, String senderPhoneNbr, String password, String message, List<String> recipients) {
        MMPRequest request = new MMPRequest();
        MMPAuthentication auth = new MMPAuthentication(senderPhoneNbr, password);
        List<MMPRecipient> toList = Optional.ofNullable(recipients).orElse(new ArrayList<>()).
                stream().
                map(s -> new MMPRecipient(s)).
                collect(Collectors.toList());
        MMPMessage text = new MMPMessage(message, toList);
        request.setAuthentication(auth);
        request.setMessage(text);
        MMPResponse sendToMMP = sendToMMP(mmpUrl, request);
        return sendToMMP.getMessageId();
    }
    
    /**
     * Request a status report for one or more message IDs with the settings of {@link Configuration}.
     * @see #statusReports(java.lang.String, java.lang.String, java.lang.String, java.util.List) 
     * 
     * @param messageIds a list of message IDs for which a status report will be requested
     * @return a list of status reports, one for each requested message ID that was found for the registered user
     */
    public List<MMPStatusReportDetail> statusReports(List<String> messageIds) {
        return statusReports(config.getUrl(), config.getMsisdn(), config.getPassword(), messageIds);
    }
    
    /**
     * Request a status report for one or more message IDs.
     * 
     * @param mmpUrl the url of the MMP server
     * @param senderPhoneNbr the msisdn of the registered user
     * @param password the password received during registration
     * @param messageIds a list of message IDs for which a status report will be requested
     * @return a list of status reports, one for each requested message ID that was found for the registered user 
     */
    public List<MMPStatusReportDetail> statusReports(String mmpUrl, String senderPhoneNbr, String password, List<String> messageIds) {
        MMPRequest request = new MMPRequest();
        MMPAuthentication auth = new MMPAuthentication(senderPhoneNbr, password);
        List<MMPMessageId> messageIdList = Optional.ofNullable(messageIds).orElse(new ArrayList<>()).
                stream().
                map(s -> new MMPMessageId(s)).
                collect(Collectors.toList());
        request.setAuthentication(auth);
        request.setMessageIds(messageIdList);
        MMPResponse sendToMMP = sendToMMP(mmpUrl, request);
        return sendToMMP.getStatusReports();
    }
    
    protected MMPResponse sendToMMP(String url, MMPRequest payload) {
        LOGGER.log(Level.FINE, "request payload: {0}", payload);
        Response response = client.target(url).
                request(MediaType.APPLICATION_XML_TYPE).
                post(Entity.xml(payload));
        MMPResponse result = verifyAndReadResponse(response);
        LOGGER.log(Level.FINE, "response payload: {0}", result);
        return result;
        
    }
    
    protected MMPResponse verifyAndReadResponse(Response response) {
        if (response == null || response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new MessengerEngineException(
                    response == null ? 
                            Response.Status.NOT_FOUND.getStatusCode() + "" : 
                            response.getStatus() + "",
                    "Unexpected HTTP response code returned by the MMP server.");
        }
        MMPResponse result = Optional.ofNullable(response.readEntity(MMPResponse.class)).orElseThrow(() -> {
            return new MessengerEngineException(Response.Status.NOT_FOUND.getStatusCode() + "", "The response has no payload which is unexpected");
        });
        
        if (!MMP_SUCCESSFUL_RESPONSE_CODE.equals(result.getCode())) {
            throw new MessengerEngineException("MMP-" + result.getCode(), result.getDescription());
        }
        
        return result;
    }
}