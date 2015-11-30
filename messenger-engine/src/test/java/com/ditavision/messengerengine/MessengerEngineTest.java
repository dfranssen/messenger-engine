/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import com.ditavision.messengerengine.mmp.request.MMPRequest;
import com.ditavision.messengerengine.mmp.response.MMPResponse;
import com.ditavision.messengerengine.mmp.response.MMPStatusReportDetail;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author dfranssen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessengerEngine.class, MessengerEngineTest.OverloadedMessengerEngine.class, ClientBuilder.class, Logger.class})
public class MessengerEngineTest {

    MessengerEngine cut;
    Client mockClient;
    Logger mockLogger;
    Configuration mockConfiguration = mock(Configuration.class);
    
    @Before
    public void setup() {
        ClientBuilder mockBuilder = mock(ClientBuilder.class);
        mockClient = mock(Client.class);
        PowerMockito.mockStatic(ClientBuilder.class);
        when(ClientBuilder.newBuilder()).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockClient);
        mockLogger = mock(Logger.class);
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(anyString())).thenReturn(mockLogger);
        cut = new MessengerEngine();
    }
    
    @Test
    public void testResponseFilterRegistration() {
        verify(mockClient).register(eq(MediaTypeCorrectionResponseFilter.class));
    }
    
    @Test
    public void testStartRegistration() throws Exception {
        prepareFakeCall();
        
        cut.startRegistration("http://test", "myUser", "myEmail");
        
        ArgumentCaptor<MMPRequest> argument = ArgumentCaptor.forClass(MMPRequest.class);
        verify(mockLogger).log(anyObject(), eq("request payload: {0}"), argument.capture());
        MMPRequest actual = argument.getValue();
        assertThat(actual.getRegistration().getMsisdn(), is("myUser"));
        assertThat(actual.getRegistration().getEmail(), is("myEmail"));
    }
    
    @Test
    public void testVerifyRegistration() throws Exception {
        MMPResponse mockMMPResponse = prepareFakeCall();
        when(mockMMPResponse.getPassword()).thenReturn("myPassword");
        
        String actualPwd = cut.verifyRegistration("http://test", "myUser", "myPincode");
        assertThat(actualPwd, is("myPassword"));
        
        ArgumentCaptor<MMPRequest> argument = ArgumentCaptor.forClass(MMPRequest.class);
        verify(mockLogger).log(anyObject(), eq("request payload: {0}"), argument.capture());
        MMPRequest actual = argument.getValue();
        assertThat(actual.getVerification().getMsisdn(), is("myUser"));
        assertThat(actual.getVerification().getPincode(), is("myPincode"));
    }
    
    @Test
    public void testSendMessageFromConfig() throws Exception {
        when(mockConfiguration.getUrl()).thenReturn("http://config");
        when(mockConfiguration.getMsisdn()).thenReturn("myConfigMsisdn");
        when(mockConfiguration.getPassword()).thenReturn("myConfigPwd");
        new OverloadedMessengerEngine(mockConfiguration).sendMessage("myMessage", Arrays.asList("to1"));
    }
    
    @Test
    public void testSendMessage() throws Exception {
        MMPResponse mockMMPResponse= prepareFakeCall();
        when(mockMMPResponse.getMessageId()).thenReturn("myMessageId");
        
        String actualMsgId = cut.sendMessage("http://test", "myUser", "myPassword", "myMessage", Arrays.asList("to1", "to2"));
        assertThat(actualMsgId, is("myMessageId"));
        
        ArgumentCaptor<MMPRequest> argument = ArgumentCaptor.forClass(MMPRequest.class);
        verify(mockLogger).log(anyObject(), eq("request payload: {0}"), argument.capture());
        MMPRequest actual = argument.getValue();
        assertThat(actual.getAuthentication().getUsername(), is("myUser"));
        assertThat(actual.getAuthentication().getPassword(), is("myPassword"));
        assertThat(actual.getMessage().getText(), is("myMessage"));
        assertThat(actual.getMessage().getRecipients().get(0).getMsisdn(), is("to1"));
        assertThat(actual.getMessage().getRecipients().get(1).getMsisdn(), is("to2"));
    }
    
    @Test
    public void testStatusReportsFromConfig() throws Exception {
        when(mockConfiguration.getUrl()).thenReturn("http://config");
        when(mockConfiguration.getMsisdn()).thenReturn("myConfigMsisdn");
        when(mockConfiguration.getPassword()).thenReturn("myConfigPwd");
        new OverloadedMessengerEngine(mockConfiguration).statusReports(Arrays.asList("id1"));
    }
    
    @Test
    public void testStatusReports() throws Exception {
        MMPResponse mockMMPResponse= prepareFakeCall();
        List<MMPStatusReportDetail> mockList = mock(List.class);
        when(mockMMPResponse.getStatusReports()).thenReturn(mockList);
        
        List<MMPStatusReportDetail> actualReports = cut.statusReports("http://test", "myUser", "myPassword", Arrays.asList("id1", "id2"));
        assertThat(actualReports, is(mockList));
        
        ArgumentCaptor<MMPRequest> argument = ArgumentCaptor.forClass(MMPRequest.class);
        verify(mockLogger).log(anyObject(), eq("request payload: {0}"), argument.capture());
        MMPRequest actual = argument.getValue();
        assertThat(actual.getAuthentication().getUsername(), is("myUser"));
        assertThat(actual.getAuthentication().getPassword(), is("myPassword"));
        assertThat(actual.getMessageIds().get(0).getId(), is("id1"));
        assertThat(actual.getMessageIds().get(1).getId(), is("id2"));
    }
    
    @Test(expected = MessengerEngineException.class)
    public void testVerifyAndReadNull() {
       cut.verifyAndReadResponse(null);
    }
    
    @Test(expected = MessengerEngineException.class)
    public void testVerifyAndReadResponseNOK() {
       Response mockResponse = mock(Response.class);
       when(mockResponse.getStatus()).thenReturn(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
       cut.verifyAndReadResponse(mockResponse);
    }
    
    @Test(expected = MessengerEngineException.class)
    public void testVerifyAndReadResponseNullEntity() {
       Response mockResponse = mock(Response.class);
       when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
       when(mockResponse.readEntity(eq(MMPResponse.class))).thenReturn(null);
       cut.verifyAndReadResponse(mockResponse);
    }
    
    @Test(expected = MessengerEngineException.class)
    public void testVerifyAndReadResponseResultCodeNOK() {
       Response mockResponse = mock(Response.class);
       when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
       MMPResponse mockMMPResponse = mock(MMPResponse.class);
       when(mockMMPResponse.getCode()).thenReturn("0");
       when(mockResponse.readEntity(eq(MMPResponse.class))).thenReturn(mockMMPResponse);
       cut.verifyAndReadResponse(mockResponse);
    }
    
    @Test
    public void testVerifyAndReadResponseOK() {
       Response mockResponse = mock(Response.class);
       when(mockResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
       MMPResponse mockMMPResponse = mock(MMPResponse.class);
       when(mockMMPResponse.getCode()).thenReturn("100");
       when(mockResponse.readEntity(eq(MMPResponse.class))).thenReturn(mockMMPResponse);
       assertThat(cut.verifyAndReadResponse(mockResponse), is(mockMMPResponse));
    }
    
    protected MMPResponse prepareFakeCall() {
        return prepareFakeCall("http://test", 200, "100");
    }
    protected MMPResponse prepareFakeCall(String url, int responseCode, String mmpResponseCode) {
        WebTarget mockWebTarget = mock(WebTarget.class);
        when(mockClient.target(eq(url))).thenReturn(mockWebTarget);
        Invocation.Builder mockInvocationBuilder = mock(Invocation.Builder.class);
        when(mockWebTarget.request(eq(MediaType.APPLICATION_XML_TYPE))).thenReturn(mockInvocationBuilder);
        Response mockResponse = mock(Response.class);
        when(mockInvocationBuilder.post(anyObject())).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(responseCode);
        MMPResponse mockMMPResponse = mock(MMPResponse.class);
        when(mockMMPResponse.getCode()).thenReturn(mmpResponseCode);
        when(mockResponse.readEntity(eq(MMPResponse.class))).thenReturn(mockMMPResponse);
        return mockMMPResponse;
    }
    
    protected class OverloadedMessengerEngine extends MessengerEngine {

        public OverloadedMessengerEngine() {
        }
        
        public OverloadedMessengerEngine(Configuration config) {
            this.config = config;
        }
        
        @Override
        public String sendMessage(String mmpUrl, String senderPhoneNbr, String password, String message, List<String> recipients) {
            assertThat(mmpUrl, is("http://config"));
            assertThat(senderPhoneNbr, is("myConfigMsisdn"));
            assertThat(password, is("myConfigPwd"));
            assertThat(message, is("myMessage"));
            assertThat(recipients.get(0), is("to1"));
            return null;
        }

        @Override
        public List<MMPStatusReportDetail> statusReports(String mmpUrl, String senderPhoneNbr, String password, List<String> messageIds) {
            assertThat(mmpUrl, is("http://config"));
            assertThat(senderPhoneNbr, is("myConfigMsisdn"));
            assertThat(password, is("myConfigPwd"));
            assertThat(messageIds.get(0), is("id1"));
            return null;
        }
    }
}
