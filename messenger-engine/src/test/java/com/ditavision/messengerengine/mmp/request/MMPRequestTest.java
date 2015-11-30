/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine.mmp.request;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dfranssen
 */
public class MMPRequestTest {
    
    private JAXBContext context;
    MMPRequest cut;

    @Before
    public void init() throws JAXBException {
        this.cut = new MMPRequest("UTC+2");
        this.context = JAXBContext.newInstance(MMPRequest.class);
    }
    
    @Test
    public void testTimezone() {
        String actual = cut.getCurrentTimezone();
        assertNotNull(actual);
        //As we do not know in which timezone the code will be executed
        assertTrue(actual.startsWith("UTC"));
    }
    
    @Test
    public void testSerialize() throws JAXBException {
        Writer writer = new StringWriter();
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"/>", writer.toString());
    }
    
    @Test
    public void testSerializeRegistration() throws JAXBException {
        Writer writer = new StringWriter();
        cut.setRegistration(new MMPRegistration("+32495xxxxx1", "test@test.com"));
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"><startRegistration msisdn=\"+32495xxxxx1\" email=\"test@test.com\"/></cp>", writer.toString());
    }
    
    @Test
    public void testSerializeVerification() throws JAXBException {
        Writer writer = new StringWriter();
        cut.setVerification(new MMPVerification("+32495xxxxx1", "3535"));
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"><verifyRegistration msisdn=\"+32495xxxxx1\" pincode=\"3535\"/></cp>", writer.toString());
    }
    
    @Test
    public void testSerializeAuthentication() throws JAXBException {
        Writer writer = new StringWriter();
        cut.setAuthentication(new MMPAuthentication("user", "pwd"));
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"><authentication><username>user</username><password>pwd</password></authentication></cp>", writer.toString());
    }
    
    @Test
    public void testSerializeAuthenticationAndMessage() throws JAXBException {
        Writer writer = new StringWriter();
        cut.setAuthentication(new MMPAuthentication("user", "pwd"));
        List<MMPRecipient> recipients = new ArrayList();
        recipients.add(new MMPRecipient("+32495xxxxx2"));
        recipients.add(new MMPRecipient("+32495xxxxx3"));
        cut.setMessage(new MMPMessage("test message", recipients));
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"><authentication><username>user</username><password>pwd</password></authentication><sendMessage><message type=\"SMS\" originatorType=\"replytophone\"><text>test message</text><recipients><recipient type=\"to\" addressType=\"msisdn\">32495xxxxx2</recipient><recipient type=\"to\" addressType=\"msisdn\">32495xxxxx3</recipient></recipients></message></sendMessage></cp>", writer.toString());
    }
    
    @Test
    public void testSerializeAuthenticationAndStatusReports() throws JAXBException {
        Writer writer = new StringWriter();
        cut.setAuthentication(new MMPAuthentication("user", "pwd"));
        List<MMPMessageId> ids = new ArrayList();
        ids.add(new MMPMessageId("27066"));
        ids.add(new MMPMessageId("3485"));
        cut.setMessageIds(ids);
        context.createMarshaller().marshal(cut, writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp version=\"3.0\" locale=\"nl_BE\" timezone=\"UTC+2\" clientVersion=\"1.1\" clientProduct=\"eapi\"><authentication><username>user</username><password>pwd</password></authentication><statusReport><message messageId=\"27066\"/><message messageId=\"3485\"/></statusReport></cp>", writer.toString());
    }
}