/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine.mmp.response;

import java.io.StringReader;
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
public class MMPResponseTest {
    
    private JAXBContext context;

    @Before
    public void init() throws JAXBException {
        this.context = JAXBContext.newInstance(MMPResponse.class);
    }

    @Test
    public void testUnmarshallStartRegistrationResponse() throws JAXBException {
        MMPResponse actual = (MMPResponse) context.createUnmarshaller().unmarshal(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response code=\"100\" description=\"description\" />"));
        assertEquals("100", actual.getCode());
        assertEquals("description", actual.getDescription());
    }
    
    @Test
    public void testUnmarshallVerifyRegistrationResponse() throws JAXBException {
        MMPResponse actual = (MMPResponse) context.createUnmarshaller().unmarshal(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response code=\"100\"><permanentKey>6464654651654654</permanentKey><password>321351351351351354</password><replyToInbox>468798498419</replyToInbox></response>"));
        assertEquals("100", actual.getCode());
        assertEquals("6464654651654654", actual.getPermanentKey());
        assertEquals("321351351351351354", actual.getPassword());
        assertEquals("468798498419", actual.getReplyToInbox());
    }

    @Test
    public void testUnmarshallSendMessageResponse() throws JAXBException {
        MMPResponse actual = (MMPResponse) context.createUnmarshaller().unmarshal(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response code=\"100\" description=\"Successful\"><message>27066</message></response>"));
        assertEquals("100", actual.getCode());
        assertEquals("Successful", actual.getDescription());
        assertEquals("27066", actual.getMessageId());
    }
    
    @Test
    public void testUnmarshallStatusReportResponse() throws JAXBException {
        MMPResponse actual = (MMPResponse) context.createUnmarshaller().unmarshal(new StringReader(
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
"<response code=\"100\" description=\"Successful\">" +
"	<statusReport messageId=\"1876\">" +
"		<recipient msisdn=\"+32495xxxx11\" status=\"The message was delivered\" statusId=\"250\"/>" +
"		<recipient msisdn=\"+32495xxxx22\" status=\"Unable to deliver the message\" statusId=\"252\"/>" +
"	</statusReport>" +
"	<statusReport messageId=\"1877\">" +
"		<recipient msisdn=\"+32495xxxx33\" status=\"The message was delivered\" statusId=\"250\"/>" +
"	</statusReport>" +
"</response>"
        ));
        assertEquals("100", actual.getCode());
        assertEquals("Successful", actual.getDescription());
        List<MMPStatusReportDetail> statusReports = actual.getStatusReports();
        assertTrue(statusReports.size() == 2);
        MMPStatusReportDetail report1 = statusReports.get(0);
        assertEquals("1876", report1.getMessageId());
        List<MMPRecipientStatus> recipients = report1.getRecipients();
        assertTrue(recipients.size() == 2);
        MMPRecipientStatus r1 = recipients.get(0);
        assertEquals("+32495xxxx11", r1.getMsisdn());
        assertEquals("The message was delivered", r1.getStatus());
        assertEquals("250", r1.getStatusId());
        MMPRecipientStatus r2 = recipients.get(1);
        assertEquals("+32495xxxx22", r2.getMsisdn());
        assertEquals("Unable to deliver the message", r2.getStatus());
        assertEquals("252", r2.getStatusId());
        MMPStatusReportDetail report2 = statusReports.get(1);
        assertEquals("1877", report2.getMessageId());
        recipients = report2.getRecipients();
        assertTrue(recipients.size() == 1);
        MMPRecipientStatus r3 = recipients.get(0);
        assertEquals("+32495xxxx33", r3.getMsisdn());
        assertEquals("The message was delivered", r3.getStatus());
        assertEquals("250", r3.getStatusId());
    }
}