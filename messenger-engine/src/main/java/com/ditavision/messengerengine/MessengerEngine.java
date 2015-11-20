/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import com.ditavision.messengerengine.MessengerEngineException;
import com.ditavision.messengerengine.mmp.request.MMPAuthentication;
import com.ditavision.messengerengine.mmp.request.MMPMessage;
import com.ditavision.messengerengine.mmp.request.MMPRequest;
import com.ditavision.messengerengine.mmp.request.MMPRecipient;
import com.ditavision.messengerengine.mmp.request.MMPRegistration;
import com.ditavision.messengerengine.mmp.response.MMPResponse;
import com.ditavision.messengerengine.mmp.request.MMPVerification;
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
    
    private final static Logger LOGGER = Logger.getLogger(MessengerEngine.class.getName());
    
    Configuration config = new Configuration();
    Client client = ClientBuilder.newBuilder().build();
    private static final String MMP_SUCCESSFUL_RESPONSE_CODE = "100";

    public MessengerEngine() {
        client.register(MediaTypeCorrectionResponseFilter.class);
    }
    
    public void startRegistration(String mmpUrl, String senderPhoneNbr, String senderEmail) {
        MMPRequest request = new MMPRequest.Builder().build();
        request.setRegistration(new MMPRegistration(senderPhoneNbr, senderEmail));
        sendToMMP(mmpUrl, request);
    }
    
    public String verifyRegistration(String mmpUrl, String senderPhoneNbr, String pinCode) {
        MMPRequest request = new MMPRequest.Builder().build();
        request.setVerification(new MMPVerification(senderPhoneNbr, pinCode));
        MMPResponse response = sendToMMP(mmpUrl, request);
        //TODO: sms those details to the user, save them somewhere, or ...
        return response.getPassword();
    }
    
    public void sendMessage(String message, List<String> recipients) {
        sendMessage(config.getUrl(), config.getMsisdn(), config.getPassword(), message, recipients);
    }
    
    public void sendMessage(String mmpUrl, String senderPhoneNbr, String password, String message, List<String> recipients) {
        MMPRequest request = new MMPRequest.Builder().build();
        MMPAuthentication auth = new MMPAuthentication(senderPhoneNbr, password);
        List<MMPRecipient> toList = Optional.ofNullable(recipients).orElse(new ArrayList<>()).
                stream().
                map(s -> new MMPRecipient(s)).
                collect(Collectors.toList());
        MMPMessage text = new MMPMessage(message, toList);
        request.setAuthentication(auth);
        request.setMessage(text);
        sendToMMP(mmpUrl, request);
    }
    
    protected MMPResponse sendToMMP(String url, MMPRequest payload) {
        LOGGER.log(Level.INFO, "request payload: {0}", payload);
        Response response = client.target(url).
                request().
                accept(MediaType.APPLICATION_XML_TYPE).
                post(Entity.xml(payload));
        MMPResponse result = verifyAndReadResponse(response);
        LOGGER.log(Level.INFO, "response payload: {0}", result);
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