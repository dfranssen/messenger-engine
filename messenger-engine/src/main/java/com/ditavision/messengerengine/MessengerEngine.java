/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author dfranssen
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
    
    public void startRegistration(String mmpUrl, String senderPhoneNbr, String senderEmail) {
        MMPRequest request = new MMPRequest();
        request.setRegistration(new MMPRegistration(senderPhoneNbr, senderEmail));
        sendToMMP(mmpUrl, request);
    }
    
    public String verifyRegistration(String mmpUrl, String senderPhoneNbr, String pinCode) {
        MMPRequest request = new MMPRequest();
        request.setVerification(new MMPVerification(senderPhoneNbr, pinCode));
        MMPResponse response = sendToMMP(mmpUrl, request);
        return response.getPassword();
    }
    
    public String sendMessage(String message, List<String> recipients) {
        return sendMessage(config.getUrl(), config.getMsisdn(), config.getPassword(), message, recipients);
    }
    
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
    
    public List<MMPStatusReportDetail> statusReports(List<String> messageIds) {
        return statusReports(config.getUrl(), config.getMsisdn(), config.getPassword(), messageIds);
    }
    
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